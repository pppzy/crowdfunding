package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.Cert;

import java.util.List;

public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);
}