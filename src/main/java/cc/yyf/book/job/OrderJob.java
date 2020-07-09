package cc.yyf.book.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 检查待支付订单
 */
public class OrderJob implements Job {

//    @Autowired
//    BookMapper bookMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        String time = simpleDateFormat.format(date);
//        List<Integer> list = bookMapper.selectInvalidOrder(time, OrderStatus.WAIT_PAY);
//        if (list != null) {
//            bookMapper.orderJobMapper(time, OrderStatus.WAIT_PAY, OrderStatus.CANCEL);
//            for (int bookId : list) {
//                bookMapper.addStock(bookId);
//            }
//        }
    }
}

