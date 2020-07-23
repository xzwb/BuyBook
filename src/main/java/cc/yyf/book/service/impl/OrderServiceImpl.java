package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.BookMapper;
import cc.yyf.book.mapper.OrderMapper;
import cc.yyf.book.pojo.*;
import cc.yyf.book.service.OrderService;
import cc.yyf.book.util.BookUtil;
import cc.yyf.book.util.ESIndex;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RestHighLevelClient restHighLevelClient;


    /**
     * 获取购物车中的内容
     * @param page
     * @param studentCode
     * @return
     */
    @Override
    public Result searchBuyCar(Page page, String studentCode) throws IOException {
        int from = (page.getPage() - 1) * page.getSize();
        int size = page.getSize();
        List<BuyCar> buyCars = new ArrayList<>();
        List<String> fieldKeys = getFieldKeys(studentCode, from, size);
        if (fieldKeys != null) {
            for (String key : fieldKeys) {
                buyCars.add(getBuyCar(studentCode, key));
            }
        }
        return Result.build(ResultStatusEnum.SUCCESS, buyCars);
    }

    /**
     * 获取购物车中需要的field的key
     * @param studentCode
     * @return
     */
    private List<String> getFieldKeys(String studentCode, int from, int size) {
        Set<String> fieldKeySet = redisTemplate.opsForHash().keys(BookUtil.buyCar+studentCode);
        // 为了获取set的子集我们要先把set集合转换成list集合
        List<String> fieldKeys = new ArrayList<>(fieldKeySet);
        if (fieldKeys.size() >= from) {
            if (fieldKeys.size() >= from+size) {
                return fieldKeys.subList(from, from+size);
            } else {
                return fieldKeys.subList(from, fieldKeys.size());
            }
        }
        return null;
    }

    /**
     * 获取购物车信息时构造buyCar
     * @param studentCode
     * @return
     */
    private BuyCar getBuyCar(String studentCode, String key) throws IOException {
        // 判断version
        Map<String, Object> map1 = (Map<String, Object>) redisTemplate.opsForHash().get(BookUtil.buyCar+studentCode, key);
        BuyCar buyCar = BuyCar.buildRedis(map1);
        Integer version = (Integer) redisTemplate.opsForValue().get(BookUtil.version+buyCar.getBookId());
        // 如果不相同就去es中重新查找
        if (version != buyCar.getVersion()) {
            GetRequest getRequest = new GetRequest(ESIndex.es, buyCar.getBookId()+"");
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            Map<String, Object> map = getResponse.getSourceAsMap();
            BuyCar buyCar1 = BuyCar.build(map);
            System.out.println(buyCar1);
            buyCar1.setVersion(version);
            addBuyCar(buyCar, studentCode);
        }
        return buyCar;
    }

    /**
     * 添加商品到购物车,采用redis中的hash结构
     * @param buyCar
     * @param studentCode
     * @return
     */
    @Override
    public Result addBuyCar(BuyCar buyCar, String studentCode) {
        Integer version = (Integer) redisTemplate.opsForValue().get(BookUtil.version+buyCar.getBookId());
        buyCar.setVersion(version);
        redisTemplate.opsForHash().put(BookUtil.buyCar+studentCode, buyCar.getBookId()+"", buyCar);
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 保存订单
     * @param userOrder
     * @return
     */
    @Override
    @Transactional
    public Result saveBookOrder(UserOrder userOrder) {
        // 查查库存
        if (haveBook(userOrder.getBookId())) {
            orderMapper.insertBookOrder(userOrder);
            bookMapper.supStock(userOrder.getBookId());
            return Result.build(ResultStatusEnum.SUCCESS);
        }
        return Result.build(ResultStatusEnum.NOT_HAVE_STOCK);
    }

    /**
     * 查看所有的订单
     * @param studentCode
     * @param from
     * @param page
     * @return
     */
    @Override
    public Result searchOrder(String studentCode, int from, int page) {
        List<UserOrder> list = new ArrayList<>();
        list = orderMapper.searchOrder(studentCode, from, page);
        return Result.build(ResultStatusEnum.SUCCESS, list);
    }

    /**
     * 按照订单类别查询
     * @param studentCode
     * @param status
     * @param from
     * @param size
     * @return
     */
    @Override
    public Result searchOrderByStyle(String studentCode, int status, int from, int size) {
        List<UserOrder> list = new ArrayList<>();
        list = orderMapper.searchOrderByStyle(studentCode, status, from, size);
        return Result.build(ResultStatusEnum.SUCCESS, list);
    }

    /**
     * 用户直接从商品主页付款
     * @param userOrder
     * @return
     */
    @Override
    @Transactional
    public Result buyBook(UserOrder userOrder) {
        orderMapper.buyBook(userOrder);
        bookMapper.supStock(userOrder.getBookId());
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 取消一个待支付的订单
     * @param studentCode 学号
     * @param orderId 订单号
     * @return
     */
    @Override
    @Transactional
    public Result cancelOrder(String studentCode, int orderId) {
        orderMapper.cancelOrder(studentCode, orderId, OrderStatus.WAIT_PAY, OrderStatus.CANCEL);
        int bookId = orderMapper.getBookIdByOrderId(orderId);
        bookMapper.addStock(bookId);
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 支付一个待支付的订单
     * @param studentCode
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public Result payOrder(String studentCode, int orderId) {
        orderMapper.cancelOrder(studentCode, orderId, OrderStatus.WAIT_PAY, OrderStatus.SUCCESS_PAY);
        return Result.build(ResultStatusEnum.SUCCESS);
    }


    /**
     * 检测是否有货
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean haveBook(int bookId) {
        int stock = bookMapper.getStock(bookId);
        if (stock > 0) {
            return true;
        }
        return false;
    }
}
