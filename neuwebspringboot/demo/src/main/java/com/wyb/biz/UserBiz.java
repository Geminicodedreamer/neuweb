package com.wyb.biz;

import com.wyb.entity.User;
import com.wyb.mapper.CodeMapper;
import com.wyb.mapper.UserMapper;
import com.wyb.util.MD5;
import com.wyb.util.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.List;

@Service
public class UserBiz {
    @Autowired
    private UserMapper mapper;
    public User checkLogin(User user) throws NoSuchAlgorithmException {
        User dbUser = mapper.selectUserByTel(user.getTel());
        if(dbUser!=null && dbUser.getLoginPwd().equals(MD5.md5(user.getLoginPwd()))){
            return dbUser;
        }else {
            throw new MyException("登录失败，账号不存在或密码错误");
        }
    }

    public User getUserById(int userId){
        return mapper.selectUserById(userId);
    }
    public List<User> getUserAll(){
        return mapper.selectUserAll();
    }

    public List<User> getUserByName(String userName){
        return mapper.selectUserByName(userName);
    }

    public boolean delete(int userId)
    {
        return mapper.deleteUser(userId) > 0;
    }

    public boolean update(User user)
    {
        return mapper.updateUser(user) > 0;
    }

    public User register(User user)
    {
        mapper.insertUser(user);
        return user;
    }

    public void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    public User getUserByTel(String tel) {
        return mapper.selectUserByTel(tel);
    }
}
