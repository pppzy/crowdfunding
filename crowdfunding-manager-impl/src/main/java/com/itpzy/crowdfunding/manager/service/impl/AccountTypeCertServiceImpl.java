package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.bean.AccountTypeCert;
import com.itpzy.crowdfunding.manager.dao.AccountTypeCertMapper;
import com.itpzy.crowdfunding.manager.service.AccountTypeCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountTypeCertServiceImpl implements AccountTypeCertService {

    @Autowired
    private AccountTypeCertMapper accountTypeCertMapper;



    @Override
    public List<Map<String, Object>> selectAcctTypeCert() {

        return accountTypeCertMapper.selectAcctTypeCert() ;
    }

    @Override
    public int addAccttypeCertid(String certid, String accttype) {
        AccountTypeCert accountTypeCert = new AccountTypeCert();

        accountTypeCert.setCertid(Integer.parseInt(certid));
        accountTypeCert.setAccttype(accttype);

        return accountTypeCertMapper.insert(accountTypeCert);
    }

    @Override
    public int deleteAccttypeCertid(String certid, String accttype) {
        AccountTypeCert accountTypeCert = new AccountTypeCert();

        accountTypeCert.setCertid(Integer.parseInt(certid));
        accountTypeCert.setAccttype(accttype);

        return accountTypeCertMapper.deleteAccttypeCertid(accountTypeCert);
    }

}
