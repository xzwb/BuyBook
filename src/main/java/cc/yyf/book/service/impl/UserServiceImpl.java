package cc.yyf.book.service.impl;

import cc.yyf.book.mapper.UserMapper;
import cc.yyf.book.pojo.Page;
import cc.yyf.book.pojo.Person;
import cc.yyf.book.pojo.Result;
import cc.yyf.book.pojo.ResultStatusEnum;
import cc.yyf.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

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
        return Result.build(ResultStatusEnum.SUCCESS, people);
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
}
