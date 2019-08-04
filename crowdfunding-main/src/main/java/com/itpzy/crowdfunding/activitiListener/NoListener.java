package com.itpzy.crowdfunding.activitiListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class NoListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String currentActivityId = delegateExecution.getCurrentActivityId();
        String currentActivityName = delegateExecution.getCurrentActivityName();
        String processInstanceId = delegateExecution.getProcessInstanceId();
        System.out.println(currentActivityId);
        System.out.println(currentActivityName);
        System.out.println(processInstanceId);
        System.out.println("流程没有通过!");
    }
}
