package com.itpzy.crowdfunding.potal.service;

import com.itpzy.crowdfunding.bean.MemberCert;

public interface MemberCertService {

    //存储用户对应的资质信息
    int insert(MemberCert memberCert);
}
