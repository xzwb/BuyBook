package cc.yyf.book.zfjw.model;

import lombok.Getter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class LoginData {

    // 防止跨站域请求
    private String csrfToken;
    // 用户数据 学号密码
    private User user;

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginData(String csrfToken, User user) {
        this.csrfToken = csrfToken;
        this.user = user;
    }

    @Getter
    public static class CovertData {

        private NameValuePair[] urlParams;
        // 发送的请求参数
        private List<NameValuePair> requestEntity;

        public NameValuePair[] getUrlParams() {
            return urlParams;
        }

        public void setUrlParams(NameValuePair[] urlParams) {
            this.urlParams = urlParams;
        }

        public List<NameValuePair> getRequestEntity() {
            return requestEntity;
        }

        public void setRequestEntity(List<NameValuePair> requestEntity) {
            this.requestEntity = requestEntity;
        }

        private CovertData(NameValuePair[] urlParams, List<NameValuePair> requestEntity) {
            this.urlParams = urlParams;
            this.requestEntity = requestEntity;
        }

        /**
         * builder模式
         * @param loginData
         * @param encryptedPassword
         * @return
         */
        public static CovertData buildCovertData(LoginData loginData, String encryptedPassword) {
            /*
             * 当前 url 为时间戳，在 UrlManagerImpl#
             */
            NameValuePair[] urlParams = null;

            List<NameValuePair> requestEntity = new ArrayList<>();
            requestEntity.add(new BasicNameValuePair("csrftoken", loginData.getCsrfToken()));
            requestEntity.add(new BasicNameValuePair("yhm", loginData.getUser().getStuCode()));
            requestEntity.add(new BasicNameValuePair("mm", encryptedPassword));
            requestEntity.add(new BasicNameValuePair("mm", encryptedPassword));

            return new CovertData(urlParams, requestEntity);
        }

    }

}
