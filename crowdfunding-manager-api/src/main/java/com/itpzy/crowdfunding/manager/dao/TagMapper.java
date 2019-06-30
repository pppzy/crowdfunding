package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.Tag;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    Tag selectByPrimaryKey(Integer id);

    List<Tag> selectAll();

    int updateByPrimaryKey(Tag record);
}