package cc.yyf.book.service.impl;

import cc.yyf.book.cache.PersonCache;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    PersonCache personCache;


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
}
