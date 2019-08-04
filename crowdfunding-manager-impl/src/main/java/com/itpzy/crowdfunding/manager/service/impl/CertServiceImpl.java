package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.bean.Cert;
import com.itpzy.crowdfunding.manager.dao.CertMapper;
import com.itpzy.crowdfunding.manager.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertServiceImpl implements CertService {

    @Autowired
    private CertMapper certMapper;


    @Override
    public List<Cert> selectAll() {
        return certMapper.selectAll();
    }

    @Override
    public List<Cert> selectCertByAcctType(String accttype) {
        return certMapper.selectCertByAcctType(accttype);
    }

}
