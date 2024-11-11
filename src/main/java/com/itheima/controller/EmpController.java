package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.anno.MyLog;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

//    @GetMapping     //接收前端两个参数。  参数一：第几页  参数二：每页展示多少行
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10")Integer pageSize){
//        PageBean pageBean =empService.page(page,pageSize);
//        return Result.success(pageBean);
//    }

    //使用PageHelper实现上面的功能（具体实现见service层）
    @GetMapping     //接收前端两个参数。  参数一：第几页  参数二：每页展示多少行
    @MyLog
    public Result pageHelper(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10")Integer pageSize,
                             String name, Short gender,
                             @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate begin,
                             @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate end){
        PageBean pageBean =empService.pageHelper(page,pageSize,name,gender,begin,end);
        return Result.success(pageBean);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    @Log
    public Result delete(@PathVariable List<Integer> ids){
        int updates = empService.deleteById(ids);
        if(updates>0){
            return Result.success();
        }else {
            return Result.error("删除失败，没有数据（来自后端响应）！");
        }
    }

    //新增员工
    @PostMapping
    @Log
    public Result add(@RequestBody Emp emp){
       empService.add(emp);
       return Result.success("新增成功！");
    }

    //通过ID查询员工
    @GetMapping("/{id}")
    public Result selectID(@PathVariable Integer id) {
        Emp emp = empService.selectById(id);
        return Result.success(emp);
    }

    //更新员工
    @PutMapping()
    @Log
    public Result update(@RequestBody Emp emp) {
        empService.update(emp);
        return Result.success();
    }

}
