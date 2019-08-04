package com.itpzy.crowdfunding.manager.service.impl;

import com.itpzy.crowdfunding.manager.service.ProcessService;
import com.itpzy.crowdfunding.util.Page;
import com.itpzy.crowdfunding.util.StringUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private RepositoryService repositoryService;



    @Override
    public List<ProcessDefinition> ajaxProcPage(Integer pageNo, Integer pageSize, String queryText) {
        Integer startIndex = (pageNo-1)*pageSize;
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if(StringUtil.isNotEmpty(queryText)){
            queryText="%"+queryText+"%";
            List<ProcessDefinition> processDefinitionList = processDefinitionQuery.processDefinitionNameLike(queryText).listPage(startIndex, pageSize);
            return processDefinitionList;
        }else{
            List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(startIndex, pageSize);
            return processDefinitionList;
        }

    }

    @Override
    public Page queryPageInfo(Integer pageNo, Integer pageSize, String queryText) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        long count = 0;

        if(StringUtil.isNotEmpty(queryText)){
             queryText="%"+queryText+"%";
             count = processDefinitionQuery.processDefinitionNameLike(queryText).count();
        }else{
             count = processDefinitionQuery.count();
        }

        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalCount((int)count);
        return page;
    }

    @Override
    public void addProcDef(HttpServletRequest request) throws IOException {
        MultipartRequest multipartRequest = (MultipartRequest)request;
        MultipartFile fuploadXml = multipartRequest.getFile("fuploadXml");
        MultipartFile fuploadPng = multipartRequest.getFile("fuploadPng");

        InputStream inputStreamXml = fuploadXml.getInputStream();
        InputStream inputStreamPng = fuploadPng.getInputStream();

        Deployment stream = repositoryService
                .createDeployment()
                .addInputStream(fuploadXml.getOriginalFilename(), inputStreamXml)
                .addInputStream(fuploadPng.getOriginalFilename(),inputStreamPng)
                .deploy();


    }

    @Override
    public void delProc(String id) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

        repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);
    }

}
