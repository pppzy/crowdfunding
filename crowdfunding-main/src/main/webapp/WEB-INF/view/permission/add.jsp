<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/10
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <li><a href="#">权限列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">权限表单<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form id="addForm" role="form">
                        <div class="form-group">
                            <label for="fname">权限名称</label>
                            <input type="text" class="form-control" id="fname" placeholder="请输入名称">
                        </div>
                        <div class="form-group">
                            <label for="furl">权限路径</label>
                            <input type="text" class="form-control" id="furl" placeholder="请输入路径">
                        </div>
                        <div class="form-group">
                            <label >权限图标选择</label>
                            <c:forEach items="${requestScope.list}" var="permission">
                                <label class="radio-inline">
                                    <input type="radio" name="icon"  value="${permission.icon}">
                                    <span class="${permission.icon}"></span>
                                </label>
                            </c:forEach>
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
<script src="${APP_PATH}/script/menu.js"></script>
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
        showMenu();
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
            url:"${APP_PATH}/permission/doAddPermission.do",
            data:{
               "name":$("#fname").val(),
               "url" :$("#furl").val(),
                "pid" :"${param.id}",
                "icon":$("input:checked").val()
            },
            dataType:"JSON",
            success:function (data) {
                if(data.success){
                    layer.msg(data.message,{time:1000,icon:6,shift:5},function () {
                        window.location.href="${APP_PATH}/permission/toIndex.htm";
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
        //1.对权限名称进行校验
        var name = $("#fname").val();
        var name_regex = /(^[a-zA-Z0-9_-]{5,16}$)|(^[\u2E80-\u9FFF]{3,8})/;
        var name_flag =name_regex.test(name);
        if(!name_flag){
            layer.msg("账户格式不正确，必须为5-16个字母数字或3-8个中文字", {time:1000, icon:5, shift:5});
            return false;
        }

        //2.对路径进行校验
        var url = $("#furl").val();
        var url_regex = /(^[a-zA-Z0-9_/-]{5,16}$)/;
        var url_flag = url_regex.test(url);
        if(!url_flag){
            layer.msg("路径格式不正确",{time:1000,icon:5,shift:5});
            return false;
        }

        return true;
    }


    //当填写账户后，提交异步请求检查是否重复
    $("#fname").change(function () {
        $.ajax({
            url:"${APP_PATH}/permission/doRepeatCheck.do",
            type:"POST",
            data:{"name":$("#fname").val()},
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

