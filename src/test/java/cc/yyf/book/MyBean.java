package cc.yyf.book;

import java.io.Serializable;

public class MyBean implements Serializable {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public MyBean(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
