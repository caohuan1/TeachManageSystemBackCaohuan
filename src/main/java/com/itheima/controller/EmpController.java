package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.anno.MyLog;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    //修改密码
    @PutMapping("/updatePassword")
    @Log
    public Result updatePassword(HttpServletRequest request, @RequestBody Map<String, String> params) {
        try {
            //1. 从请求头获取token
            String token = request.getHeader("token");
            if (token == null || token.isEmpty()) {
                return Result.error("未登录");
            }
            
            //2. 解析token获取当前登录用户的id
            Claims claims = JwtUtils.parseJWT(token);
            Integer id = (Integer) claims.get("id");
            
            //3. 获取前端传入的密码
            String oldPassword = params.get("oldPassword");
            String newPassword = params.get("newPassword");
            
            if (oldPassword == null || oldPassword.isEmpty()) {
                return Result.error("请输入原密码");
            }
            if (newPassword == null || newPassword.isEmpty()) {
                return Result.error("请输入新密码");
            }
            
            //4. 调用service修改
            boolean success = empService.updatePassword(id, oldPassword, newPassword);
            if (success) {
                return Result.success("修改成功");
            }
            return Result.error("原密码错误");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return Result.error("修改密码失败，请稍后重试");
        }
    }

}
