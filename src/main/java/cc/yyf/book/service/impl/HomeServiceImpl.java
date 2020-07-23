package cc.yyf.book.service.impl;

import cc.yyf.book.cache.PersonCache;
import cc.yyf.book.mapper.HomeMapper;
import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.BookUpdate;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.HomeService;
import cc.yyf.book.util.BookUtil;
import cc.yyf.book.util.ESIndex;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    PersonCache personCache;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    HomeMapper homeMapper;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 根据用户学号获取用户信息
     * @param studentCode
     * @return
     */
    @Override
    public Result homeService(String studentCode) {
        return Result.build(ResultStatusEnum.SUCCESS, personCache.getPerson(studentCode));
    }

    /**
     * 注销
     * @param studentCode
     * @return
     */
    @Override
    public Result logout(String studentCode) {
        personCache.logout(studentCode);
        return Result.build(ResultStatusEnum.SUCCESS);
    }

    /**
     * 获取当前用户发布的书籍信息
     * @param studentCode 当前用户的学号
     * @param from 偏移量
     * @param size 数据个数
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public Result getOwnBook(String studentCode, int from, int size) throws IOException, ParseException {
        SearchRequest searchRequest = new SearchRequest(ESIndex.es);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new FieldSortBuilder("bookDate").order(SortOrder.DESC));
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studentCode", studentCode);
        searchSourceBuilder.query(termQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Book> books = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            books.add(Book.build(documentFields.getSourceAsMap()));
        }
        return Result.build(ResultStatusEnum.SUCCESS, books);
    }

    /**
     * 修改已经发布的书籍信息
     * @param studentCode
     * @param book
     * @return
     */
    @Override
    public Result updateBook(String studentCode, BookUpdate book) {
        homeMapper.updateBook(studentCode, book);
        // 每一次的修改都会使redis中的书籍版本号加一
        redisTemplate.opsForValue().increment(BookUtil.version+book.getBookId());
        return Result.build(ResultStatusEnum.SUCCESS);
    }
}
