package com.itpzy.crowdfunding.manager.controller;

import com.itpzy.crowdfunding.bean.Cert;
import com.itpzy.crowdfunding.manager.service.AccountTypeCertService;
import com.itpzy.crowdfunding.manager.service.CertService;
import com.itpzy.crowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cert")
public class CertController {

    @Autowired
    private CertService certService;

    @Autowired
    private AccountTypeCertService accountTypeCertService;


    //跳转到资质管理主页面
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request){
        List<Cert> certs =  certService.selectAll();

        request.setAttribute("CertList",certs);

        return "cert/index";
    }

    //跳转到分类管理主页面（资质和账户类型的对应）
    @RequestMapping(value = "accountTypeCert")
    public String accountTypeCert(HttpServletRequest request){
        List<Cert> certs = certService.selectAll();

        request.setAttribute("certsList",certs);

        List<Map<String,Object>>  dataList =   accountTypeCertService.selectAcctTypeCert();


        request.setAttribute("acctTypeCert",dataList);

        System.out.println(dataList);

        return "cert/accountTypeCert";

    }

    @RequestMapping(value = "addAccttypeCertid")
    @ResponseBody
    public AjaxResult addAccttypeCertid(String certid,String accttype ){

        try {
            int count =  accountTypeCertService.addAccttypeCertid(certid,accttype);

            if(count==1){
                return AjaxResult.success("增加成功!");
            }else {
                return AjaxResult.fail("添加失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

    }

    @RequestMapping(value = "deleteAccttypeCertid")
    @ResponseBody
    public AjaxResult deleteAccttypeCertid(String certid,String accttype ){

        try {
            int count =  accountTypeCertService.deleteAccttypeCertid(certid,accttype);

            if(count==1){
                return AjaxResult.success("删除成功!");
            }else {
                return AjaxResult.fail("删除失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

    }
}
