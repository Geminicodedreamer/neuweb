package com.wyb.mapper;

import com.wyb.entity.Code;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeMapper {
    @Select("select * from t_code where userId=#{id}")
    List<Code> selectCodes(int id);

    @Select("select * from t_code")
    List<Code> selectCodesAll();

    @Select("select * from t_code where codeId=#{id}")
    Code selectCodeById(int id);

    @Select("select * from t_code where title=#{title}")
    List<Code> selectCodeByTitle(String title);

    @Select("select * from t_code where title=#{title} and userId=#{userId}")
    List<Code> selectUserCodeByTitle(@Param("userId") int userId , @Param("title") String title);

    @Insert("insert into t_code values(null , #{title} , #{code} , #{userId})")
    void insertCode(Code code);

    @Delete("delete from t_code where codeId=#{id};")
    int deleteCodeById(int id);

    @Update("update t_code set title=#{title} , code=#{code} where codeId=#{codeId}")
    int updateCodeById(@Param("title") String title, @Param("code") String code, @Param("codeId") int codeId);


}
