package com.itpzy.crowdfunding.potal.dao;

import com.itpzy.crowdfunding.bean.Member;

import java.util.Map;

public interface MemberMapper {

    int updateLoginAcctByPrimaryKey(Member record);

    Member selectUser(Map<String, Object> dataMap);

    int selectCount(Map<String, Object> dataMap);

    void updateBaseInfo(Member loginMember);

    void updateEmail(Member loginMember);

    void updateStatus(Member loginMember);


    int insert(Member member);

    Member selectMemberByPiid(String processInstanceId);


    Member selectMemberById(Integer membId);

    void passAudit(int memberId);

    void refuseAudit(int memberId);
}