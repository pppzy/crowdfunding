package com.itpzy.crowdfunding.manager.dao;

import com.itpzy.crowdfunding.bean.Advertisement;
import com.itpzy.crowdfunding.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    //查询总记录数
    int selectCount();

    int  selectCountWithText(String queryText);
    //分页/按字段模糊查询  所有的广告
    List<Advertisement> selectAdvert(@Param("page") Page page, @Param("queryText") String queryText);
}