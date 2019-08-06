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

    //更新Email信息
    void updateEmail(Member loginMember);

    //更新审核状态信息
    void updateStatus(Member loginMember);

    //注册用户会员
    int insert(Member member);

    //根据piid查询出会员信息
    Member selectMemberByPiid(String processInstanceId);

    //根据id查询出会员信息
    Member selectMemberById(Integer membId);

    //完成实名认证
    void passAudit(int memberId);
    //完成实名认证
    void refuseAudit(int memberId);
}
