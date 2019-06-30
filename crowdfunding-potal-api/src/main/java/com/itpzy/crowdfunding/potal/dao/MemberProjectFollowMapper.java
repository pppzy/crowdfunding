package com.itpzy.crowdfunding.potal.dao;

import com.itpzy.crowdfunding.bean.MemberProjectFollow;

import java.util.List;

public interface MemberProjectFollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberProjectFollow record);

    MemberProjectFollow selectByPrimaryKey(Integer id);

    List<MemberProjectFollow> selectAll();

    int updateByPrimaryKey(MemberProjectFollow record);
}