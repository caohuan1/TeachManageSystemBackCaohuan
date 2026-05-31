package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface EmpMapper {
    //统计返回总数量
    @Select("select count(*) from emp ")
    Integer count();

    //分页查询
    @Select("select * from emp limit #{start},#{pageSize}")
    List<Emp> page(Integer start, Integer pageSize );

    //使用PageHelper包实现分页查询
//    @Select("select * from emp")
    List<Emp> pageHelper(String name, Short gender, LocalDate begin, LocalDate end);//注意：此处方法名不能使用page()，也就是不能使用方法重载，否则springboot项目启动会报错

    int deleteByIds(List<Integer> ids) ;

    @Select("insert into emp (id, username, name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            "values (#{id},#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    @Select("select * from emp where id = #{id}")
    Emp selectById(Integer id);

    void update(Emp emp);

    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp getByUsernameAndPassword(Emp emp);

    @Select("delete from emp where dept_id = #{deptId}")
    void deleteByDeptId(Integer deptId);

    @Select("select * from emp where dept_id = #{deptId}")
    List<Emp> selectByDeptId(Integer deptId);

    @Update("update emp set dept_id = 0 where dept_id = #{deptId}")
    int updateDeptIdToZero(Integer deptId);

    @Update("update emp set password = #{newPassword}, update_time = #{updateTime} where id = #{id}")
    void updatePassword(@Param("id") Integer id, @Param("newPassword") String newPassword, @Param("updateTime") LocalDateTime updateTime);
}
