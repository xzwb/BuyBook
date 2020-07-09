package cc.yyf.book.zfjw.model;

import lombok.NonNull;

public class User {
    // 学号
    private String stuCode;
    // 密码
    private String password;

    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User(String stuCode, String password) {
        this.stuCode = stuCode;
        this.password = password;
    }

    public static User builder(@NonNull String stuCode, @NonNull String password) {
        return new User(stuCode, password);
    }

}
