package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.ProjectTag;

import java.util.List;

public interface ProjectTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectTag record);

    ProjectTag selectByPrimaryKey(Integer id);

    List<ProjectTag> selectAll();

    int updateByPrimaryKey(ProjectTag record);
}