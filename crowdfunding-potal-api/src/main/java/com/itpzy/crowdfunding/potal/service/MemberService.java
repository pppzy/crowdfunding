package com.itpzy.crowdfunding.potal.service;

import com.itpzy.crowdfunding.bean.Member;

import java.util.Map;

public interface MemberService {

    //验证用户登录
    Member selectUserDoLogin(Map<String,Object> dataMap);

    //更新会员信息
    void updateLoginAcctByPrimaryKey(Member loginMember);

    //更新BaseInfo
    void updateBaseInfo(Member loginMember);
}
