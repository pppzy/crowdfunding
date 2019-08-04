package com.itpzy.crowdfunding.potal.dao;

import com.itpzy.crowdfunding.bean.CertForm;

import java.util.List;

public interface CertFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CertForm record);

    CertForm selectByPrimaryKey(Integer id);

    List<CertForm> selectAll();

    int updateByPrimaryKey(CertForm record);

    //根据会员id查询出流程表单
    CertForm selectByMemid(Integer memid);

    //更新流程表单的步骤记忆信息
    void updateLastProc(CertForm certForm);
}