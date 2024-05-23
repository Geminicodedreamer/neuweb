package com.wyb.controller;

import com.mysql.cj.Session;
import com.wyb.biz.UserBiz;
import com.wyb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//@Controller//声明这个类是spring项目中的控制器类（相当于Servlet）
//@ResponseBody
@RestController//Controller+ResponseBody
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBiz biz;
    @RequestMapping("/register")
    public Map register(User user){
        User existingUser = biz.getUserByTel(user.getTel());
        Map<String, Object> map = new HashMap<>();
        if (existingUser != null) {
            map.put("isOk", false);
            map.put("msg", "手机号已被注册");
        } else {
            User registeredUser = biz.register(user);
            map.put("user", registeredUser);
            map.put("isOk", true);
            map.put("msg", "注册成功");
        }
        return map;
    }

    @RequestMapping("/all")
    public Map getUserAll()
    {
        Map map = new HashMap();
        List<User> list = biz.getUserAll();
        if(list == null){
            map.put("isOk", false);
            map.put("msg", "加载失败");
        }else {
            map.put("isOk", true);
            map.put("users" , list);
            map.put("msg", "加载成功");
        }
        return map;
    }

    @RequestMapping("/search")
    public Map searchUserByName(String userName){
        Map map = new HashMap();
        List<User> list = biz.getUserByName(userName);
        map.put("isOk", true);
        map.put("users" , list);
        map.put("msg", "加载成功");
        return map;
    }

    @RequestMapping("/update")
    public Map update(User user){
        Map map = new HashMap();
        if(biz.update(user)){
            map.put("isOk", true);
            map.put("msg", "修改成功");
        }
        else {
            map.put("isOk", false);
            map.put("msg", "修改失败");
        }
        return map;
    }

    @RequestMapping("/login")
    public Map login(User user, HttpSession session) throws NoSuchAlgorithmException {
        user = biz.checkLogin(user);
        session.setAttribute("login_user",user);
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("user",user);
        map.put("msg","登录成功");
        return map;
    }

    @RequestMapping("/islogin")
    public Map islogin(HttpServletRequest request){
        User user= (User)request.getSession().getAttribute("login_user");
        User newuser = biz.getUserById(user.getUserId());
        Map map = new HashMap();
        if(user == null)
        {
            map.put("isOk",false);
            map.put("msg","请登录后使用网站");
        }
        else{
            map.put("isOk",true);
            map.put("username" , newuser.getUserName());
            map.put("userId" , newuser.getUserId());
            map.put("msg","登录成功");
        }
        return map;
    }

    @RequestMapping("/logout")
    public Map login(HttpServletRequest request){
        request.getSession().removeAttribute("login_user");
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("msg","账号已安全退出");
        return map;
    }

    @RequestMapping("/userinfo")
    public Map getUserInfo(HttpServletRequest request)
    {
        User user= (User)request.getSession().getAttribute("login_user");
        User newuser = biz.getUserById(user.getUserId());
        Map res = new HashMap();
        if(user == null)
        {
            res.put("isOk", false);
        }
        else {
            res.put("isOk", true);
            res.put("user", newuser);
        }
        return res;
    }

    @RequestMapping("/del")
    public Map del(int userId,HttpServletRequest request){
        Map map = new HashMap();
        if(biz.delete(userId)) {
            request.getSession().removeAttribute("login_user");
            map.put("isOk", true);
            map.put("msg", "账号已注销");
        } else {
            map.put("isOk", false);
            map.put("msg", "账号未成功注销");
        }
        return map;
    }

    public void setBiz(UserBiz biz) {
        this.biz = biz;
    }
}
