package com.itheima.aop;

import com.alibaba.fastjson.JSONObject;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(com.itheima.anno.Log)")
    public Object rerocdLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取操作人ID   operateUser
        String jwt = request.getHeader("token");//通过注入HttpServletRequest，使用getHeader方法获取token的值
        Claims claims = JwtUtils.parseJWT(jwt);//解析JWT令牌，见LoginController.login()方法，封装了id信息。
        Integer operateUser = (Integer) claims.get("id");//因为login()方法封装了id信息所以直接使用get获取

        //获取操作时间    operateTime
        LocalDateTime operateTime =LocalDateTime.now();

        //获取操作类名    className
        String className = joinPoint.getTarget().getClass().getName();

        //获取操作方法名   methodName
        String methodName = joinPoint.getSignature().getName();

        //获取操作方法参数  methodParams
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        Long begin =System.currentTimeMillis();//记录方法执行前的时间
        Object result = joinPoint.proceed();//执行原始方法
        Long end =System.currentTimeMillis();//记录方法执行后的时间

        //获取操作方法返回值 returnValue
        String returnValue = JSONObject.toJSONString(result);

        Long costTime = end - begin;//获取操作耗时    costTime

        OperateLog operateLog =new OperateLog
                (null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);
        log.info("利用AOP思想记录操作日志：{}",operateLog);

        return result;
    }

}
