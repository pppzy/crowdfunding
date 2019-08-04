package com.itpzy.crowdfunding.manager.controller;

import com.itpzy.crowdfunding.bean.Advertisement;
import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.service.AdvertService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Const;
import com.itpzy.crowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequestMapping(value = "/advert")
@Controller
public class AdvertController {

    @Autowired
    private AdvertService advertService;


    //跳转到广告管理的主页面
    @RequestMapping(value = "/index")
    public String index(){
        return "advert/index";
    }
    //跳转到添加页面
    @RequestMapping(value = "/add")
    public String add(){
        return "advert/add";
    }

    //异步方式——查询广告信息
    @RequestMapping(value = "ajaxAdvertPage")
    @ResponseBody
    public AjaxResult ajaxRolePage(Page page, @RequestParam(required = false) String queryText){
        try {
            page  =   advertService.selectAllAdvert(page,queryText);
            return AjaxResult.success("查询成功!").add("list",page);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

    //同步的文件上传方式
  /*  @RequestMapping(value = "upload")
    public String upload(HttpServletRequest request, Advertisement advert, HttpSession session) throws IOException {
        MultipartRequest request1 = (MultipartRequest) request;
        MultipartFile upload = request1.getFile("upload");
        String realPath = session.getServletContext().getRealPath("/advert");
        String originalFilename = upload.getOriginalFilename();

        String fileName = originalFilename.substring(originalFilename.indexOf("."));
        String uuid = UUID.randomUUID().toString();
        String iconPath = uuid+fileName;
        String filePath = realPath+"//pic//"+iconPath;
        upload.transferTo(new File(filePath));

        advert.setIconpath(iconPath);
        advert.setStatus("1");
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        advert.setUserid(user.getId());

        int count = advertService.insert(advert);
        return "redirect:/advert/index.htm";
    }*/

    //异步方式的文件上传方式
    @RequestMapping(value = "upload")
    @ResponseBody
    public AjaxResult upload(HttpServletRequest request, Advertisement advert, HttpSession session) throws IOException {
        MultipartRequest request1 = (MultipartRequest) request;
        MultipartFile upload = request1.getFile("upload");
        String realPath = session.getServletContext().getRealPath("/advert");
        String originalFilename = upload.getOriginalFilename();

        String fileName = originalFilename.substring(originalFilename.indexOf("."));
        String uuid = UUID.randomUUID().toString();
        String iconPath = uuid+fileName;
        String filePath = realPath+"//pic//"+iconPath;
        upload.transferTo(new File(filePath));

        advert.setIconpath(iconPath);
        advert.setStatus("1");
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        advert.setUserid(user.getId());

        int count = advertService.insert(advert);
        if(count==1){
            return AjaxResult.success("提交表单成功!");
        }else{
            return AjaxResult.fail("提交表单数据失败!");
        }

    }


}
