package com.itheima.service.impl;

import com.itheima.mapper.DeptLogMapper;
import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Dept;
import com.itheima.pojo.DeptLog;
import com.itheima.pojo.Emp;
import com.itheima.service.DeptLogService;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private DeptLogService deptLogService;


    @Override
    public List<Dept> list() {
        List<Dept> list = deptMapper.list();
        return list;
    }

    @Transactional(rollbackFor = Exception.class)//此方法开启事务（要么全部成功，事务提交；要么全部失败，事务回滚）
    @Override
    public int delete(Integer id) {
        // 检查该部门下是否有员工
        List<Emp> empList = empMapper.selectByDeptId(id);
        if (empList != null && !empList.isEmpty()) {
            String names = empList.stream()
                    .map(Emp::getName)
                    .collect(Collectors.joining("、"));
            throw new RuntimeException("该部门下还有员工" + names + ",不允许删除！");
        }
        int i;
        try {
            i = deptMapper.deleteById(id);
        } finally {
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行了解散部门的操作,此次解散的是"+id+"号部门");
            deptLogService.insert(deptLog);
        }
        return i;
    }

    @Transactional(rollbackFor = Exception.class)//解散部门：员工dept_id置为0，保留部门记录（不删除）
    @Override
    public int dissolve(Integer id) {
        // 将该部门下所有员工的 dept_id 置为 0
        int count = empMapper.updateDeptIdToZero(id);
        // 记录操作日志
        DeptLog deptLog = new DeptLog();
        deptLog.setCreateTime(LocalDateTime.now());
        deptLog.setDescription("执行了解散部门的操作,此次解散的是" + id + "号部门，该部门" + count + "名员工已移入空部门");
        deptLogService.insert(deptLog);
        return count;
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public Dept select(int id) {
        Dept dept = deptMapper.select(id);
        return dept;
    }

    @Override
    public int update(Dept dept) {
        int u = deptMapper.update(dept);
//        dept.setUpdateTime(LocalDateTime.now());
        return u;
    }
}
