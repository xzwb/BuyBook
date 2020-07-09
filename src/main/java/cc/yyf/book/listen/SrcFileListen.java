package cc.yyf.book.listen;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SrcFileListen {
    /**
     * 删除文件
     */
    @RabbitListener(queues = "queue_delFile_yyf")
    public void delFile(String src) {
        File file = new File(src);
        file.delete();
    }
}
