package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 学员管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 分页查询学员列表
    @GetMapping
    @Log
    public Result pageHelper(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String name, Short gender, Integer classId,
                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        PageBean pageBean = studentService.pageHelper(page, pageSize, name, gender, classId, begin, end);
        return Result.success(pageBean);
    }

    // 批量删除学员
    @DeleteMapping("/{ids}")
    @Log
    public Result delete(@PathVariable List<Integer> ids) {
        int updates = studentService.deleteById(ids);
        if (updates > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败，没有数据（来自后端响应）！");
        }
    }

    // 新增学员
    @PostMapping
    @Log
    public Result add(@RequestBody Student student) {
        studentService.add(student);
        return Result.success("新增成功！");
    }

    // 通过ID查询学员
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        Student student = studentService.selectById(id);
        return Result.success(student);
    }

    // 更新学员
    @PutMapping
    @Log
    public Result update(@RequestBody Student student) {
        studentService.update(student);
        return Result.success();
    }
}
