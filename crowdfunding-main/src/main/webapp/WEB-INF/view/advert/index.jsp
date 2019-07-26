<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/26
  Time: 9:24
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 广告管理</a></div>
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
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/advert/add.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>广告描述</th>
                                <th>状态</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="4" align="center">
                                   <div class="pagination"></div>
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
        ajax_select_advert(0);
    });

    var page = {
        "pageSize":5,
        "pageNo":0,
        "queryText":''
    }


    $("#queryBtn").click(function () {
       var text = $("#queryText").val();
       page.queryText = text;
       ajax_select_advert(0);
    })

    //异步方式查询所有广告信息
    function ajax_select_advert(pageno) {
        page.pageNo= pageno+1;
        if(page.pageNo<0){
            page.pageNo=1;
        }
        $.ajax({
            url:"${APP_PATH}/advert/ajaxAdvertPage.do",
            data:page,
            type:"POST",
            success:function (data) {
                var advertList = data.dataMap.list.list;
                var pageInfo = data.dataMap.list;
                showAdvert(advertList);
                showPageList(pageInfo);
            },
            error:function () {
                layer.msg("查询失败",{time:1000,icon:5,shift:5});
            }

        });
    }

    function showAdvert(data) {
        var context ='' ;

        $.each(data,function (index, advert) {
            context+= ' <tr>\n' +
        '              <td>'+(index+1)+'</td>\n' +
        '              <td>'+advert.name+'</td>\n';
        switch(advert.status){
            case '0':
            context+='<td>草稿</td>\n';
            break;

            case '1':
            context+='<td>未审核</td>\n';
            break;

            case '2':
            context+='<td>审核完成</td>\n';
            break;

            case '3':
                context+='<td>发布</td>\n';
                break;
        }
        context+= ' <td>\n' +
        '                  <button type="button" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-check"></i></button>\n' +
        '                  <button type="button" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil"></i></button>\n' +
        '                  <button type="button" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i></button>\n' +
        '              </td>\n' +
        '          </tr>';
        })

        $("tbody").html(context);
    }

    function showPageList(data) {
        $(".pagination").pagination(data.totalCount,{
            num_edge_entries: 1, //边缘页数
            num_display_entries: 4, //主体页数
            callback:ajax_select_advert,
            items_per_page:data.pageSize, //每页显示10项
            current_page:(data.pageNo-1),
            prev_text:"上页",
            next_text:"下页"
        })
    }

</script>
</body>
</html>

