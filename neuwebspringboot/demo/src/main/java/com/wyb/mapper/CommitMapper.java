package com.wyb.mapper;

import com.wyb.entity.Code;
import com.wyb.entity.Commit;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommitMapper {
    @Select("select * from t_commit")
    public List<Commit> selectCommitsAll();

    @Select("select * from t_commit where userId=#{userId}")
    public List<Commit> selectCommitsByUserId(int userId);

    @Delete("delete * from t_commit where commitId=#{comId}")
    public int delCommitById(int comId);

    @Insert("insert into t_commit values (null,#{comConent},#{comLike},#{userId})")
    public int insertCommit(Commit commit);

}
