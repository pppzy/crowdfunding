import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activiti5Test {

    private ApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring-*.xml");

    private ProcessEngine processEngine = ac.getBean(ProcessEngine.class);


    //3.完成任务
    @Test
    public void test13(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> chenlong = taskService.createTaskQuery().taskAssignee("chenlong").list();
        for (Task task : chenlong) {
            System.out.println("task"+task);
            taskService.complete(task.getId());
        }
    }

    //2.启动流程实例
    @Test
    public void test12(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition myProcess_1 = repositoryService.createProcessDefinitionQuery().latestVersion().processDefinitionKey("myProcess_1").singleResult();
        Map<String,Object> dataMap = new HashMap<>();
        //创建Map集合，用于给流程中的变量传参
        dataMap.put("tl","chenlong");
        //启动流程实例时，必须传参，否则会报错
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(myProcess_1.getId(),dataMap);
        System.out.println(processInstance);

    }


    //1.部署自定义的流程
    @Test
    public void test11(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("myProcess4.bpmn").deploy();
        System.out.println(deploy);

    }


    //查询历史
    @Test
    public void test10(){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().finished().processInstanceId("801").list();
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println("节点"+historicActivityInstance+','+historicActivityInstance.getId()+','+historicActivityInstance.getActivityName());
        }
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished().processInstanceId("801").singleResult();
        System.out.println(historicProcessInstance);
    }

    //执行任务
    @Test
    public void test9(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> tl = taskQuery.taskCandidateGroup("tl").list();
        //遍历查询分组tl的任务，并将任务分配给lisi
        for (Task task : tl) {
            System.out.println("task:"+task+","+task.getId());
            taskService.claim(task.getId(),"lisi");
        }

        System.out.println("+++++++++++++++++++++++++++");

        //遍历查询lisi人员的任务，并执行完成
        TaskQuery taskQuery1 = taskService.createTaskQuery();
        List<Task> zhangsan = taskQuery1.taskAssignee("lisi").list();
        for (Task task : zhangsan) {
            System.out.println(task+","+task.getId()+","+task.getAssignee());
            taskService.complete(task.getId());
        }

        System.out.println("执行完成后继续查询----------------------");
        TaskQuery taskQuery2 = taskService.createTaskQuery();
        List<Task> list = taskQuery2.list();
        for (Task task : list) {
            System.out.println(task+","+task.getId()+","+task.getAssignee());
        }

    }


    //启动自定义的流程实例
    @Test
    public void test8(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        ProcessDefinition myProcess_3 = processDefinitionQuery.processDefinitionKey("myProcess_3").latestVersion().singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(myProcess_3.getKey());
        System.out.println(processInstance);
    }


    //部署新的自定义分组流程
    @Test
    public void test7(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("myProcess3.bpmn").deploy();
        System.out.println(deploy);
    }



    //历史查询
    @Test
    public void test6(){
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> list1 = historicActivityInstanceQuery.finished().list();
        for (HistoricActivityInstance historicActivityInstance : list1) {
            System.out.println(historicActivityInstance);
        }

        System.out.println("-----------------------");
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        List<HistoricProcessInstance> list = historicProcessInstanceQuery.finished().list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            System.out.println(historicProcessInstance);
        }

    }


    //执行任务
    @Test
    public void test5(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.list();
        for (Task task : list) {
            System.out.println("task:"+task);
            taskService.complete(task.getId());
        }

        System.out.println("执行任务后------------------------------");
        TaskQuery taskQuery1 = taskService.createTaskQuery();
        List<Task> list1 = taskQuery1.list();
        for (Task task : list1) {
            System.out.println("task:"+task);
        }
    }


    //实例化流程（启动流程）
    @Test
    public void test4(){
        RuntimeService runtimeService = processEngine.getRuntimeService();



        //根据已定义的流程的id来进行实例化流程(启动该流程)
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("myProcess_2:1:304");
        System.out.println("processInstance"+processInstance);
    }


    //查询部署信息和 定义的流程信息
    @Test
    public void test3(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //DeploymentQuery：用于查询 act_re_deployment表的信息。 Deployment: 该表对应的实体类
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> list = deploymentQuery.list();
        for (Deployment deployment : list) {
            System.out.println("deployment"+deployment);
        }
        //ProcessDefinitionQuery:用于查询 act_re_procdef表的信息。ProcessDefinition:该表对应的实体类
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list1 = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : list1) {
            System.out.println(processDefinition);
        }

        System.out.println("----------------------------------------");
        ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionKey("myProcess_1").latestVersion().singleResult();
        System.out.println("最新版本的自定义流程"+processDefinition);
    }

    //部署自定义的流程
    @Test
    public void test2(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("myProcess2.bpmn").deploy();
        System.out.println(deploy);
    }


    //创建核心对象,生成23张表
    @Test
    public void test1(){
        System.out.println("processEngine"+processEngine);
    }
}
