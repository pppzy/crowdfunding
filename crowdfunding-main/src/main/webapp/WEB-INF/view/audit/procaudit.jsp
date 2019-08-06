<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/8/6
  Time: 8:38
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 实名认证审核</a></div>
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
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>流程名称</th>
                                <th>流程版本</th>
                                <th>任务名称</th>
                                <th>申请会员</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul id="pagination" class="pagination">

                                    </ul>
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
<script src="${APP_PATH}/jquery/pagination/jquery.pagination.js"></script>
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

        ajax_select_page(0);
    });



    //json字符串用于传输异步时候传输数据
    var jsonData = {
        "pageNo":1,
        "pageSize":10
    }


    //异步分页查询方法：
    function ajax_select_page(pageIndex) {
        jsonData.pageno = pageIndex+1;
        if(jsonData.pageno<0){
            jsonData.pageno==1
        }
        $.ajax({
            type:"POST",
            url:"${APP_PATH}/procaudit/ajaxTask.do",
            data:jsonData,
            dataType:"json",
            success:function (data) {

                //如果查询成功
                if(data.success){
                    var taskList = data.dataMap.taskList;
                    var pageinfo = data.dataMap.page;
                    user_list(taskList);
                    user_page(pageinfo);
                }else{
                    //查询失败则跳出信息
                    layer.msg(data.message, {time:1000, icon:5, shift:6});
                }

            }

        });
    }


    //解析json数据中的user集合，以拼串将数据渲染到jsp页面
    function user_list(list) {
        var content = "";
        $.each(list,function (index, task) {
            content += '   <tr>\n' +
                '     <td>'+(index+1)+'</td>\n' +
                '     <td>'+task.procname+'</td>\n' +
                '     <td>'+task.version+'</td>\n' +
                '     <td>'+task.name+'</td>\n' +
                '     <td>'+task.member.loginacct+'</td>\n' +
                '     <td>\n' +
                '          <button type="button"onclick="window.location.href=\'${APP_PATH}/procaudit/toAuditProc.htm?membId='+task.member.id+'&taskId='+task.taskId+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>\n' +
                '          <button type="button"  class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>\n' +
                '     </td>\n' +
                '     </tr>    '

        })
        $("tbody").html(content);
    }

    function user_page(page) {
        $(".pagination").pagination(page.totalCount,{
            items_per_page:page.pageSize,
            num_display_entries:4,
            current_page:(page.pageNo-1),
            num_edge_entries:1,
            prev_text:"上一页",
            next_text:"下一页",
            callback:ajax_select_page
        })
    }


</script>
</body>
</html>

