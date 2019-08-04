<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/8/4
  Time: 9:59
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
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}

        input[type=checkbox] {
            width:18px;
            height:18px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 分类管理</a></div>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th>名称</th>
                                <th >商业公司</th>
                                <th >个体工商户</th>
                                <th >个人经营</th>
                                <th >政府及非营利组织</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${requestScope.certsList}" var="cert" varStatus="status">
                                <tr>
                                    <td>${cert.name}</td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="0" ></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="1" ></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="2" ></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="3" ></td>
                                </tr>

                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/script/menu.js"></script>
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

        showMenu();


        <c:forEach items="${requestScope.acctTypeCert}" var="n">
         $("tbody :checkbox[certid='${n.certid}'][accttype='${n.accttype}']").prop("checked",true);
        </c:forEach>
    });


    //当单击复选框后，提交异步，用于增加或删除对应关系
    $("tbody :checkbox").click(function () {
        //1.判断被单击的复选框是否被选中
        var flag = $(this).prop("checked");
        //2.如果选中，则发起异常请求向数据库增加 账户类型和资质对应关系

        var jsonObj ={
          "certid":$(this).attr("certid"),
          "accttype":$(this).attr("accttype")
        };

        if(flag){
            $.ajax({
                url:"${APP_PATH}/cert/addAccttypeCertid.do",
                data:jsonObj,
                type:"POST",
                success:function (data) {
                    if(data){
                        layer.msg(data.message,{time:1000,icon:6,shift:5});
                    }else{
                        layer.msg(data.message,{time:1000,icon:6,shift:5});
                    }
                }
            })
        };
        //3.如果是取消，则发起异常请求，向数据库删除该条对应数据
        if(!flag){
            $.ajax({
                url:"${APP_PATH}/cert/deleteAccttypeCertid.do",
                data:jsonObj,
                type:"POST",
                success:function (data) {
                    if(data){
                        layer.msg(data.message,{time:1000,icon:6,shift:5});
                    }else{
                        layer.msg(data.message,{time:1000,icon:6,shift:5});
                    }
                }
            })
        }
    });











</script>
</body>
</html>

