package com.itpzy.crowdfunding.potal.controller;

import com.itpzy.crowdfunding.bean.Cert;
import com.itpzy.crowdfunding.bean.CertForm;
import com.itpzy.crowdfunding.bean.Member;
import com.itpzy.crowdfunding.bean.MemberCert;
import com.itpzy.crowdfunding.manager.service.CertService;
import com.itpzy.crowdfunding.potal.service.CertFormService;
import com.itpzy.crowdfunding.potal.service.MemberCertService;
import com.itpzy.crowdfunding.potal.service.MemberService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Const;
import com.itpzy.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/member")
public class MemberController {


    @Autowired
    private CertFormService certFormService;

    @Autowired
    private MemberService memberService;


    @Autowired
    private CertService certService;

    @Autowired
    private MemberCertService memberCertService;








    //跳转到选择账户类型页面
    @RequestMapping(value = "toSelectAcctType")
    public String toSelectAcctType(){
        return "apply/selectAcctType";
    }

    //跳转到基本信息页面
    @RequestMapping(value = "toBasicInfo")
    public String toBasicInfo(){
        return "apply/basicInfo";
    }


    //跳转到上传资质页面,根据资质和账户类型中间表查询出要上传的资质
    @RequestMapping(value = "toUploadCert")
    public String toUploadCert(HttpSession session, HttpServletRequest request){
        Member loginMember =  (Member)session.getAttribute(Const.LOGIN_MEMBER);
        List<Cert> certList =  certService.selectCertByAcctType(loginMember.getAccttype());

        request.setAttribute("certList",certList);

        return "apply/uploadCert";
    }



    //4.接收上传的多个文件域，并存储信息到t_member_cert的表中，并更新流程表单的 步骤记忆信息。apply3
    @RequestMapping(value = "uploadCert")
    @ResponseBody
    public AjaxResult uploadCert(Data datas, HttpSession session){

        try {

            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);

            //1.获取多个文件域信息，遍历并存储到数据库表中
            List<MultipartFile> files = datas.getFiles();
            List<Integer> certIds = datas.getIds();

            String realPath = session.getServletContext().getRealPath("/advert");

            int index = 1;
            int count=0;
            for (MultipartFile file : files) {
                //1.1文件路径拼接
                StringBuilder stringBuilder = new StringBuilder();
                String originalFilename = file.getOriginalFilename();
                String jsp = originalFilename.substring(originalFilename.indexOf("."));

                StringBuilder filePath = stringBuilder.append(realPath).append("/pic/").append(UUID.randomUUID().toString()).append(jsp);

                //1.2文件传输
                file.transferTo(new File(filePath.toString()));

                //1.3将资质信息和账户更新到数据库中
                Integer certid = certIds.get(index);
                MemberCert memberCert = new MemberCert();
                memberCert.setCertid(certid);
                memberCert.setIconpath(filePath.toString());
                memberCert.setMemberid(loginMember.getId());

                 count += memberCertService.insert(memberCert);

                 index++;
            }

            //2.进行更新流程表单的步骤记忆信息
            CertForm certForm = certFormService.queryForm(loginMember.getId());
            certForm.setLastproc("apply3");
            certFormService.updateLastProc(certForm);


            if(count==index){
                return AjaxResult.success("添加成功!");
            }else {
                return AjaxResult.fail("添加失败!");
            }


        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }



    //3.接收填写的基本信息，并更新到数据库中，并更新流程表单的步骤记忆
    @RequestMapping(value = "basicInfo")
    @ResponseBody
    public AjaxResult basicInfo(Member member, HttpSession session){

        try {

            //1.进行更新用户信息
            Member loginMember =  (Member)session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setRealname(member.getRealname());
            loginMember.setCardnum(member.getCardnum());
            loginMember.setTelphone(member.getTelphone());

            memberService.updateBaseInfo(loginMember);


            //2.进行更新流程表单的步骤记忆信息
            CertForm certForm = certFormService.queryForm(loginMember.getId());
            certForm.setLastproc("apply2");
            certFormService.updateLastProc(certForm);
            return AjaxResult.success("添加成功!");

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }






    //2.选择了账户类型后，更新用户信息和流程表单并跳转到下一页 basicInfo.htm
    @RequestMapping(value = "selectAcctType")
    public String selectAcctType(String accttype,HttpSession session){

        //1.进行更新用户信息
        Member loginMember =  (Member)session.getAttribute(Const.LOGIN_MEMBER);
        loginMember.setAccttype(accttype);

        memberService.updateLoginAcctByPrimaryKey(loginMember);


        //2.进行更新流程表单的步骤记忆信息
        CertForm certForm = certFormService.queryForm(loginMember.getId());
        certForm.setLastproc("apply1");
        certFormService.updateLastProc(certForm);

        return "redirect:/member/toBasicInfo";

    }




    //1.当点击实名认证后，跳转到此，这里作为一个中转站，生成流程单记录已进行的步骤，根据步骤记忆进行跳转到对应的页面
    @RequestMapping(value = "/apply")
    public String apply(HttpSession session){
        //1.从数据库中查询此用户的流程单信息
        Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
        CertForm certForm = certFormService.queryForm(loginMember.getId());

        //2.判断流程单，如果没有则创建一个并 添加到数据库中
        if(certForm==null){
            CertForm newCertForm = new CertForm();
            newCertForm.setMemid(loginMember.getId());
            newCertForm.setLastproc("apply0");
           int count =  certFormService.insert(newCertForm);

           return "redirect:/member/toSelectAcctType.htm";

        }else{
            //3.如果有，则根据流程单的步骤记忆信息，进行相应的跳转
            String a = certForm.getLastproc();
            String url = "";
            switch (a){

                case "apply0":
                    url="redirect:/member/toSelectAcctType.htm";
                    break;

                case "apply1":
                    url ="redirect:/member/toBasicInfo.htm";
                    break;


                case "apply2":
                    url= "redirect:/member/toUploadCert.htm";
                    break;

                case "apply3":
                    url= "apply/sendEmail";
                    break;

                case "apply4":
                    url ="apply/finishEmail";
                    break;

                    default:
                        url= "member/index";

            }
            return url;
        }
    }

}
