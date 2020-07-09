package cc.yyf.book.zfjw.manager.impl;

import cc.yyf.book.zfjw.manager.UrlManager;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class UrlManagerImpl implements UrlManager {

    private static final String defaultProtocol = "http";

    private static final String publicKeyUrl = "/jwglxt/xtgl/login_getPublicKey.html";
    private static final String indexUrl = "/jwglxt/xtgl/login_slogin.html";
    private static final String loginUrl = "/jwglxt/xtgl/login_slogin.html";

    private static UrlManager instance;

    private UrlManagerImpl() {
    }

    /**
     * 单例模式 双重验证
     * @return
     */
    public static UrlManager getInstance() {
        if (instance == null) {
            synchronized (UrlManagerImpl.class) {
                if (instance == null) {
                    instance = new UrlManagerImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public URIBuilder getPublicKeyUrl(String baseUrl) throws URISyntaxException {
        return afterDecorate(getUrl(baseUrl, publicKeyUrl), true);
    }

    @Override
    public URIBuilder getIndexUrl(String baseUrl) throws URISyntaxException {
        return afterDecorate(getUrl(baseUrl, indexUrl), false)
                .setParameter("_t", String.valueOf(System.currentTimeMillis()))
                .setParameter("language", "zh_CN");
    }

    @Override
    public URIBuilder getLoginUrl(String baseUrl) throws URISyntaxException {
        return afterDecorate(getUrl(baseUrl, loginUrl), true);
    }

    // url拼接
    private String getUrl(String baseUrl, String link) {
        return defaultProtocol + "://" +
                (!baseUrl.endsWith("/") ? baseUrl : baseUrl.substring(0, baseUrl.length() - 1))
                + link;
    }

    private URIBuilder afterDecorate(String string, boolean exec) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(string);
        if (!exec) {
            return uriBuilder;
        }
        uriBuilder.setParameter("time", String.valueOf(System.currentTimeMillis()));
        return uriBuilder;
    }

}
