<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/7
  Time: 15:34
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

        td {
            white-space: nowrap;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
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
                    <button type="button" id="delBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/user/toAdd.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="delAll" type="checkbox"></th>
                                <th width="284" >账号</th>
                                <th width="349">名称</th>
                                <th width="676">邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination"></ul>
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
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
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
       ajax_select_page(0);
    });





    //json字符串用于传输异步时候传输数据，queryText:模糊查询字段
    var jsonData = {
        "pageno":1,
        "pagesize":10,
        "queryText":''
    }



    $("#queryBtn").click(function (){
        var queryText = $("#queryText").val();
        jsonData.queryText = queryText;
        ajax_select_page(1);
    })



    //异步分页查询方法：
    function ajax_select_page(pageIndex) {
        jsonData.pageno = pageIndex+1;
        if(jsonData.pageno<0){
            jsonData.pageno==1
        }
        $.ajax({
           type:"POST",
           url:"${APP_PATH}/user/ajaxUserPage.do",
           data:jsonData,
           dataType:"json",
           success:function (data) {

               //如果查询成功
               if(data.success){
                   var userlist = data.dataMap.page.list;
                   var pageinfo = data.dataMap.page;
                   user_list(userlist);
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
        $.each(list,function (index, user) {
        content += '   <tr>\n' +
            '     <td>'+(index+1)+'</td>\n' +
            '     <td><input type="checkbox" name="delCheckBox" id="'+user.id+'" class="delCB" ></td>\n' +
            '     <td>'+user.loginacct+'</td>\n' +
            '     <td>'+user.username+'</td>\n' +
            '     <td>'+user.email+'</td>\n' +
            '     <td>\n' +
            '          <button type="button"onclick="window.location.href=\'${APP_PATH}/user/toAssignRole.htm?id='+user.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>\n' +
            '          <button type="button" onclick="window.location.href=\'${APP_PATH}/user/toEdit.htm?id='+user.id+'\' "  class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>\n' +
            '          <button type="button" id="'+user.id+'"  class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>\n' +
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


   /* //前五后四方式分页
    function user_page(pageinfo) {
        var start = 0;
        var end = 0;
        var pageno = pageinfo.pageNo;
        var totalpage = pageinfo.totalPage;
        var content = '';
        if(totalpage<10){
            start=1;
            end = totalpage;
        }
        //前五后四
        if(totalpage>=10){
            start = pageno-5;
            end = pageno+4;
        }
        //如果前面不足五页，那么start=1, 由end补足十页
        if(start<=0){
            start=1;
            end = 9+start;
        }
        //如果后面不足四页，那么end =totalPage，由start补足十页
        if(end>totalpage){
            end = totalpage;
            start = end -9;
        }
        //拼串， 首页和上一页
        if(pageno==1){
            content+= '<li class="disabled"><a href="javascript:void(0)">首页</a></li> ';
            content+= '<li class="disabled"><a href="javascript:void(0)">上一页</a></li> ';
        }else{
            content+= '<li ><a href="javascript:ajax_select_page(1)">首页</a></li> ';
            content+= '<li ><a href="javascript:ajax_select_page('+(pageno-1)+')">上一页</a></li> ';
        }
        //拼串，导航条页数
        for(var i=start;i<=end;i++){
            if(pageno==i){
                content += ' <li class="active"><a href="javascript:ajax_select_page('+i+')">'+i+'<span class="sr-only">(current)</span></a></li>';
            }else {
                content += ' <li ><a href="javascript:ajax_select_page('+i+')">'+i+'</a></li>';
            }
        }
        //拼串，下一页和末页
        if(pageno==totalpage){
            content+= '<li class="disabled"><a href="javascript:void(0)">下一页</a></li>  ';
            content+= '<li class="disabled"><a href="javascript:void(0)">末页</a></li>  ';
        }else{
            content+= '<li ><a href="javascript:ajax_select_page('+(pageno+1)+')">下一页</a></li>  ';
            content+= '<li ><a href="javascript:ajax_select_page('+totalpage+')">末页</a></li>  ';
        }

        $("tfoot ul").html(content);

    }*/

    //给每个删除按钮绑定删除单个用户方法
    $("tbody").on("click",".btn-danger",function () {
        var name =  $(this).parents("tr").find("td:eq(2)").text();
        var id = this.id;
        layer.confirm("确定要删除"+name+"吗？",{icon:3,title:'提示'},function (index) {
            $.ajax({
                url:"${APP_PATH}/user/toDelUser.do",
                data:{"id":id},
                type:"POST",
                success:function (data) {
                    if(data.success){
                        layer.msg(data.message,{icon:6,time:1000,shift:5},function () {
                            window.location.reload();
                        });

                    }else{
                        layer.msg(data.message,{icon:5,time:1000,shift:5});
                    }
                },
                error:function () {
                    layer.msg("删除失败出现异常",{icon:5,time:1000,shift:5});
                }
            })
            layer.close(index)
        },function (index) {
            layer.close(index)
        });
    })


    //删除用到得单选框和全选框之间绑定联系
    $("#delAll").click(function () {
       var flag = $("#delAll").prop("checked");
       $("tbody input[name='delCheckBox']").prop("checked",flag);
    })

    $("tbody").on("click",".delCB",function () {
        var flag2 = ($("tbody input[name='delCheckBox']").length)==($("tbody input:checked").length);
        $("#delAll").prop("checked",flag2);
    })



    //删除多个
    $("#delBtn").click(function () {
        var nameContent = '';
        var jsonObj = {};
        var length = $("tbody input:checked").length;
        $("tbody input:checked").each(function (index, user) {
            nameContent+=   $(user).parents("tr").find("td:eq(2)").text()+'-';
            jsonObj["datas["+index+"].id"] = user.id;
        })
       var name =  nameContent.substring(0,nameContent.length-1);
       if(length>0){
           layer.confirm("确定要删除["+name+"]这些用户吗?",{icon:3,title:"提示"},function (index) {
               $.ajax({
                   type:"POST",
                   url:"${APP_PATH}/user/toDelUserList.do",
                   data:jsonObj,
                   success:function (data) {
                       if(data.success){
                           layer.msg(data.message,{time:1000,icon:6,shift:5},function () {
                               window.location.href="${APP_PATH}/user/toIndex.htm";
                           })
                       }else{
                           layer.msg(data.message,{time:1000,icon:5,shift:5})
                       }
                   },
                   error:function () {
                       layer.msg("删除异常!",{time:1000,icon:5,shift:5})
                   }
               });
               layer.close(index);
           },function (index) {
               layer.close(index);
           })
       }else{
           layer.msg("请选择要删除的用户?",{time:1000,icon:5,shift:5});
       }

    });










</script>
</body>
</html>

