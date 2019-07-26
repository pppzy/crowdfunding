<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/14
  Time: 14:23
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
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="leftRole">未分配角色列表</label><br>
                            <select  id="leftRole" class="form-control" multiple size="10" style="width:250px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignUnRole}" var="role" >
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>


                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="leftAddRoleBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="leftAddAllRoleBtn" class="btn btn-default glyphicon glyphicon-triangle-right">分配所有</li>
                                <br>
                                <li id="rightDelRoleBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                                <br>
                                <li id="rightDelAllRoleBtn" class="btn btn-default glyphicon glyphicon-triangle-left" style="margin-top:20px;">移除所有</li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="rightRole">已分配角色列表</label><br>
                            <select id="rightRole" class="form-control" multiple size="10" style="width:250px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignRole}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
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

    var jsonObj = {
        "userId":${param.id}
    };
    //右边箭头：给用户分配选择的角色
    $("#leftAddRoleBtn").click(function () {

      var selectedOption =  $("#leftRole option:selected");

      if(selectedOption.length>0){
          $.each(selectedOption,function (index,n) {

              jsonObj["ids["+index+"]"] = $(this).val();
          })
          $.ajax({
              url:"${APP_PATH}/user/doAssignRole.do",
              type:"POST",
              data:jsonObj,
              success:function (data) {
                  layer.msg(data.message,{time:1000,icon:6,shift:5});
                  $("#rightRole").append(selectedOption);
              },
              error:function () {
                  layer.msg("分配角色失败!",{time:1000,icon:5,shift:5})
              }
          });
      }else{
          layer.msg("请选择至少一个角色!",{time:1000,icon:0,shift:5});
      }

    });
    //左边箭头，给用户移除分配的角色
    $("#rightDelRoleBtn").click(function () {
        var selectedOption = $("#rightRole option:selected");
        if(selectedOption.length>0){
            $.each(selectedOption,function (index,n) {
                jsonObj["ids["+index+"]"] = $(this).val();
            })
            $.ajax({
                url:"${APP_PATH}/user/doAssignUnRole.do",
                type:"POST",
                data:jsonObj,
                success:function (data) {
                    layer.msg(data.message,{time:1000,icon:6,shift:5});
                    $("#leftRole").append(selectedOption);
                },
                error:function () {
                    layer.msg("移除分配角色失败!",{time:1000,icon:5,shift:5})
                }
            });
        }else{
            layer.msg("请选择至少一个角色!",{time:1000,icon:0,shift:5})
        }

    });

    //分配所有角色
    $("#leftAddAllRoleBtn").click(function () {
        var selectedOption =  $("#leftRole option");

        if(selectedOption.length>0){
            $.each(selectedOption,function (index,n) {

                jsonObj["ids["+index+"]"] = $(this).val();
            })
            $.ajax({
                url:"${APP_PATH}/user/doAssignRole.do",
                type:"POST",
                data:jsonObj,
                success:function (data) {
                    layer.msg(data.message,{time:1000,icon:6,shift:5});
                    $("#rightRole").append(selectedOption);
                },
                error:function () {
                    layer.msg("分配角色失败!",{time:1000,icon:5,shift:5})
                }
            });
        }else{
            layer.msg("没有多余的角色可分配了!",{time:1000,icon:0,shift:5});
        }
    });

    //移除所有角色
    $("#rightDelAllRoleBtn").click(function () {
        var selectedOption = $("#rightRole option");
        if(selectedOption.length>0){
            $.each(selectedOption,function (index,n) {
                jsonObj["ids["+index+"]"] = $(this).val();
            })
            $.ajax({
                url:"${APP_PATH}/user/doAssignUnRole.do",
                type:"POST",
                data:jsonObj,
                success:function (data) {
                    layer.msg(data.message,{time:1000,icon:6,shift:5});
                    $("#leftRole").append(selectedOption);
                },
                error:function () {
                    layer.msg("移除分配角色失败!",{time:1000,icon:5,shift:5})
                }
            });
        }else{
            layer.msg("没有多余的角色可移除了!",{time:1000,icon:0,shift:5})
        }
    });

    $("form").delegate("#leftRole option","dblclick",function () {
        jsonObj["ids[0]"] = $(this).val();
        var obj = $(this);
        $.ajax({
            url:"${APP_PATH}/user/doAssignRole.do",
            type:"POST",
            data:jsonObj,
            success:function (data) {
                layer.msg(data.message,{time:1000,icon:6,shift:5});
                $("#rightRole").append(obj);
            },
            error:function () {
                layer.msg("分配角色失败!",{time:1000,icon:5,shift:5})
            }
        });
    })

    $("form").delegate("#rightRole option","dblclick",function () {
        jsonObj["ids[0]"] = $(this).val();
        var obj = $(this);
        $.ajax({
            url:"${APP_PATH}/user/doAssignUnRole.do",
            type:"POST",
            data:jsonObj,
            success:function (data) {
                layer.msg(data.message,{time:1000,icon:6,shift:5});
                $("#leftRole").append(obj);
            },
            error:function () {
                layer.msg("分配角色失败!",{time:1000,icon:5,shift:5})
            }
        });
    })


</script>
</body>
</html>

