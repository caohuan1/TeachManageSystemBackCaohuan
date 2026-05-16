package com.itheima.service;

import com.itheima.pojo.Clazz;

import java.util.List;

/**
 * 班级管理Service
 */
public interface ClazzService {
    List<Clazz> list();

    int delete(Integer id) throws Exception;

    void add(Clazz clazz);

    Clazz select(int id);

    int update(Clazz clazz);
}
