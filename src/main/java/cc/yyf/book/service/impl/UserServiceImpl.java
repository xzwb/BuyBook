package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.HomeMapper;
import cc.yyf.book.mapper.UserMapper;
import cc.yyf.book.pojo.*;
import cc.yyf.book.service.UserService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    HomeMapper homeMapper;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 查询用户
     * @param page
     * @return
     */
    @Transactional
    @Override
    public Result searchUser(Page page) {
        // 偏移量
        int from = (page.getPage() - 1) * page.getSize();
        List<Person> people = new ArrayList<>();
        people = userMapper.selectPerson(from, page.getSize(), page.getMessage());
        Map map = new HashMap();
        map.put("total", userMapper.getPersonTotal(page.getMessage()));
        return Result.build(ResultStatusEnum.SUCCESS, people, map);
    }

    /**
     * 查询用户根据学号
     * @param studentCode
     * @return
     */
    @Override
    public Result selectUser(String studentCode) {
        Person person = userMapper.getPersonByStudentCode(studentCode);
        return Result.build(ResultStatusEnum.SUCCESS, person);
    }

    /**
     * 获取该学号用户发布的书籍信息
     * @param studentCode 学号
     * @param from 偏移量
     * @param size 数据数量
     * @return
     */
    @Override
    public Result usersBook(String studentCode, int from, int size) throws ParseException, IOException {
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
        Map map = new HashMap();
        map.put("total", homeMapper.getOwnBookTotal(studentCode));
        return Result.build(ResultStatusEnum.SUCCESS, books, map);
    }
}
