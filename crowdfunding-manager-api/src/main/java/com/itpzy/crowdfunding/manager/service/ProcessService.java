package com.itpzy.crowdfunding.manager.service;

import com.itpzy.crowdfunding.util.Page;
import org.activiti.engine.repository.ProcessDefinition;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProcessService {

    List<ProcessDefinition> ajaxProcPage(Integer pageNo, Integer pageSize, String queryText);

    Page queryPageInfo(Integer pageNo, Integer pageSize, String queryText);

    void addProcDef(HttpServletRequest request) throws IOException;

    void delProc(String id);


}
