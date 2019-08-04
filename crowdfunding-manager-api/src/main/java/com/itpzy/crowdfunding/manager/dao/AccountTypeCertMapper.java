package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.AccountTypeCert;

import java.util.List;
import java.util.Map;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);

    //查找资质和账户类型的中间表信息
    List<Map<String,Object>> selectAcctTypeCert();

    //删除中间表的数据
    int deleteAccttypeCertid(AccountTypeCert accountTypeCert);
}