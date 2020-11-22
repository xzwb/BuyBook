package cc.yyf.book;

import cc.yyf.book.pojo.Book;
import cc.yyf.book.util.ESIndex;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * es api测试
 */
@SpringBootTest
class BookApplicationTests {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 判断索引是否存在
     * @throws IOException
     */
    @Test
    void contextLoads() throws IOException {
//        GetIndexRequest request = new GetIndexRequest("book");
//        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
//        System.out.println(exists);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<MyBean>> map = new HashMap<>();
        List<MyBean> list = new ArrayList<>();
        map.put("a", list);
        list.add(new MyBean("ss", "18"));
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, List.class);
        String s = objectMapper.writeValueAsString(map);
        Map<String, List<MyBean>> map1 = objectMapper.readValue(s, javaType);
        System.out.println(map1);
        System.out.println(s);
    }

    /**
     * 获取文档信息
     */
    @Test
    void test() throws IOException {
        GetRequest getRequest = new GetRequest("book", "2");

//        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
//        System.out.println(exists);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
        System.out.println(getResponse.toString());
    }

    /**
     * 删除文档记录
     */
    @Test
    void delete() throws IOException {
        DeleteRequest request = new DeleteRequest("book", "1");
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    /**
     * 批量查询
     */
    @Test
    void testSearch() throws IOException {
        SearchRequest request = new SearchRequest(ESIndex.es);
        // 构建搜索的条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        searchSourceBuilder.query(matchAllQueryBuilder);
        // 查询条件
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userName", "yyf");
//        searchSourceBuilder.query(termQueryBuilder);
        // 分页
//        searchSourceBuilder.size(0);
//        searchSourceBuilder.from(5);
        // 查询时间
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//        SearchHits hits = search.getHits();
//        System.out.println(JSON.toJSONString(hits));
        for (SearchHit documentFields : search.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }
}
