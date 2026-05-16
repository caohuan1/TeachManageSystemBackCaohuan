package com.itheima.service;

import com.itheima.pojo.PageBean;
import com.itheima.pojo.Student;

import java.time.LocalDate;
import java.util.List;

/**
 * 学员管理Service
 */
public interface StudentService {
    // 分页查询
    PageBean pageHelper(Integer page, Integer pageSize, String name, Short gender,
                        Integer classId, LocalDate begin, LocalDate end);

    // 批量删除
    int deleteById(List<Integer> ids);

    // 新增学员
    void add(Student student);

    // 根据ID查询
    Student selectById(Integer id);

    // 更新学员
    void update(Student student);

    // 学员登录
    Student login(Student student);
}
