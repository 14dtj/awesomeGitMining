<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pg"  uri="http://jsptags.com/tags/navigation/pager" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Result</title>
    <link href="<c:url value="/css/listcss.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/bootstrap.css"/>" rel="stylesheet" type="text/css" media="all">
</head>
<body>
<div class="row">

    <div class="well">
        <form action="/repo/search" method="post">
            <input type="text" class="search-query form-control col-md-10" name="name" placeholder="Search keyword..."><br>
            <button type="submit" class="btn btn-primary btn-sm">Search</button>
        </form>
    </div>

    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well">
                <h2> 搜索结果列表</h2>
            </div>
            <div class="box-content">
                <div class="box-content">
                    <ul class="dashboard-list">
                        <c:forEach items="${repos }" var="repo">
                            <li>
                                <h3><i class="glyphicon glyphicon-th"><strong><a href="${repo.fullName }">${repo.fullName }</a></strong></i></h3>
                                <p><strong>${repo.language}</strong></p>
                                <p><strong>Subscribers:</strong>&nbsp;${repo.subscribersCount}
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <strong>Forks:</strong>&nbsp;${repo.forksCount}
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <strong>Stargazers:</strong>&nbsp;${repo.stargazersCount}</p>
                                <p>${repo.description}</p>
                                <strong>Last Updated:</strong>&nbsp;${repo.updatedAt}<br>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <ul class="pagination pagination-centered">
            ${pageUrl}<br>
            <pg:pager url="/repo/search" items="${total}">
                <li>
                    <pg:first>
                        <a href="${pageUrl}">首页</a>
                    </pg:first>
                </li>
                <li>
                    <pg:prev>
                        <a href="${pageUrl }">前页</a>
                    </pg:prev>
                </li>
                <li>
                    <pg:pages>
                        <a href="${pageUrl }">${pageNumber }</a>
                    </pg:pages>
                </li>
                <li>
                    <pg:next>
                        <a href="${pageUrl }">后页</a>
                    </pg:next>
                </li>
                <li>
                    <pg:last>
                        <a href="${pageUrl }">尾页</a>
                    </pg:last>
                </li>
            </pg:pager>
        </ul>
    </div>
</div>
</body>
</html>
