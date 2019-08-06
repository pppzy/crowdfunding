package com.itpzy.crowdfunding.potal.activiti.listener;

import com.itpzy.crowdfunding.potal.service.CertFormService;
import com.itpzy.crowdfunding.potal.service.MemberService;
import com.itpzy.crowdfunding.util.ApplicationUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

public class NoListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {

        ApplicationContext ac = ApplicationUtil.applicationContext;

        int memberId = (int)execution.getVariable("memberId");

        MemberService memberService = ac.getBean(MemberService.class);
        CertFormService certFormService = ac.getBean(CertFormService.class);

        memberService.refuseAudit(memberId);

        certFormService.passAudit(memberId);
    }
}
