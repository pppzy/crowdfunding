<%@ page import="com.itpzy.crowdfunding.bean.Tag" %><%--
  Created by IntelliJ IDEA.
  User: ppp
  Date: 2019/7/13
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul style="padding-left:0px;" class="list-group">


    <c:forEach items="${sessionScope.rootPermission}" var="permissionRoot" >
        <li class="list-group-item tree-closed" >
            <a href="javascript:void(0)"><i class="${permissionRoot.icon}"></i>${permissionRoot.name}</a>
        </li>
        <c:forEach items="${permissionRoot.children}" var="permission">
            <c:if test="${empty permission.children}">
                <li class="list-group-item tree-closed" >
                    <a href="${APP_PATH}/${permission.url}"><i class="${permission.icon}"></i>${permission.name}</a>
                </li>
            </c:if>
            <c:if test="${not empty permission.children}">
                <li class="list-group-item tree-closed">
                    <span><i class="${permission.icon}"></i> ${permission.name} <span class="badge" style="float:right">${fn:length(permission.children)}</span></span>
                    <ul style="margin-top:10px;display:none;">
                        <c:forEach items="${permission.children}" var="child">
                            <li style="height:30px;">
                                <a href="${APP_PATH}/${child.url}"><i class="${child.icon}"></i>${child.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:if>
        </c:forEach>
    </c:forEach>
</ul>
