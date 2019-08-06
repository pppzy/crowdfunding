package com.itpzy.crowdfunding.potal.service.impl;

import com.itpzy.crowdfunding.bean.MemberCert;
import com.itpzy.crowdfunding.potal.dao.MemberCertMapper;
import com.itpzy.crowdfunding.potal.service.MemberCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberCertServiceImpl implements MemberCertService {

    @Autowired
    private MemberCertMapper memberCertMapper;

    @Override
    public int insert(MemberCert memberCert) {
        return memberCertMapper.insert(memberCert);
    }

    @Override
    public  List<Map<String,Object>> selectById(Integer membId) {
        return memberCertMapper.selectByMembid(membId);
    }
}
