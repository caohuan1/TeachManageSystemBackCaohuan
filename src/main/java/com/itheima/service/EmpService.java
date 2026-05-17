package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface EmpService {

    PageBean page(Integer page, Integer pageSize);

    //使用PageHelper实现分页查询
    PageBean pageHelper(Integer page, Integer pageSize,String name, Short gender,
                        LocalDate begin,
                        LocalDate end);

    int deleteById(List<Integer> ids);

    void add(Emp emp);

    Emp selectById(Integer id);

    void update(Emp emp);

    Emp login(Emp emp);

    /**
     * 修改密码
     * @param id 员工ID
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 修改结果（true-成功，false-原密码错误）
     */
    boolean updatePassword(Integer id, String oldPassword, String newPassword);
}
