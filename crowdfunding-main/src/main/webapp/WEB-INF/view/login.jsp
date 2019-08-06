<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/6/29
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"   %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="${APP_PATH}/index.htm" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="loginForm"   class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2><br>
        <h6 style="color: red">${sessionScope.loginMsg}</h6>
        <div class="form-group  has-feedback">
            <input type="text" class="form-control" id="floginacct" name="loginacct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
            <span class="help-block"></span>
        </div>
        <div class="form-group  has-feedback">
            <input type="text" class="form-control" id="fuserpswd" name="userpswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            <span class="help-block"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select id="ftype" class="form-control" name="type" >
                <option value="member">会员</option>
                <option value="user">管理</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input id="rememberBox" type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="${APP_PATH}/reg.htm">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script> <%--导入layer弹窗组件 --%>
<script>
    function dologin() {
       // $("#loginForm").submit();
        //1.校验表单数据信息是否符合格式
        var flag = form_login_check();
        if(flag!=true){
            return false;
        }
        //2.序列化表单，获取表单数据
        var dataform =  $("#loginForm").serialize();

        var type = $("#ftype option:selected").val();

        var flag = $("#rememberBox:checked").prop("checked");

        var rememberStatus = flag?1:0;

        //3.提交异步登录请求
        $.ajax({
            url:"${APP_PATH}/doLogin.do",
            data:dataform+"&rememberStatus="+rememberStatus,
            type:"POST",
            dataType:"JSON",
            success:function (data) {
                if(data.success){
                    if("member"==type){
                        layer.msg("登录成功!", {time:1000, icon:16},function () {
                            window.location.href = "${APP_PATH}/memberIndex.htm";
                        });
                    }else{
                        layer.msg("登录成功!", {time:1000, icon:16},function () {
                            window.location.href = "${APP_PATH}/main.htm";
                        });
                    }


                }else{
                    layer.msg(data.message, {time:1000, icon:5, shift:6}); //弹出时间，图标，特效
                }
            }
        })
    }

    //用于校验表单数据的方法
    function form_login_check() {
        //1.校验用户名
       var loginacct = $("#floginacct").val();
       var regex_acct = /(^[a-zA-Z0-9_-]{5,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
       var flag_acct = regex_acct.test(loginacct);
       if(flag_acct!=true){
           /*      $("#floginacct").parent().addClass("has-error");
         $("#floginacct").nextAll(".help-block").text("用户名格式必须满足5-16个字符或2-5个中文字");*/
           form_statu_check("#floginacct","fail","用户名格式必须满足5-16个字符或2-5个中文字");
           return false;
       }
        /*
        $("#floginacct").parent().addClass("has-success");
           $("#floginacct").nextAll(".help-block").text("用户名格式正确");*/
        form_statu_check("#floginacct","success","用户名格式正确");

       //2.校验密码
       var userpswd = $("#fuserpswd").val();
       var regex_pswd = /^[a-zA-Z0-9_-]{3,18}$/;
       var flag_pswd = regex_pswd.test(userpswd);
       if(flag_pswd!=true){
           form_statu_check("#fuserpswd","fail","密码格式错误!");
           return false;
       }
        form_statu_check("#fuserpswd","success","密码格式正确");

       return true;
    }

    function form_statu_check(emp,statu,message) {
        //1.先清除所有的状态信息
        $(emp).parent().removeClass("has-success has-error");
        $(emp).nextAll(".help-block").text("");
        //2.根据statu 设置状态信息
        if(statu=="success"){
            $(emp).parent().addClass("has-success");
            $(emp).nextAll(".help-block").text(message);
        }
        if(statu=="fail"){
            $(emp).parent().addClass("has-error");
            $(emp).nextAll(".help-block").text(message);
            layer.msg(message, {time:1000, icon:5, shift:6}); //弹出时间，图标，特效
            $(emp).val("");
            $(emp).focus();
        }
    }
</script>
</body>
</html>
