package com.itpzy.crowdfunding.potal.service.impl;

import com.itpzy.crowdfunding.bean.Member;
import com.itpzy.crowdfunding.exception.DoLoginException;
import com.itpzy.crowdfunding.potal.dao.MemberMapper;
import com.itpzy.crowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member selectUserDoLogin(Map<String, Object> dataMap) {

        Member member = memberMapper.selectUser(dataMap);
        if(member==null){
            int i = memberMapper.selectCount(dataMap);
            if(i!=1){
                throw new DoLoginException("会员账户不存在!");
            }else{
                throw new DoLoginException("会员密码不正确!");
            }
        }
        return member;
    }

    @Override
    public void updateLoginAcctByPrimaryKey(Member loginMember) {
        memberMapper.updateLoginAcctByPrimaryKey(loginMember);
    }

    @Override
    public void updateBaseInfo(Member loginMember) {
        memberMapper.updateBaseInfo(loginMember);
    }
}
