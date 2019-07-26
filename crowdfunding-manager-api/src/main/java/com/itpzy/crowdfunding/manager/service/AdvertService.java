package com.itpzy.crowdfunding.manager.service;

import com.itpzy.crowdfunding.bean.Advertisement;
import com.itpzy.crowdfunding.util.Page;

public interface AdvertService  {
    //分页查询所有广告信息
    Page selectAllAdvert(Page page, String queryText);

    //添加广告信息
    int insert(Advertisement advert);
}
