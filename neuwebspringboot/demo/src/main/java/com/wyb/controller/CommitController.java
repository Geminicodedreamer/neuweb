package com.wyb.controller;

import com.wyb.biz.CommitBiz;
import com.wyb.entity.Code;
import com.wyb.entity.Commit;
import com.wyb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commit")
public class CommitController {
    @Autowired
    CommitBiz biz;

    @RequestMapping("/add")
    public Map add(Commit commit , HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("login_user");
        commit.setUserId(user.getUserId());
        biz.insertCommit(commit);
        Map result = new HashMap<>();
        result.put("isOk",true);
        result.put("msg","添加成功");
        return result;
    }

    @RequestMapping("/all")
    public Map getAllCommits(){
        List<Commit> list = biz.selectCommitsAll();
        Map result = new HashMap<>();
        result.put("isOk",true);
        result.put("msg","加载成功");
        result.put("commits",list);
        return result;
    }

    @RequestMapping("/search")
    public Map searchCommitByUserId(int  userId){
        List<Commit> list = biz.selectCommitsByUserId(userId);
        Map result = new HashMap<>();
        result.put("isOk",true);
        result.put("msg","查询成功");
        result.put("commits",list);
        return result;
    }

    @RequestMapping("/del")
    public Map del(int id){
        Map result = new HashMap<>();
        if(this.biz.delCommitById(id) > 0){
            result.put("isOk",true);
            result.put("msg","删除成功");
        }else{
            result.put("isOk",false);
            result.put("msg","删除失败");
        }

        return result;
    }


    public void setCommitBiz(CommitBiz biz) {
        this.biz = biz;
    }
}
