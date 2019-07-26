package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.bean.Advertisement;
import com.itpzy.crowdfunding.manager.dao.AdvertisementMapper;
import com.itpzy.crowdfunding.manager.service.AdvertService;
import com.itpzy.crowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public Page selectAllAdvert(Page page, String queryText) {
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        int totalCount = 0;
        //总记录数
        totalCount = advertisementMapper.selectCountWithText(queryText);

        page.setTotalCount(totalCount);
        //总页数
        int totalPage = totalCount%pagesize==0?totalCount/pagesize:totalCount/pagesize+1;
        page.setTotalPage(totalPage);

        int startIndex = (pageno-1)*pagesize;
        page.setStartIndex(startIndex);
        List<Advertisement> advertList = advertisementMapper.selectAdvert(page,queryText);

        page.setList(advertList);
        return page ;
    }

    @Override
    public int insert(Advertisement advert) {
        return advertisementMapper.insert(advert);
    }
}
