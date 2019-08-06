package com.itpzy.crowdfunding.potal.dao;

import com.itpzy.crowdfunding.bean.MemberCert;

import java.util.List;
import java.util.Map;

public interface MemberCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCert record);

    MemberCert selectByPrimaryKey(Integer id);

    List<MemberCert> selectAll();

    int updateByPrimaryKey(MemberCert record);

    List<Map<String,Object>> selectByMembid(Integer membId);
}