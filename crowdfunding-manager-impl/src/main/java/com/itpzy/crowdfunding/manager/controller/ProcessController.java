package com.itpzy.crowdfunding.manager.controller;

import com.itpzy.crowdfunding.manager.service.ProcessService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private RepositoryService repositoryService;


    //跳转到流程管理主页面
    @RequestMapping(value = "index")
    public String index(){
        return "process/index";
    }

    //分页查询流程
    @RequestMapping(value = "ajaxProcPage")
    @ResponseBody
    public AjaxResult ajaxProcPage(@RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                   @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                                   String queryText){


        try {

            List<ProcessDefinition> processDefinitionList =  processService.ajaxProcPage(pageNo,pageSize,queryText);

            List<Map<String,Object>> myProcList = new ArrayList<>();


            for (ProcessDefinition processDefinition : processDefinitionList) {
                Map<String,Object> map = new HashMap<>();
                map.put("id",processDefinition.getId());
                map.put("name",processDefinition.getName());
                map.put("key",processDefinition.getKey());
                map.put("version",processDefinition.getVersion());
                myProcList.add(map);

            }
            Page pageInfo = processService.queryPageInfo(pageNo,pageSize,queryText);
            return AjaxResult.success("查询成功!").add("pageInfo",pageInfo).add("procList",myProcList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return AjaxResult.fail(e.getMessage());
        }
    }

    //添加自定义的流程
    @RequestMapping(value = "addProcDef")
    @ResponseBody
    public AjaxResult addProcDef(HttpServletRequest request){

        try {
            processService.addProcDef(request);
            return AjaxResult.success("添加成功!");
        }catch (Exception e){
            System.out.println(e);
            return AjaxResult.fail(e.getMessage());
        }

    }

    @RequestMapping(value = "delProc")
    @ResponseBody
    public AjaxResult delProc(String id){
        try {
            processService.delProc(id);
            return AjaxResult.success("删除成功!");
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "toShowImg")
    public String toShowImg(){
        return "process/showImg";
    }

    @RequestMapping(value = "doShowImg")
    public void doShowImg(String id, HttpServletResponse response) throws IOException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
        String diagramResourceName = processDefinition.getDiagramResourceName();

        InputStream in = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);
        ServletOutputStream outputStream = response.getOutputStream();

//        int len1 = in.read();
//        while (len1!=-1){
//            outputStream.write(len1);
//        }

        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len=in.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
        }

    }

}
