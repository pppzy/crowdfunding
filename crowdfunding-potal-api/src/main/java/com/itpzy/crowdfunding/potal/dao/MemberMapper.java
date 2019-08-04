package com.itpzy.crowdfunding.potal.dao;

import com.itpzy.crowdfunding.bean.Member;

import java.util.Map;

public interface MemberMapper {

    int updateLoginAcctByPrimaryKey(Member record);

    Member selectUser(Map<String, Object> dataMap);

    int selectCount(Map<String, Object> dataMap);

    void updateBaseInfo(Member loginMember);
}