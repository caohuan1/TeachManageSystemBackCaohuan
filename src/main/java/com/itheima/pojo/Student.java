package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学员实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id; //ID
    private String username; //用户名
    private String password; //密码
    private String name; //姓名
    private Short gender; //性别: 1 男, 2 女
    private String image; //头像url
    private String phone; //手机号
    private String idCard; //身份证号
    private Integer classId; //所属班级ID
    private String education; //学历
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
