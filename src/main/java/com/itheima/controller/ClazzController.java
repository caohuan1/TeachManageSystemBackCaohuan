package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.Result;
import com.itheima.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理Controller
 */
@RequestMapping("/classes")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    // 查询全部的班级列表
    @GetMapping
    public Result list() {
        List<Clazz> list = clazzService.list();
        return Result.success(list);
    }

    // 根据url传入班级id查询班级数据
    @GetMapping("/{id}")
    public Result selectById(@PathVariable int id) {
        Clazz clazz = clazzService.select(id);
        return Result.success(clazz);
    }

    // 根据url传入删除的id删除班级
    @DeleteMapping("/{id}")
    @Log
    public Result delete(@PathVariable int id) throws Exception {
        int i = clazzService.delete(id);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败！(来自后端响应)");
        }
    }

    // json格式传入新增的班级进行新增班级操作
    @PostMapping
    @Log
    public Result insert(@RequestBody Clazz clazz) {
        clazzService.add(clazz);
        return Result.success();
    }

    // json格式传入修改的班级进行修改班级操作
    @PutMapping
    @Log
    public Result update(@RequestBody Clazz clazz) {
        int u = clazzService.update(clazz);
        if (u > 0) {
            return Result.success();
        } else {
            return Result.error("修改失败！(来自后端响应)");
        }
    }
}
