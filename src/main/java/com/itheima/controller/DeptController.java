package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 部门管理Controller
 */
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    //查询全部的部门列表
    @GetMapping
    public Result list() {
        List<Dept> list = deptService.list();
        return Result.success(list);
    }
    //根据url传入部门id查询部门数据
    @GetMapping("/{id}")
    public Result selectById(@PathVariable int id) {
        Dept dept = deptService.select(id);
        return Result.success(dept);
    }
    //根据url传入删除的id删除部门
    @DeleteMapping("/{id}")
    @Log
    public Result delete(@PathVariable int id) throws Exception {
        int i = deptService.delete(id);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败！(来自后端响应)");
        }
    }
    //json格式传入新增的部门进行新增部门操作
    @PostMapping
    @Log
    public Result insert(@RequestBody Dept dept) {
        deptService.add(dept);
        return Result.success();
    }
    //json格式传入新增的部门进行修改部门操作
    @PutMapping
    @Log
    public Result update(@RequestBody Dept dept) {
        int u = deptService.update(dept);
        if (u > 0) {
            return Result.success();
        } else {
            return Result.error("修改失败！(来自后端响应)");
        }
    }

}
