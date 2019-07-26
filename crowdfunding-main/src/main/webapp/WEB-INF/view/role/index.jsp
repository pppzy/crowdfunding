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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
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
                                <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/role/toAdd.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div class="pagination"> </div>
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
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
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
        showMenu();
        ajax_select_role(0);
    });

    var jsonObj = {
        "pageSize":5,
        "pageNo":1,
        "queryText":''
    };

    $("#queryBtn").click(function () {
        var queryText1 = $("#queryText").val();
        jsonObj.queryText = queryText1;
        ajax_select_role(1);
    });

    //异步方式查询所有角色信息
    function ajax_select_role(pageIndex) {
        jsonObj.pageNo= pageIndex+1;
        if(jsonObj.pageNo<0){
            jsonObj.pageNo=1;
        }
        $.ajax({
           url:"${APP_PATH}/role/ajaxRolePage.do",
           data:jsonObj,
           type:"POST",
           success:function (data) {
               var roleList = data.dataMap.list.list;
               var pageInfo = data.dataMap.list;
               showRole(roleList);
               showPageListByPagination(pageInfo);
           },
           error:function () {
               layer.msg("查询失败",{time:1000,icon:5,shift:5});
           }

        });
    }


    //遍历role集合输出到页面
    function showRole(list) {
        var context = '';
        $.each(list,function (index, role) {
            context+= ' <tr>\n' +
                '         <td>'+(index+1)+'</td>\n' +
                '         <td><input type="checkbox"></td>\n' +
                '         <td>'+role.name+'</td>\n' +
                '         <td>\n' +
                '             <button type="button" onclick="toAssignPermission('+role.id+')"   class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>\n' +
                '             <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>\n' +
                '             <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>\n' +
                '         </td>\n' +
                '     </tr>  ' ;
        })

        $("tbody").html(context);
    }
      function showPageListByPagination(page) {
          $(".pagination").pagination(page.totalCount,{
              items_per_page:page.pageSize,
              num_display_entries:4,
              current_page:(page.pageNo-1),
              num_edge_entries:1,
              prev_text:"上一页",
              next_text:"下一页",
              callback:ajax_select_role
          })
      }



  /*  function showPageList(page) {

        if(page.pageNo==1){
           var pervious = '<li class="disabled"><a href="javascript:void(0)">首页</a></li>\n' +
               '     <li class="disabled"><a href="javascript:void(0)">上一页</a></li>';
       }else{
           var pervious = '<li ><a href="javascript:ajax_select_role(1)">首页</a></li>\n' +
               '     <li ><a href="javascript:ajax_select_role('+(page.pageNo-1)+')">上一页</a></li>';
       }

       if(page.pageNo==page.totalPage){
         var last = ' <li class="disabled"><a href="javascript:void(0)">下一页</a></li>\n' +
             '     <li class="disabled"><a href="javascript:void(0)">末页</a></li>'
       }else{
         var last = '<li ><a href="javascript:ajax_select_role('+(page.pageNo+1)+')">下一页</a></li>\n' +
             '     <li ><a href="javascript:ajax_select_role('+(page.totalPage)+')">末页</a></li> '
       }

       //前五后四方式分页条
        var pageno = page.pageNo;
        var totalPage = page.totalPage;
        var start = 0;
        var end = 0;
        if(totalPage<10){
            start=1;
            end=totalPage;
        }else{
            start=pageno-5;
            end=pageno+4;
        }

        if(start<0){
            start=1;
            end=9+start;
        }
        if(end>totalPage){
            end=totalPage,
            start=totalPage-9;
        }

        var pagelist = '';
        for(var i=start;i<=end;i++){
            if(pageno==i){
                 pagelist += ' <li class="active"><a href="javascript:ajax_select_role('+i+')">'+i+'</a></li> ';
            }else{
                 pagelist += ' <li ><a href="javascript:ajax_select_role('+i+')">'+i+'</a></li> ';
            }
        }

        $("tfoot ul").html('');
        $("tfoot ul").append(pervious).append(pagelist).append(last);
    }*/

    function toAssignPermission(id) {
        window.location.href="${APP_PATH}/role/toAssignPermission.htm?id="+id;
    }



</script>
</body>
</html>

