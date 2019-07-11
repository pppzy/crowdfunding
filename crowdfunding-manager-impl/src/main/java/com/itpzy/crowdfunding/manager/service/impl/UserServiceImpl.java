package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.dao.UserMapper;
import com.itpzy.crowdfunding.manager.service.UserService;
import com.itpzy.crowdfunding.util.DoLoginException;
import com.itpzy.crowdfunding.util.MD5Util;
import com.itpzy.crowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserDoLogin(Map usermap) {
        User user = userMapper.selectUserdoLogin(usermap);
        if(user==null){
            int i = userMapper.selectCountUser(usermap);
            if(i!=1){
                throw new DoLoginException("用户不存在!");
            }else{
                throw new DoLoginException("用户名密码不正确!");
            }
        }
        return user;
    }


    @Override
    public Page selectPage(Integer pageNo, Integer pageSize) {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setStartIndex((pageNo-1)*pageSize);
        //1.传入用户数据集合
        List<User> list = userMapper.selectPage(page.getStartIndex(),pageSize);
        page.setList(list);
        //2.调用查询记录总数
        int count = userMapper.selectCount();
        page.setTotalCount(count);
        page.setTotalPage(count%pageSize==0?count/pageSize:count/pageSize+1);
        return page;
    }


    @Override
    public Page selectPage(Map<String, Object> dataMap) {
        Integer pageNo = (Integer) dataMap.get("pageno");
        Integer pageSize = (Integer) dataMap.get("pagesize");
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setStartIndex((pageNo-1)*pageSize);
        dataMap.put("startIndex",page.getStartIndex());
        //1.传入用户数据集合
        List<User> list = userMapper.selectPage(dataMap);
        page.setList(list);
        //2.调用查询记录总数
        int count = userMapper.selectCount(dataMap);
        page.setTotalCount(count);
        page.setTotalPage(count%pageSize==0?count/pageSize:count/pageSize+1);

        return page;

    }

    @Override
    public int selectAcctRepeat(String loginacct) {
        int count = userMapper.selectRepeatUser(loginacct);
        return count;
    }

    @Override
    public int addUser(User user) {
        String regex_acct ="^[a-zA-Z0-9_-]{5,16}$";
        String regex_name = "(^[a-zA-Z0-9_-]{5,16}$)|(^[\\u2E80-\\u9FFF]{3,8})";
        String regex_email = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if(!user.getLoginacct().matches(regex_acct)){
            throw new DoLoginException("账户格式不正确!");
        }
        if(!user.getUsername().matches(regex_name)){
            throw new DoLoginException("用户名格式不正确");
        }
        if(!user.getEmail().matches(regex_email)){
            throw new DoLoginException("邮箱格式不正确");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        String pswd = MD5Util.digest("123");
        user.setCreatetime(createTime);
        user.setUserpswd(pswd);
        int insert = userMapper.insert(user);
        return insert;
    }

    @Override
    public User selectUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public int updateUser(User user) {
        String regex_name = "(^[a-zA-Z0-9_-]{5,16}$)|(^[\\u2E80-\\u9FFF]{3,8})";
        String regex_email = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        if(!user.getUsername().matches(regex_name)){
            throw new DoLoginException("用户名格式不正确");
        }
        if(!user.getEmail().matches(regex_email)){
            throw new DoLoginException("邮箱格式不正确");
        }
       int count =  userMapper.updateByPrimaryKey(user);
        return count;
    }

    @Override
    public int deleteUser(Integer id) {
        int i = userMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int deleteUserList(List<Integer> idList) {
      int count = 0;
        for (Integer integer : idList) {
            userMapper.deleteByPrimaryKey(integer);
            count++;
        }
        return count;
    }
}
