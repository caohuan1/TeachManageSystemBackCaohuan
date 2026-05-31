package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {
    List<Dept> list();

    int delete(Integer id);

    /**
     * 解散部门：将该部门下所有员工 dept_id 置为 0，保留部门记录（不删除）
     */
    int dissolve(Integer id);

    void add(Dept dept);

    Dept select(int id);

    int update(Dept dept);
}
