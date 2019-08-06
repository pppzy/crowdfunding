package com.itpzy.crowdfunding.potal.controller;

import com.itpzy.crowdfunding.bean.Cert;
import com.itpzy.crowdfunding.bean.CertForm;
import com.itpzy.crowdfunding.bean.Member;
import com.itpzy.crowdfunding.bean.MemberCert;
import com.itpzy.crowdfunding.manager.service.CertService;
import com.itpzy.crowdfunding.potal.activiti.listener.NoListener;
import com.itpzy.crowdfunding.potal.activiti.listener.YesListener;
import com.itpzy.crowdfunding.potal.service.CertFormService;
import com.itpzy.crowdfunding.potal.service.MemberCertService;
import com.itpzy.crowdfunding.potal.service.MemberService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Const;
import com.itpzy.crowdfunding.util.MD5Util;
import com.itpzy.crowdfunding.vo.Data;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

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


    @Autowired
    private RepositoryService repositoryService;


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;









    @RequestMapping(value = "/regMember")
    @ResponseBody
    public AjaxResult regMember(Member member){

        String userpswd = member.getUserpswd();
        String digest = MD5Util.digest(userpswd);

        member.setUserpswd(digest);

        int count =  memberService.insert(member);
       if(count==1){
           return AjaxResult.success("注册成功!");
       }else{
           return AjaxResult.fail("注册失败!");
       }

    }

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


    //跳转到发送邮箱验证码页面
    @RequestMapping(value = "toSendEmail")
    public String toSendEmail(){
        return "apply/sendEmail";
    }


    //跳转到审核验证码页面
    @RequestMapping(value = "toFinishEmail")
    public String toFinishEmail(){
        return "apply/finishEmail";
    }




    //6.获取激活码，判断并更新 流程表单和会员表信息
    @RequestMapping(value = "finishEmail")
    @ResponseBody
    public AjaxResult finishEmail(String code,HttpSession session){
        //1.获取流程单中的 code 激活码
        Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
        CertForm certForm = certFormService.queryForm(loginMember.getId());
        String codeEmail = certForm.getCode();

        //2.与用户提交的激活码作判断

        //2.1如果激活码匹配,则以用户名 完成该流程
        if(code!=null&&code!=""&&code.equals(codeEmail)){

            TaskQuery taskQuery = taskService.createTaskQuery();

            Task task = taskQuery.processInstanceId(certForm.getPiid()).taskAssignee(loginMember.getLoginacct()).active().singleResult();

            taskService.complete(task.getId());

         //3.更新流程表单的信息

            certForm.setLastproc("finish");
            certFormService.updateLastProc(certForm);

         //4.更新会员表信息
            loginMember.setAuthstatus("1");
            memberService.updateStatus(loginMember);


            return AjaxResult.success("申请成功!请耐心等待后台审核");
        }else{

            return AjaxResult.fail("验证码错误，审核失败!");
        }

    }




    //5.收到用户提交的邮箱的异步请求，判断邮箱是否做了更改，开启流程实例，并传入各项参数
    @RequestMapping(value = "sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(String email,HttpSession session){


        try {

            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);

            //1.判断用户对邮箱信息是否做了更改
            String emailMem = loginMember.getEmail();

            if(email!=null&&!email.equals(emailMem)){
                //1.1如果更改了，则更新信息
                loginMember.setEmail(email);
                memberService.updateEmail(loginMember);
            }

            //2.启动发送邮件至后台审批的流程实例
            ProcessDefinition emailProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey("emailProcess").latestVersion().singleResult();

            //2.1准备流程实例所需要的数据
            Map<String,Object> dataMap = new HashMap<>();
            //getEmail
            //code
            //member
            //yesListener
            //noListener
            //flag    --后台审核时
            StringBuffer code = new StringBuffer();
            dataMap.put("getEmail",email);
            for(int i=0;i<=3;i++){
                code.append(new Random().nextInt(10));
            }
            dataMap.put("code",code.toString());
            dataMap.put("yesListener",new YesListener());
            dataMap.put("noListener",new NoListener());
            dataMap.put("member",loginMember.getLoginacct());

            ProcessInstance processInstance = runtimeService.startProcessInstanceById(emailProcess.getId(),dataMap);

            //3.启动流程实例后，更新 数据库中的 流程表单 信息
            CertForm certForm = new CertForm();
            certForm.setPiid(processInstance.getId());
            certForm.setLastproc("apply4");
            certForm.setCode(code.toString());
            certForm.setMemid(loginMember.getId());
            certFormService.update(certForm);

            return AjaxResult.success("发送成功!");

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

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

            int index = 0;
            int count=0;
            for (MultipartFile file : files) {
                //1.1文件路径拼接
                StringBuilder stringBuilder = new StringBuilder();

                String originalFilename = file.getOriginalFilename();
                String jsp = originalFilename.substring(originalFilename.indexOf("."));

                String uuid = UUID.randomUUID().toString();

                String filePath = stringBuilder.append(realPath).append("/pic/").append(uuid).append(jsp).toString();

                String iconpath = uuid+jsp;


                //1.2文件传输
                file.transferTo(new File(filePath));

                //1.3将资质信息和账户更新到数据库中
                Integer certid = certIds.get(index);
                MemberCert memberCert = new MemberCert();
                memberCert.setCertid(certid);
                memberCert.setIconpath(iconpath);
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

        return "redirect:/member/toBasicInfo.htm";

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
                    url= "redirect:/member/toSendEmail.htm";
                    break;

                case "apply4":
                    url ="redirect:/member/toFinishEmail.htm";
                    break;

                    default:
                        url= "member/index";

            }
            return url;
        }
    }

}
