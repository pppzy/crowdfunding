<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/10
  Time: 9:44
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
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <%@include file="/WEB-INF/view/common/maintop.jsp"%>

            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <%@include file="/WEB-INF/view/common/menu.jsp"%>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form id="addForm" role="form">
                        <div class="form-group">
                            <label for="floginacct">登陆账号</label>
                            <input type="text" class="form-control" id="floginacct" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="fusername">用户名称</label>
                            <input type="text" class="form-control" id="fusername" placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="femail">邮箱地址</label>
                            <input type="email" class="form-control" id="femail" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button id="addBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button id="resetBtn"  type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });

    $("#addBtn").click(function () {
        //1.提交之前需要进行表单的校验（前台校验）
        var flag = add_form_check();
        if(!flag){
            return false;
        }
        //2.根据addBtn的属性判断用户名是否重复(后台校验)
        var repeat_flag =$("#addBtn").attr("repeatTest");
        if(repeat_flag=="fail"){
            return false;
        }

        //3.发送异步请求向数据库添加用户
        $.ajax({
            type:"POST",
            url:"${APP_PATH}/user/doAddUser.do",
            data:{
               "loginacct":$("#floginacct").val(),
               "username" :$("#fusername").val(),
                "email" :$("#femail").val()
            },
            dataType:"JSON",
            success:function (data) {
                if(data.success){
                    layer.msg(data.message,{time:1000,icon:6,shift:5},function () {
                        window.location.href="${APP_PATH}/user/toIndex.do";
                    })

                }else{
                    layer.msg(data.message,{time:1000,icon:5,shift:5});
                }
            },
            error:function (data) {
                layer.msg("请求失败!",{time:1000,icon:5,shift:5});
            }
        })



    });
    //客户端表单校验方法
    function add_form_check(){
        //1.对账户进行校验
        var loginacct = $("#floginacct").val();
        var acct_regex = /^[a-zA-Z0-9_-]{5,16}$/;
        var acct_flag =acct_regex.test(loginacct);
        if(!acct_flag){
            layer.msg("账户格式不正确，必须为5-16个字母或数字组成", {time:1000, icon:5, shift:5});
            return false;
        }

        //2.对用户名称进行校验
        var username = $("#fusername").val();
        var username_regex = /(^[a-zA-Z0-9_-]{5,16}$)|(^[\u2E80-\u9FFF]{3,8})/;
        var username_flag = username_regex.test(username);
        if(!username_flag){
            layer.msg("用户名称格式不正确，必须为5-16个字母数字组成或3-8个中文字",{time:1000,icon:5,shift:5});
            return false;
        }

        //3.对邮箱进行校验
        var email = $("#femail").val();
        var email_regex = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        var email_flag = email_regex.test(email);
        if(!email_flag){
            layer.msg("邮箱格式不正确",{time:1000,icon:5,shift:5});
            return false;
        }
        return true;
    }


    //当填写账户后，提交异步请求检查是否重复
    $("#floginacct").change(function () {
        $.ajax({
            url:"${APP_PATH}/user/doRepeatCheck.do",
            type:"POST",
            data:{"loginacct":$("#floginacct").val()},
            success:function (data) {
                if(data.success){
                    $("#addBtn").attr("repeatTest","success");
                }else{
                    $("#addBtn").attr("repeatTest","fail");
                    layer.msg(data.message,{time:1000,icon:5,anim:5});
                }
            }
        });
    });



    //重置按钮
    $("#resetBtn").click(function () {
        $("#addForm")[0].reset();
    });


</script>
</body>
</html>

