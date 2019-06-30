package com.itpzy.crowdfunding.potal.dao;

import com.itpzy.crowdfunding.bean.Member;

import java.util.List;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);
}