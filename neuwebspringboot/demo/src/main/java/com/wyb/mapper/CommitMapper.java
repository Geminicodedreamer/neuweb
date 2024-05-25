package com.wyb.mapper;

import com.wyb.entity.Code;
import com.wyb.entity.Commit;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommitMapper {
    @Select("select * from t_commit")
    public List<Commit> selectCommitsAll();

    @Select("select * from t_commit where context like concat('%', #{context}, '%')")
    public List<Commit> selectCommitsByContext(String context);

    @Delete("delete from t_commit where commitId=#{commitId}")
    public int delCommitById(int commitId);

    @Insert("insert into t_commit values (null,#{context},#{userId})")
    public int insertCommit(Commit commit);

    @Update("update t_commit set context=#{context} where commitId=#{commitId}")
    public int updateCommit(@Param("context") String context ,@Param("commitId") int commitId);

}
