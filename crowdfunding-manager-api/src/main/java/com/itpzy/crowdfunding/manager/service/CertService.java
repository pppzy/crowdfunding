package com.itpzy.crowdfunding.manager.service;

import com.itpzy.crowdfunding.bean.Cert;

import java.util.List;

public interface CertService {

    //查找所有的资质信息
    List<Cert> selectAll();

    //根据账户类型查询出对应的资质
    List<Cert> selectCertByAcctType(String accttype);
}
