<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/13
  Time: 15:13
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
    <link rel="stylesheet" href="${APP_PATH}/css/pagination.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="queryText"  class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>

                    <br>
                    <br>
                    <br>

                    <div>
                    <form role="form" id="uploadForm" method="post" enctype="multipart/form-data" >
                        <div class="form-group">
                            <label for="fuploadXml">流程bpmn </label>
                            <input class="form-control" type="file" name="fuploadXml" id="fuploadXml" placeholder="请输入流程.bpmn">
                        </div>
                        <div class="form-group">
                            <label for="fuploadPng">流程图片 </label>
                            <input class="form-control" type="file" name="fuploadPng" id="fuploadPng" placeholder="请输入流程.png">
                        </div>
                    </form>
                    </div>

                    <button id="uploadProcBtn" type="button" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-upload"></i> 上传流程定义文件</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>流程名称</th>
                                <th>流程版本</th>
                                <th>流程Key</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <div id="pagination" class="pagination">
                                    </div>
                                </td>
                            </tr>

                            </tfoot>
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
<script src="${APP_PATH}/jquery/pagination/jquery.pagination.js"></script>
<script src="${APP_PATH}/jquery/jqueryForm/jquery-form.min.js"></script>
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
        ajax_select_process(0);
    });

    var jsonObj = {
        "pageNo":1,
        "pageSize":5,
        "queryText":""
    };

    //给模糊查询按钮绑定单击事件
    $("#queryBtn").click(function () {
        jsonObj["queryText"] = $("#queryText").val();
        ajax_select_process(0);
    });


    //异步方式查询所有流程信息
    function ajax_select_process(pageIndex) {
        jsonObj.pageNo= pageIndex+1;
        if(jsonObj.pageNo<0){
            jsonObj.pageNo=1;
        }
        $.ajax({
            url:"${APP_PATH}/process/ajaxProcPage.do",
            data:jsonObj,
            type:"POST",
            success:function (data) {
                var procList = data.dataMap.procList;
                var pageInfo = data.dataMap.pageInfo;
                showProc(procList);
                showPageInfo(pageInfo);
            },
            error:function () {
                layer.msg("查询失败",{time:1000,icon:5,shift:5});
            }

        });
    }

    //展示数据
    function showProc(data) {
        var content = '';
        $.each(data,function (index,n) {
            content+= ' <tr>';
            content+= ' 	<td>'+(index+1)+'</td>';
            content+= ' 	<td>'+n.name+'</td>';
            content+= ' 	<td>'+n.version+'</td>';
            content+= ' 	<td>'+n.key+'</td>';
            content+= ' 	<td>';
            content+= ' 		<button type="button" onclick="window.location.href=\'${APP_PATH}/process/toShowImg.htm?id='+n.id+'\'"  class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-eye-open"></i></button>';
            content+= ' 		<button type="button" onclick="delProc(\''+n.id+'\',\''+n.name+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            content+= ' 	</td>';
            content+= ' </tr>';
        });
        $("tbody").html(content);
    }

    //展示分页
    function showPageInfo(data) {
        $("#pagination").pagination(data.totalCount,{
            items_per_page:data.pageSize,  //每页显示的条目数
            num_display_entries:4,         //连续分页显示的主体数目
            current_page:(data.pageNo-1),  //当前选中的页面，默认0 表示第一页
            num_edge_entries:1,            //两侧首尾显示的分页条目数
            prev_text:"上一页",             //上一页分页按钮显示的文字
            next_text:"下一页",             //下一页分页按钮显示的文字
            callback:ajax_select_process      //当单击分页按钮，调用的函数
        })
    }


    $("#uploadProcBtn").click(function () { //给按钮绑定单击事件
                   //不传参数：触发单击事件

        if($("#fuploadXml").val()==""){
            layer.msg("请添加要部署的流程文件和图片!",{time:1000,icon:5,shift:5});
            return false;
        }



            //1.涉及file文本域表单的异步提交，需要用到jquery-form插件
            //设置异步提交的属性
            var options = {
                url:"${APP_PATH}/process/addProcDef.do",
                success: function (data) {
                    if(data.success){
                        layer.msg(data.message,{time:1000,icon:6,shift:5},function () {
                            window.location.reload();
                        });
                    }else{
                        layer.msg("添加失败!",{time:1000,icon:5,shift:5});
                    }
                }
            };
            //表单异步提交
            $("#uploadForm").ajaxSubmit(options);

    });

    function delProc(id, name) {
        layer.confirm("确定要删除["+name+"]流程吗？",function (index) {
            $.ajax({
              url:"${APP_PATH}/process/delProc.do",
              data:"id="+id,
              type:"POST",
              success:function (data) {
                  if(data.success){
                      layer.msg("删除成功!",{time:1000,icon:6,shift:5},function () {
                          window.location.reload();
                      });

                  }else{
                      layer.msg(data.message,{time:1000,icon:5,shift:5});
                  }
              }
            })
            layer.close(index);
        },function (index) {
            layer.close(index);
        })
    }


</script>
</body>
</html>


