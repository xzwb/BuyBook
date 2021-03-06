package cc.yyf.book.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分页插件
 */
@Data
public class Page {
    // 查询条件
    private String message;
    // 页数
    @NotNull
    private int page;
    // 每页个数
    @NotNull
    private int size;
}
