package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 班级管理Mapper
 */
@Mapper
public interface ClazzMapper {
    @Select("select * from class")
    List<Clazz> list();

    @Delete("delete from class where id = #{id}")
    int deleteById(int id);

    @Insert("insert into class (name, begin_date, end_date, status, create_time, update_time)" +
            "values (#{name}, #{beginDate}, #{endDate}, #{status}, #{createTime}, #{updateTime})")
    void insert(Clazz clazz);

    @Select("select * from class where id = #{id}")
    Clazz select(int id);

    @Update("update class set name = #{name}, begin_date = #{beginDate}, end_date = #{endDate}, status = #{status}, update_time = #{updateTime} where id = #{id}")
    int update(Clazz clazz);
}
