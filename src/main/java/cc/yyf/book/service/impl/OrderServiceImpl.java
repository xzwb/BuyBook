package cc.yyf.book.service.impl;

import cc.yyf.book.exception.BuyCarException;
import cc.yyf.book.exception.OrderException;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
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
    RabbitTemplate rabbitTemplate;

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
        Set<String> fieldKeySet = redisTemplate.opsForHash().keys(BookUtil.buyCar+studentCode);
        Map map = new HashMap();
        map.put("total", fieldKeySet.size());
        return Result.build(ResultStatusEnum.SUCCESS, buyCars, map);
    }

    /**
     * 删除购物车中的一个商品
     * @param studentCode 学号
     * @param bookIds 购物车中商品的编号
     * @return
     */
    @Override
    public Result deleteBuyCar(String studentCode, List<Integer> bookIds) {
        for (Integer bookId : bookIds) {
            redisTemplate.opsForHash().delete(BookUtil.buyCar+studentCode, bookId+"");
        }
        return Result.build(ResultStatusEnum.SUCCESS);
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
        Map map = new HashMap();
        map.put("total", orderMapper.getOrderTotal(studentCode));
        return Result.build(ResultStatusEnum.SUCCESS, list, map);
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
        Map map = new HashMap();
        map.put("total", orderMapper.getOrderByStyleTotal(studentCode, status));
        return Result.build(ResultStatusEnum.SUCCESS, list, map);
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
        int num = orderMapper.getNumByOrderId(orderId);
        bookMapper.addStock(bookId, num);
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 支付一个待支付的订单
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public Result payOrder(int orderId) {
        if (orderEnd(orderId)) {
            throw new OrderException();
        }
        orderMapper.payOrder(OrderStatus.SUCCESS_PAY, orderId);
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 生成订单
     * @param makeOrder
     * @param studentCode
     * @return
     */
    @Override
    @Transactional
    public Result makeOrder(MakeOrder makeOrder, String studentCode) throws IOException, ParseException {
        List<Integer> bookIds = makeOrder.getBookIds();
        List<Integer> number = makeOrder.getNumber();
        List<Order> orders = new ArrayList<>();
        int count = bookIds.size();
        Date buyDate = new Date();
        Date orderEndTime = new Date(buyDate.getTime() + 15*1000*60);
        for (int i = 0; i < count; i++) {
            Integer bookId = bookIds.get(i);
            Integer num = number.get(i);
            // 有货
            if (bookMapper.getStock(bookId) >= num) {
                UserOrder userOrder = UserOrder.build(studentCode, bookId, buyDate, OrderStatus.WAIT_PAY, orderEndTime, num);
                orderMapper.insertBookOrder(userOrder);
                orders.add(orderMapper.getOrderByOrderId(userOrder.getOrderId()));
                // 减少库存
                bookMapper.subStock(bookId, num);
            } else {
                // 没货回滚事务
                throw new BuyCarException();
            }
        }
        return Result.build(ResultStatusEnum.SUCCESS, orders);
    }

    /**
     * 立即支付订单
     * @param orderIds 订单列表
     * @return
     */
    @Override
    @Transactional
    public Result payOrderNow(List<Integer> orderIds) {
        for (int orderId : orderIds) {
            if (orderEnd(orderId)) {
                throw new OrderException();
            }
            orderMapper.payOrder(OrderStatus.SUCCESS_PAY, orderId);
        }
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 确认收货
     * @param orderId
     * @param studentCode
     * @return
     */
    @Override
    @Transactional
    public Result okOrder(int orderId, String studentCode) {
        orderMapper.cancelOrder(studentCode, orderId, OrderStatus.SUCCESS_PAY, OrderStatus.OK);
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 检测order是否过期
     */
    @Transactional
    public boolean orderEnd(int orderId) {
        if (orderMapper.getOrderStatus(orderId) == 3) {
            return true;
        }
        return false;
    }
}
