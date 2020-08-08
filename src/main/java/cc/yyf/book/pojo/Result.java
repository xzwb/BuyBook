package cc.yyf.book.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回给前端的结果封装
 */
@Data
public class Result {
    // 状态码
    private int status;
    // 详细信息
    private String message;
    // 附带的内容
//    private Object data;
    private List<Object> data = new ArrayList<>();

    /***** 构造方法 *****/
    private Result() {
    }

    private Result(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    /** 使用build方法创建Result对象 **/
    public static Result build(ResultStatusEnum statusEnum) {
        return new Result(statusEnum.getStatus(), statusEnum.getMessage());
    }

    public static Result build(ResultStatusEnum statusEnum,  Object ... objects) {
        Result result = Result.build(statusEnum);
        for (Object data : objects) {
            result.data.add(data);
        }
        return result;
    }
}
