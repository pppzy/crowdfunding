package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.Cert;

import java.util.List;

public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);

    //根据账户类型和中间表，查询出所对应的需要上传的资质
    List<Cert> selectCertByAcctType(String accttype);
}