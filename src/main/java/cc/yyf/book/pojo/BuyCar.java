package cc.yyf.book.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 存入redis的购物车类
 */
@Data
public class BuyCar implements Serializable {
    // 书籍编号
    private int bookId;
    // 书籍图片路径
    private String bookSrc;
    // 书籍名字
    private String bookName;
    // 卖家学号
    private String sellerStudentCode;
    // 卖家昵称
    private String sellerName;
    // 商品价格
    private Double price;
    /**
     * 商品版本号,这个是用来同步商品信息的如果商品被修改了就会被监测
     */
    private Integer version;

    /**
     * 建造者模式
     */
    public static BuyCar build(Map<String, Object> map) {
        BuyCar buyCar = new BuyCar();
        buyCar.setBookId((Integer) map.get("bookId"));
        buyCar.setBookName((String) map.get("bookName"));
        buyCar.setSellerStudentCode((String) map.get("studentCode"));
        buyCar.setPrice((Double) map.get("price"));
        buyCar.setBookSrc((String) map.get("bookSrc"));
        buyCar.setSellerName((String) map.get("userName"));
        return buyCar;
    }

    /**
     * 建造者redis
     */
    public static BuyCar buildRedis(Map<String, Object> map) {
        BuyCar buyCar = new BuyCar();
        buyCar.setBookId((Integer) map.get("bookId"));
        buyCar.setBookName((String) map.get("bookName"));
        buyCar.setSellerStudentCode((String) map.get("sellerStudentCode"));
        buyCar.setPrice((Double) map.get("price"));
        buyCar.setBookSrc((String) map.get("bookSrc"));
        buyCar.setSellerName((String) map.get("sellerName"));
        buyCar.setVersion((Integer) map.get("version"));
        return buyCar;
    }
}
