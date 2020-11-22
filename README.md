# BuyBook
本项目是一个面对西邮内部的一个二手书交易网站，实现了用户登录，注册，用户添加书籍信息，用户对书籍的购买，以及订单的查询。
项目基于前后端分离的设计方式，通过json进行数据交互。前端基于 Vue，后端基于 SpringBoot、MyBatis 等框架开发。

---
# 相关技术点
### RabbitMQ
使用腾讯云短信业务实现发送短信验证码。调用相关`API`。这里使用到了`RabbitMQ`因为用户不需要等待发短信的时间，用户传入输入手机号前端做验证，传入后台，后台使用`JSR303`进行一个正则校验以后放入消息队列中，然后给用户返回`200`。

### JWT权限验证
在用户登录或注册成功后，为用户生成一个 token，并且将token存入`Redis`数据库中设置过期时间为`30`分钟，用户获取token之后，每次请求都需要携带 token。用户发送请求后，如果请求需要用户登录，服务会从请求头 `token` 获取 token，判断`Redis`中有没有该用户的token，如果有会更新在`redis`中的token时间重置为30分钟，再利用服务器的秘钥判断 token 是否被篡改，最后进行 token 的有效性验证，判断 token 是否过期或已经失效。
对于token失效处理:

-	用户注销
用户注销后会删除用户在`Redis`中的token信息
- token过期
会去`redis`中查看是否有token信息，如果有生成新的token并且给前端返回状态码`201`并且返回新的token，新的token存入`redis`中替换掉旧token并设置30分钟过期。前端接收到以后在用户不知情的情况下修改请求头用新的token发送相同的请求到后台。

### 订单超时处理
订单的超时:使用Quartz 设置定时任务，如果超时则恢复订单处理，同时恢复库存。每分钟执行一次，检索数据库字段进行匹配。

### MyBatis的数据库类型映射
- 在书籍类型处使用了`List<String>`保存书籍的类型，使用`typeHandler`对`JDBCType`和`JavaType`进行转化。
```java
/**
 * mybatis自定义java类型和数据库类型的转换
 * list 和 varchar的转换
 */
public class MyTypeHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        String list = String.join(",", strings);
        preparedStatement.setString(i, list);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        List<String> list = Arrays.asList(result.split(","));
        return list;
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        List<String> list = Arrays.asList(result.split(","));
        return list;
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        List<String> list = Arrays.asList(result.split(","));
        return list;
    }
}
```
- 将订单状态的枚举类型转化为Int类型
```java
/**
 * 自定义枚举类在数据库中的存储
 */
public class OrderEnumTypeHandler implements TypeHandler<OrderStatus> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, OrderStatus orderStatus, JdbcType jdbcType) throws SQLException {
        int status = orderStatus.getStatus();
        preparedStatement.setInt(i, status);
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, String s) throws SQLException {
        int status = resultSet.getInt(s);
        if (status == 1) {
            return OrderStatus.WAIT_PAY;
        } else if (status == 2) {
            return OrderStatus.SUCCESS_PAY;
        } else if (status == 3) {
            return OrderStatus.CANCEL;
        } else if (status == 4) {
            return OrderStatus.END_TIME;
        } else {
            return OrderStatus.OK;
        }
    }

    @Override
    public OrderStatus getResult(ResultSet resultSet, int i) throws SQLException {
        int status = resultSet.getInt(i);
        if (status == 1) {
            return OrderStatus.WAIT_PAY;
        } else if (status == 2) {
            return OrderStatus.SUCCESS_PAY;
        } else if (status == 3) {
            return OrderStatus.CANCEL;
        } else if (status == 4) {
            return OrderStatus.END_TIME;
        } else {
            return OrderStatus.OK;
        }
    }

    @Override
    public OrderStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        int status = callableStatement.getInt(i);
        if (status == 1) {
            return OrderStatus.WAIT_PAY;
        } else if (status == 2) {
            return OrderStatus.SUCCESS_PAY;
        } else if (status == 3) {
            return OrderStatus.CANCEL;
        } else if (status == 4) {
            return OrderStatus.END_TIME;
        } else {
            return OrderStatus.OK;
        }
    }
}
```

### 全局异常处理
全局异常处理返回相对应的状态码
```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result except(HttpServletRequest request, Exception e) {
        log.info(new Date().toString());
        /* 处理jsr303校验抛出的异常 */
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String errorMsg = fieldError.getDefaultMessage();
            return Result.build(ResultStatusEnum.BIND_EXCEPTION(errorMsg));
        } else if (e instanceof BindException) {
            log.info("***不能为null");
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String errorMsg = error.getDefaultMessage();
            return Result.build(ResultStatusEnum.BIND_EXCEPTION(errorMsg));
            /* 针对短信验证码的时候,传入的电话号码为空 */
        } else if (e instanceof HttpMessageNotReadableException) {
            return Result.build(ResultStatusEnum.NULL_EXCEPTION);
            /* 保存文件的时候无法保存 */
        } else if (e instanceof IOException) {
            return Result.build(ResultStatusEnum.IO_EXCEPTION);
            /* 学号被重复注册问题 */
        } else if (e instanceof DuplicateKeyException) {
            return Result.build(ResultStatusEnum.CODE_HAVE);
            /* 没有token */
        } else if (e instanceof LoginTokenException) {
            return Result.build(ResultStatusEnum.TOKEN_FALSE);
            /* 购物车订单中有没货的 */
        } else if (e instanceof BuyCarException) {
            return Result.build(ResultStatusEnum.NOT_HAVE_STOCK);
        } else if (e instanceof OrderException) {
            return Result.build(ResultStatusEnum.ORDER_ENDTIME);
        }
       return Result.build(ResultStatusEnum.EXCEPTION);
    }
}
```

### ES实现书籍信息的模糊搜索
`ElasticSearch`实现了强大的模糊搜索功能，但我们的数据保存在 MySQL 关系型数据库中，为此，使用 Logstash 定时增量同步 MySQL 中的部分数据到 Es 中，相关配置在:
https://github.com/xzwb/BuyBook/blob/master/mysql.conf
为了更好的实现模糊查询的准确性，使用了 ik 中文分词。

### Redis
- 使用redis的`HyperLogLog`类型来统计当前书籍的`UV`（Unique Visitor）访问量，在用户点开当前的书籍主页时调用。
- 利用redis的hash结构存储购物车信息，并且添加购物车时会有一个`version`字段，在获取购物车信息的时候会先去ES中比较`version`字段确保获取到最新的书籍信息

---
# 项目截图

![](https://github.com/xzwb/BuyBook/blob/master/img/1.png)

![](https://github.com/xzwb/BuyBook/blob/master/img/2.png)

![](https://github.com/xzwb/BuyBook/blob/master/img/3.png)

![](https://github.com/xzwb/BuyBook/blob/master/img/4.png)

![](https://github.com/xzwb/BuyBook/blob/master/img/5.png)

![](https://github.com/xzwb/BuyBook/blob/master/img/6.png)
