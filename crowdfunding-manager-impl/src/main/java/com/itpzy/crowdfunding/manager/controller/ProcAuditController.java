package com.itpzy.crowdfunding.manager.controller;

import com.itpzy.crowdfunding.bean.Member;
import com.itpzy.crowdfunding.potal.service.MemberCertService;
import com.itpzy.crowdfunding.potal.service.MemberService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/procaudit")
public class ProcAuditController {



    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCertService memberCertService;








    @RequestMapping(value = "doPass")
    @ResponseBody
    public AjaxResult doPass(String taskId,Integer memberId){
        try {


            taskService.setVariable(taskId,"flag","true");
            taskService.setVariable(taskId,"memberId",memberId);
            taskService.complete(taskId);



            return AjaxResult.success("审核成功!");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

    }

    @RequestMapping(value = "doRefuse")
    @ResponseBody
    public AjaxResult doRefuse(String taskId,Integer memberId){
        try {


            taskService.setVariable(taskId,"flag","false");
            taskService.setVariable(taskId,"memberId",memberId);
            taskService.complete(taskId);



            return AjaxResult.success("审核不通过!");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

    }




    @RequestMapping(value = "toAuditProc")
    public String toAuditProc(Integer membId,Map map){



        Member member = memberService.selectMemberById(membId);

        List<Map<String,Object>> list = memberCertService.selectById(membId);

        map.put("member",member);
        map.put("memberCert",list);

        return "audit/auditProc";


    }




    //1.实名认证流程审核主页
    @RequestMapping(value = "/index")
    public String index(){
        return "audit/procaudit";
    }

    //2.异步请求：查询需要的数据并返回json
    @RequestMapping(value = "/ajaxTask")
    @ResponseBody
    public AjaxResult ajaxTask(@RequestParam(required = false,defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize){


        try {
            Page pageInfo = new Page();
            pageInfo.setPageNo(pageNo);
            pageInfo.setPageSize(pageSize);
            Integer startIndex = (pageNo-1)*pageSize;
            pageInfo.setStartIndex(startIndex);

            //1.准备数据容器
            List<Map<String,Object>> dataMap = new ArrayList<>();

            //2.查询正在执行的任务的集合
            TaskQuery taskQuery = taskService.createTaskQuery();
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            ProcessDefinition emailProcess = processDefinitionQuery.processDefinitionKey("emailProcess").latestVersion().singleResult();

            List<Task> taskList = taskQuery.processDefinitionId(emailProcess.getId()).taskCandidateGroup("backgroup").listPage(startIndex,pageSize);

            Long count = taskQuery.processDefinitionId(emailProcess.getId()).taskCandidateGroup("backgroup").count();

            pageInfo.setTotalCount(count.intValue());

            //3.遍历任务集合
            for (Task task : taskList) {
                Map<String,Object> map = new HashMap<>();
                //需要获取的数据
                // 任务id
                // 流程名称
                // 流程版本
                // 任务名称
                // 申请的会员名称
                // 流程实例id
                String id = task.getId();
                String taskName = task.getName();
                String processInstanceId = task.getProcessInstanceId();
                String proceName = emailProcess.getName();
                int version = emailProcess.getVersion();

                //查询出申请的会员信息
                Member member =  memberService.selectMemberByPiid(processInstanceId);

                //3.1查询出需要的消息后，封装进容器中
                map.put("taskId",id);
                map.put("name",taskName);
                map.put("procname",proceName);
                map.put("version",version);
                map.put("member",member);
                dataMap.add(map);
            }

            return AjaxResult.success("查询成功!").add("page",pageInfo).add("taskList",dataMap);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }


}
