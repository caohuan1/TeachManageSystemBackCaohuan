package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String PAGE_CACHE_PREFIX = "student:page:";
    private static final Duration CACHE_TTL = Duration.ofMinutes(30);

    @Override
    public PageBean pageHelper(Integer page, Integer pageSize, String name, Short gender,
                               Integer classId, LocalDate begin, LocalDate end) {
        // 构建缓存Key
        String key = PAGE_CACHE_PREFIX +
                page + ":" + pageSize + ":" +
                (name == null ? "*" : name) + ":" +
                (gender == null ? "*" : gender) + ":" +
                (classId == null ? "*" : classId) + ":" +
                (begin == null ? "*" : begin) + ":" +
                (end == null ? "*" : end);

        // 1. 先查Redis
        PageBean pageBean = (PageBean) redisTemplate.opsForValue().get(key);
        if (pageBean != null) {
            log.info("Redis命中分页缓存: {}", key);
            return pageBean;
        }

        // 2. 未命中，查MySQL
        PageHelper.startPage(page, pageSize);
        List<Student> studentList = studentMapper.pageHelper(name, gender, classId, begin, end);
        Page<Student> p = (Page<Student>) studentList;
        pageBean = new PageBean(p.getTotal(), p.getResult());

        // 3. 回写Redis
        redisTemplate.opsForValue().set(key, pageBean, CACHE_TTL);
        log.info("回写Redis分页缓存: {}", key);

        return pageBean;
    }

    @Override
    public int deleteById(List<Integer> ids) {
        int count = studentMapper.deleteByIds(ids);
        clearPageCache();
        return count;
    }

    @Override
    public void add(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
        clearPageCache();
    }

    @Override
    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
        clearPageCache();
    }

    @Override
    public Student login(Student student) {
        return studentMapper.getByUsernameAndPassword(student);
    }

    /**
     * 清除所有分页缓存（增删改后调用）
     */
    private void clearPageCache() {
        Set<String> keys = redisTemplate.keys(PAGE_CACHE_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("清除分页缓存: {}条", keys.size());
        }
    }
}