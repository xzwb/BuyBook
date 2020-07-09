package cc.yyf.book.thread;

import javax.servlet.http.Part;
import java.io.IOException;

/**
 * 保存文件的线程
 */
public class SaveFileThread extends Thread {
    private Part part;

    private String src;

    public SaveFileThread(Part part, String src) {
        this.part = part;
        this.src = src;
    }

    @Override
    public void run() {
        try {
            part.write(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
