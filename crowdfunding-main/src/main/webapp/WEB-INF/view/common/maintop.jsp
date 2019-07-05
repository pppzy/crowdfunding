<%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/5
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <title>maintop</title>
</head>
<body>
<li style="padding-top:8px;">
    <div class="btn-group">
        <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
            <i class="glyphicon glyphicon-user"></i> ${sessionScope.user.username} <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" role="menu">
            <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
            <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
            <li class="divider"></li>
            <li><a href="${APP_PATH}/loginout.do"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
        </ul>
    </div>
</li>
<li style="margin-left:10px;padding-top:8px;">
    <button type="button" class="btn btn-default btn-danger">
        <span class="glyphicon glyphicon-question-sign"></span> 帮助
    </button>
</li>
</body>
</html>
