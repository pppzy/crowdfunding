package com.itpzy.crowdfunding.potal.service.impl;

import com.itpzy.crowdfunding.bean.CertForm;
import com.itpzy.crowdfunding.potal.dao.CertFormMapper;
import com.itpzy.crowdfunding.potal.service.CertFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertFormServiceImpl implements CertFormService {

    @Autowired
    private CertFormMapper certFormMapper;

    @Override
    public CertForm queryForm(Integer memid) {
        return certFormMapper.selectByMemid(memid);
    }

    @Override
    public int insert(CertForm newCertForm) {
        return certFormMapper.insert(newCertForm);
    }

    @Override
    public void updateLastProc(CertForm certForm) {
        certFormMapper.updateLastProc(certForm) ;
    }
}
