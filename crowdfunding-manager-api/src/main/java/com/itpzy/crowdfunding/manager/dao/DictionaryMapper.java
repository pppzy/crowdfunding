package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.Dictionary;

import java.util.List;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dictionary record);

    Dictionary selectByPrimaryKey(Integer id);

    List<Dictionary> selectAll();

    int updateByPrimaryKey(Dictionary record);
}