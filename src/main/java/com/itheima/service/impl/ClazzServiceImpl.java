package com.itheima.service.impl;

import com.itheima.mapper.ClazzMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Clazz;
import com.itheima.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Clazz> list() {
        return clazzMapper.list();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(Integer id) throws Exception {
        int i = clazzMapper.deleteById(id);
        // 删除该班级下的所有学员
        studentMapper.deleteByClassId(id);
        return i;
    }

    @Override
    public void add(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz select(int id) {
        return clazzMapper.select(id);
    }

    @Override
    public int update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        return clazzMapper.update(clazz);
    }
}
