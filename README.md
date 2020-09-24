# BuyBook
本项目是一个面对西邮内部的一个二手书交易网站，实现了用户登录，注册，用户添加书籍信息，用户对书籍的购买，以及订单的查询。
项目基于前后端分离的设计方式，通过json进行数据交互。前端基于 Vue，后端基于 SpringBoot、MyBatis 等框架开发。

---
# 技术点
- 使用`JWT`进行授权认证
- 利用腾讯云短信API进行短信的发送
- 利用`rabbitmq`进行短信的延迟发送
- 将购物车信息存储到`redis`，并将短信验证码等信息缓存到`redis`中
- 使用 `Quartz` 处理订单超时任务
- 使用`elasticsearch`对书籍信息进行存储
- 使用`logstash`对数据库和`elasticsearch`中的信息进行同步
- 实现了全局异常处理
- 使用 JSR303 以及分组校验进行数据验证
