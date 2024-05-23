package com.wyb.biz;

import com.wyb.entity.Commit;
import com.wyb.mapper.CommitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitBiz {
    @Autowired
    CommitMapper commitMapper;

    public List<Commit> selectCommitsAll(){
        return commitMapper.selectCommitsAll();
    }
    public List<Commit> selectCommitsByUserId(int userId)
    {
        return commitMapper.selectCommitsByUserId(userId);
    }
    public int delCommitById(int comId){
        return commitMapper.delCommitById(comId);
    }
    public int insertCommit(Commit commit){
        return commitMapper.insertCommit(commit);
    }

}
