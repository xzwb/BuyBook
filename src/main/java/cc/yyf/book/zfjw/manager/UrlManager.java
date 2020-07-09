package cc.yyf.book.zfjw.manager;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public interface UrlManager {
    URIBuilder getPublicKeyUrl(String baseUrl) throws URISyntaxException;

    URIBuilder getIndexUrl(String baseUrl) throws URISyntaxException;

    URIBuilder getLoginUrl(String baseUrl) throws URISyntaxException;
}
