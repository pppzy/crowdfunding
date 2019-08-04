package com.itpzy.crowdfunding.vo;

import com.itpzy.crowdfunding.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class Data {

    //批量增删改时，前端提交多条user数据
    private List<User> userList = new ArrayList<User>();

    //批量增删改操作时，前端提交多条user数据
    private List<User> datas  = new ArrayList<User>();

    //批量操作时，前端提交多条id Integer简单类型数据
    private List<Integer> ids;

    //用于接收前端提交的多个 文件域
    private List<MultipartFile> files;



    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
