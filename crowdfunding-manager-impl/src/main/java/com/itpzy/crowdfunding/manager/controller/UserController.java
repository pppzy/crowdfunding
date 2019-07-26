package com.itpzy.crowdfunding.manager.controller;

import com.itpzy.crowdfunding.bean.Role;
import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.service.RoleService;
import com.itpzy.crowdfunding.manager.service.UserService;
import com.itpzy.crowdfunding.util.AjaxResult;
import com.itpzy.crowdfunding.util.Page;
import com.itpzy.crowdfunding.util.StringUtil;
import com.itpzy.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    /**
     * 异步方式——分页查询
     * @param pageno
     * @param pagesize
     * @return
     */
    @RequestMapping(value = "/ajaxUserPage")
    @ResponseBody
    public AjaxResult AjaxSelectPage(@RequestParam(required = false,defaultValue = "1")Integer pageno,
                                     @RequestParam(required = false,defaultValue = "10")Integer pagesize,
                                     @RequestParam(required = false) String queryText){
        try {
            //创建Map集合，用于存储查询数据，以方便后面动态sql查询
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("pageno",pageno);
            dataMap.put("pagesize",pagesize);
            //判断如果queryText不为空，则将信息存储到Map集合中
            if (StringUtil.isNotEmpty(queryText)){
                String trim = queryText.trim();
                if(trim.contains("%")){
                    trim = trim.replaceAll("%", "\\\\%");
                }
                dataMap.put("queryText",trim);
            }

            Page page = userService.selectPage(dataMap);
            return AjaxResult.success("查询成功").add("page",page);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail("查询失败"+e.getMessage());
        }
    }

    /**
     * 异步方式——分页查询
     * @param pageno
     * @param pagesize
     * @return
     */
 /*   @RequestMapping(value = "/ajaxUserPage")
    @ResponseBody
    public AjaxResult AjaxSelectPage(@RequestParam(required = false,defaultValue = "1")Integer pageno,
                                     @RequestParam(required = false,defaultValue = "10")Integer pagesize){
        try {
            Page page = userService.selectPage(pageno, pagesize);
            return AjaxResult.success("查询成功").add("page",page);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail("查询失败"+e.getMessage());
        }

    }*/


    /**
     * 跳转页面到user/index页面，用作异步分页查询
     * @return
     */
    @RequestMapping(value = "/toIndex")
    public String ToUserIndex(){
        return "user/index";
    }

    /**
     * 同步方式——分页查询 跳转到user/index1页面
     * @param pageNo 当前页
     * @param pageSize 每页显示个数
     * @param map 将数据存入request域中
     * @return
     */
    @RequestMapping(value = "/index")
    public String UserIndex(@RequestParam(required = false,defaultValue = "1") Integer pageNo ,
                            @RequestParam(required = false,defaultValue = "10") Integer pageSize, Map map){

        Page page = userService.selectPage(pageNo,pageSize);

        map.put("page",page);

        return "user/index1";
    }

    //跳转到增加用户页面
    @RequestMapping(value = "toAdd")
    public String add(){
        return "user/add";
    }

    //新增用户时对账号是否重复进行检测
    @RequestMapping(value = "doRepeatCheck")
    @ResponseBody
    public AjaxResult RepeatCheck(String loginacct){
        String regex="^[a-zA-Z0-9_-]{5,16}$";
        boolean matches = loginacct.matches(regex);
        if(!matches){
            return AjaxResult.fail("账户格式不正确(后台)");
        }
        int count =userService.selectAcctRepeat(loginacct);
        if(count>=1){
            return AjaxResult.fail("账户名重复!");
        }else {
            return AjaxResult.success();
        }
    }
    //新增用户
    @RequestMapping(value = "doAddUser",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult doAddUser(User user){
       try {
           int n = userService.addUser(user);
           if(n==1){
               return AjaxResult.success("添加用户成功");
           }else{
               return AjaxResult.fail("添加用户失败");
           }
       }catch (Exception e){
           e.printStackTrace();
           return AjaxResult.fail(e.getMessage());
       }

    }

    //跳转到编辑用户页面
    @RequestMapping(value = "toEdit")
    public String toEdit(Integer id,Model model){
       User user =  userService.selectUserById(id);
        model.addAttribute("user",user);
        return "user/edit";
    }

    //编辑用户信息
    @RequestMapping(value = "toEditUser",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult toEditUser(User user){

        try {
            int count =  userService.updateUser(user);
            if(count==1){
                return AjaxResult.success("修改成功!");
            }else{
                return AjaxResult.fail("修改失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.fail("修改失败"+e.getMessage());
        }
    }

    //根据id删除单个用户
    @RequestMapping(value = "toDelUser",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult toDelUser(Integer id){
      int count =  userService.deleteUser(id);
      if(count==1){
          return AjaxResult.success("删除成功!");
      }else{
          return AjaxResult.fail("删除失败!");
      }
    }

    //删除多个用户
/*    @RequestMapping(value = "toDelUserList",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult toDelUserList(String idStr){
        //处理数据，转换为int类型数值并存入到list集合中
        String[] split = idStr.split("-");
        List<Integer> idList = new ArrayList<>();
        for (String s : split) {
            int i = Integer.parseInt(s);
            idList.add(i);
        }
        int count = userService.deleteUserList(idList);
        if(count==split.length){
            return AjaxResult.success("删除成功!");
        }else{
            return AjaxResult.fail("删除失败!");
        }
    }*/
     //删除多个用户
    @RequestMapping(value = "toDelUserList",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult toDelUserList(Data userData){
        int count = userService.deleteUserList(userData);
        if(count==userData.getDatas().size()){
            return AjaxResult.success("删除成功!");
        }else{
            return AjaxResult.fail("删除失败!");
        }
    }

    //跳转到角色分配页面
    @RequestMapping(value="toAssignRole")
    public String toAssignRole(Integer id,Model model){
        //1.查询出所有的角色集合
        List<Role> roles =  roleService.selectAll();
        //2.创建未分配角色的集合和已分配角色的集合
        List<Role> assignUnRole = new ArrayList<>();
        List<Role> assignRole = new ArrayList<>();
        //3.根据id参数查询用户的对应的所有角色
        List<Integer> roleIds = userService.selectRoleById(id);
        //4.遍历所有的角色集合
        for (Role role : roles) {
            int roleId = role.getId();
            if(roleIds.contains(roleId)){
                assignRole.add(role);
            }else{
                assignUnRole.add(role);
            }
        }
        model.addAttribute("assignRole",assignRole);
        model.addAttribute("assignUnRole",assignUnRole);
        return "user/assignRole";
    }

    //分配角色
    @RequestMapping(value = "doAssignRole")
    @ResponseBody
    public AjaxResult doAssignRole(Integer userId,Data datas){
      int count =  userService.doAssignRole(userId,datas);

        return AjaxResult.success("分配角色成功!");
    }

    //移除分配角色
    @RequestMapping(value = "doAssignUnRole")
    @ResponseBody
    public AjaxResult doAssignUnRole(Integer userId,Data datas){
        int count =  userService.doAssignUnRole(userId,datas);

        return AjaxResult.success("移除分配的角色成功!");
    }




}
