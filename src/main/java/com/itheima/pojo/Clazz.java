package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 班级实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clazz {
    private Integer id; //ID
    private String name; //班级名称
    private LocalDate beginDate; //开班日期
    private LocalDate endDate; //结课日期
    private Short status; //班级状态: 1 在读, 2 已结课
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
