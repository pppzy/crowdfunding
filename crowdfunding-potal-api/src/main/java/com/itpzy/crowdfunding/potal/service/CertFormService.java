package com.itpzy.crowdfunding.potal.service;

import com.itpzy.crowdfunding.bean.CertForm;

public interface CertFormService {


    //根据会员id查询流程表单
    CertForm queryForm(Integer memid);

    //向数据库添加表单信息
    int insert(CertForm newCertForm);

    //更新步骤记忆信息
    void updateLastProc(CertForm certForm);

    //更新表信息
    void update(CertForm certForm);

    //更新表单的完成状态和步骤记忆
    void updateFinish(CertForm certForm);

    //更新表单，完成流程
    void passAudit(int memberId);


}
