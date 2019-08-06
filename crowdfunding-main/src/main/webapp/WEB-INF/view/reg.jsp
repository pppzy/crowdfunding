<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/6/29
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="regForm" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="floginacct" name="loginacct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="fuserpswd" name="userpswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="fusername" name="username" placeholder="请输入用户名" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="femail" name="email" placeholder="请输入邮箱地址" style="margin-top:10px;">
            <span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select id="usertype" class="form-control" >
                <option value="1">企业</option>
                <option value="0">个人</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="${APP_PATH}/login.htm">我有账号</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="javascript:uploadMember()" > 注册</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
</body>
<script>

    function uploadMember() {
        var dataJson = {
            'loginacct':$("#floginacct").val(),
            'userpswd':$("#fuserpswd").val(),
            'username':$("#fusername").val(),
            'email':$("#femail").val(),
            'usertype':$("#usertype option:selected").val()
        }
        var usertype =  $("select option:selected").val();
        var loadIndex= -1;
        $.ajax({
            url:"${APP_PATH}/member/regMember.do",
            data:dataJson,
            beforeSend:function () {
                 loadIndex= layer.msg("正在注册中!",{time:1000,icon:6,shift:5}) ;
                return true;
            },
            success:function (data) {
                layer.close(loadIndex);
                if(data.success){
                    layer.msg(data.message,{time:1000,icon:6,shift:5},function () {
                        window.location.href = "${APP_PATH}/login.htm";
                    })
                }else{
                    layer.msg(data.message,{time:1000,icon:5,shift:5});
                }
            }

        });
    }


</script>
</html>
