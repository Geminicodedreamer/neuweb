package com.wyb.mapper;

import com.wyb.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.ResultSet;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from t_user where userId=#{id}")
    public User selectUserById(int id);

    @Select("select * from t_user where userName like concat('%', #{userName}, '%')")
    public List<User> selectUserByName(String userName);

    @Select("select * from t_user")
    public List<User> selectUserAll();

    @Select("select * from t_user where tel=#{tel}")
    public User selectUserByTel(String tel);

    @Insert("insert into t_user values (null,#{tel},#{loginPwd},#{userName})")
    public int insertUser(User user);

    @Update("update t_user set tel=#{tel} , loginPwd=#{loginPwd} , userName=#{userName} where userId=#{userId}")
    public int updateUser(User user);

    @Delete("delete u , c , m from t_user as u left join t_code as c ON u.userId = c.userId left join t_commit as m on u.userId = m.userId where u.userId= #{userId}")
    public int deleteUser(int userId);
}
