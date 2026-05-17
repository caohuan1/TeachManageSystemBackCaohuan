package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //获取总行数
        Integer count = empMapper.count();

        //获取数据列表
        Integer start = (page-1)*pageSize;
        List<Emp> empList = empMapper.page(start, pageSize);

        //封装为PageBean对象
        PageBean pageBean =new PageBean(count,empList);

        return pageBean;
    }

    @Override//使用PageHelper实现分页查询
    public PageBean pageHelper(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询
        List<Emp> empList = empMapper.pageHelper(name,gender,begin,end);
        Page<Emp> p = (Page<Emp>) empList;
        //封装为PageBean对象
        PageBean pageBean =new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public int deleteById(List<Integer> ids) {
        int updates =empMapper.deleteByIds(ids);
        return updates;
    }

    @Override
    public void add(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public Emp selectById(Integer id) {
        Emp emp = empMapper.selectById(id);
        return emp;
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Override
    public Emp login(Emp emp) {
        emp=empMapper.getByUsernameAndPassword(emp);
        return emp;
    }

    @Override
    public boolean updatePassword(Integer id, String oldPassword, String newPassword) {
        // 校验新密码不能为空
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("新密码不能为空");
        }
        
        // 校验密码长度（至少6位）
        if (newPassword.length() < 6) {
            throw new IllegalArgumentException("密码长度不能少于6位");
        }
        
        Emp emp = empMapper.selectById(id);
        if (emp == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        
        // 校验原密码
        if (!emp.getPassword().equals(oldPassword)) {
            return false;
        }
        
        // 校验新密码不能和原密码相同
        if (newPassword.equals(oldPassword)) {
            throw new IllegalArgumentException("新密码不能与原密码相同");
        }
        
        empMapper.updatePassword(id, newPassword, LocalDateTime.now());
        return true;
    }

}
