package com.itpzy.crowdfunding.manager.service;

import java.util.List;
import java.util.Map;

public interface AccountTypeCertService {

    //查找资质和账户类型的中间表信息
    List<Map<String,Object>> selectAcctTypeCert();

    //向中间表添加数据
    int addAccttypeCertid(String certid, String accttype);

    //向中间表删除数据
    int deleteAccttypeCertid(String certid, String accttype);
}
