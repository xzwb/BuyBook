package cc.yyf.book;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 开启基于注解的rabbitmq
@EnableRabbit
// 开启声明式事务
@EnableTransactionManagement
@SpringBootApplication
// 开启定时器
@EnableScheduling
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
