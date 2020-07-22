package cc.yyf.book.cache;

import cc.yyf.book.mapper.HomeMapper;
import cc.yyf.book.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonCache {
    @Autowired
    HomeMapper homeMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 对用户信息的一个缓存
     * @param studentCode
     * @return
     */
    @Cacheable(cacheNames = "user", key = "#root.methodName+'['+#studentCode+']'")
    public Person getPerson(String studentCode) {
        Person person = homeMapper.selectPersonByStudentCode(studentCode);
        return person;
    }

    /**
     * 删除用户的信息缓存，并且从redis中删除用户的登录信息
     * @param studentCode
     */
    @CacheEvict(value = "user", key = "'getPerson' + '[' + #studentCode + ']'")
    public void logout(String studentCode) {
        redisTemplate.delete(studentCode);
    }
}
