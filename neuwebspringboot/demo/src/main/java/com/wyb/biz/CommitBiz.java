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
    public List<Commit> selectCommitsByContext(String context)
    {
        return commitMapper.selectCommitsByContext(context);
    }
    public int delCommitById(int commitId){
        return commitMapper.delCommitById(commitId);
    }
    public int insertCommit(Commit commit){
        return commitMapper.insertCommit(commit);
    }
    public int updateCommit(String context , int commitId){
        return commitMapper.updateCommit(context , commitId);
    }

}
