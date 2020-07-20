package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.BookMapper;
import cc.yyf.book.pojo.Book;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.BookService;
import cc.yyf.book.thread.SaveFileThread;
import cc.yyf.book.util.ESIndex;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 用户发布书籍的service层
     * @param book
     * @param fileURI
     * @param part
     * @return
     */
    @Transactional
    @Override
    public Result release(Book book, String fileURI, Part part) {
        bookMapper.insertBook(book);
        new SaveFileThread(part, fileURI).run();
        return Result.build(ResultStatusEnum.SUCCESS, book);
    }

    /**
     * 根据书籍id定位到特定书籍
     * @param bookId
     * @return
     */
    @Override
    public Result selectBookById(int bookId) throws IOException, ParseException {
        GetRequest getRequest = new GetRequest(ESIndex.es, bookId+"");

        // 判断文档是否存在
        if (restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT)) {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            Map<String, Object> map = getResponse.getSourceAsMap();
            Book book = Book.build(map);
            return Result.build(ResultStatusEnum.SUCCESS, book);
        }

        // 输入的bookId无法找到书籍
        return Result.build(ResultStatusEnum.DOC_NOT_FOUND);
    }

    /**
     * 通过es获取所有书籍信息
     * @param from 起始位置
     * @param size 每页的个数
     * @return
     */
    @Override
    public Result getAllBook(int from, int size) throws IOException, ParseException {
        SearchRequest request = new SearchRequest(ESIndex.es);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置当前页的数据个数
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        searchSourceBuilder.sort(new FieldSortBuilder("bookDate").order(SortOrder.DESC));
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<Book> books = new ArrayList<>();
        for (SearchHit documentFields : search.getHits().getHits()) {
            books.add(Book.build(documentFields.getSourceAsMap()));
        }

        return Result.build(ResultStatusEnum.SUCCESS, books);
    }

    /**
     * 模糊搜索书籍
     * @param message 搜索条件
     * @param from
     * @param size
     * @return
     */
    @Override
    public Result searchBook(String message, int from, int size) throws IOException, ParseException {
        SearchRequest request = new SearchRequest(ESIndex.es);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("bookName", message);
        searchSourceBuilder.query(matchQueryBuilder);
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("bookName");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        // 执行搜索
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        List<Book> books = new ArrayList<>();
        for (SearchHit documentFields : response.getHits().getHits()) {
            // 解析高亮字段并替换
            Map<String, HighlightField> highlightFieldMap = documentFields.getHighlightFields();
            HighlightField bookName = highlightFieldMap.get("bookName");
            Map<String, Object> map = documentFields.getSourceAsMap();
            if (bookName != null) {
                Text[] fragments = bookName.fragments();
                String s = "";
                for (Text text : fragments) {
                    s += text;
                }
                map.put("bookName", s);
            }
            books.add(Book.build(map));
        }
        return Result.build(ResultStatusEnum.SUCCESS, books);
    }
}
