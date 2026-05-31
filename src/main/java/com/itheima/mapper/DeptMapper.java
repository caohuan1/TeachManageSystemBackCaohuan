package com.itheima.mapper;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 部门管理
 */
@Mapper
public interface DeptMapper {
    @Select("select * from dept")
    List<Dept> list();

    @Delete("delete from dept where id = #{id}")
    int deleteById(int id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into dept (name, create_time, update_time)"+
            "values (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    @Select("select * from dept where id = #{id}")
    Dept select(int id);

    //("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    @Update("update dept set name = #{name} where id = #{id}")
    int update(Dept dept);
}
