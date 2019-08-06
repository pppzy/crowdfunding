package com.itpzy.crowdfunding.potal.service;

import com.itpzy.crowdfunding.bean.MemberCert;

import java.util.List;
import java.util.Map;

public interface MemberCertService {

    //存储用户对应的资质信息
    int insert(MemberCert memberCert);

    //根据Id查询出资质用户信息
    List<Map<String,Object>> selectById(Integer membId);
}
