package com.itpzy.crowdfunding.potal.service.impl;

import com.itpzy.crowdfunding.bean.MemberCert;
import com.itpzy.crowdfunding.potal.dao.MemberCertMapper;
import com.itpzy.crowdfunding.potal.service.MemberCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCertServiceImpl implements MemberCertService {

    @Autowired
    private MemberCertMapper memberCertMapper;

    @Override
    public int insert(MemberCert memberCert) {
        return memberCertMapper.insert(memberCert);
    }
}
