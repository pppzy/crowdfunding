package com.itpzy.crowdfunding.activitiListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class YesListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("流程通过拉!");
    }
}
