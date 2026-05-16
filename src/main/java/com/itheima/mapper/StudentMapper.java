package com.itheima.mapper;

import com.itheima.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 学员管理Mapper
 */
@Mapper
public interface StudentMapper {
    // 分页查询学员列表
    List<Student> pageHelper(String name, Short gender, Integer classId, LocalDate begin, LocalDate end);

    // 批量删除学员
    int deleteByIds(List<Integer> ids);

    // 新增学员
    @Insert("insert into student (username, password, name, gender, image, phone, id_card, class_id, education, create_time, update_time)" +
            "values (#{username}, #{password}, #{name}, #{gender}, #{image}, #{phone}, #{idCard}, #{classId}, #{education}, #{createTime}, #{updateTime})")
    void insert(Student student);

    // 根据ID查询学员
    @Select("select * from student where id = #{id}")
    Student selectById(Integer id);

    // 更新学员信息
    void update(Student student);

    // 根据用户名和密码查询学员
    @Select("select * from student where username = #{username} and password = #{password}")
    Student getByUsernameAndPassword(Student student);

    // 根据班级ID删除学员
    @Delete("delete from student where class_id = #{classId}")
    void deleteByClassId(Integer classId);

    // 根据用户名查询学员
    @Select("select * from student where username = #{username}")
    Student getByUsername(String username);
}
