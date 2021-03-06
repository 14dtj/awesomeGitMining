<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Statistics</title>
    <link href="<c:url value="/css/indexpage.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/animate.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css" media="all">
    <style>
        .title{
            font-family:Arial,微软雅黑;
            font-size:18px;
            text-anchor:middle;
            text-align:center;
        }
        .chord path {
            fill-opacity: 0.67;
            stroke: #000;
            stroke-width: 0.5px;
        }
    </style>
</head>
<body>

<div class="templatemo-top-nav-container">
    <div class="row">
        <nav class="templatemo-top-nav">
            <ul>
                <li><a href="/index.jsp">Home</a></li>
                <li><a href="/repo/repos?pager.offset=0">Repository</a></li>
                <li><a href="/user/users?pager.offset=0">User</a></li>
                <li><a href="/statistics/repository">Repository Statistics</a></li>
                <li><a href="#" class="active">User Statistics</a></li>
                <li><a href="/statistics/bigQuery">Big Query</a></li>
                <li><a href="/recommend">Recommended</a></li>
            </ul>
        </nav>

        <div class="dropdown navbar-right">
            <%session.setAttribute("backuri", "/");%>
            <%
                if (session.getAttribute("loginMember") == null) {
            %>
            <a href="#" id="drop_a" data-toggle="dropdown">
                Visitors <b class="caret"></b>
            </a>
            <ul class="dropdown-menu animated fadeInRight">
                <li>
                    <a href="/login.jsp">Sign in</a>
                </li>
                <li>
                    <a href="/register">Sign up</a>
                </li>
            </ul>
            <%
            } else {
            %>
            <a href="#" id="drop_a" data-toggle="dropdown" >
                <%=session.getAttribute("loginMember")%><b class="caret"></b>
            </a>
            <ul class="dropdown-menu animated fadeInRight">
                <li>
                    <a href="/favouriteRepos">Favorite Repositories</a>
                </li>
                <li>
                    <a href="/logout">Log out</a>
                </li>
            </ul>
            <%
                }
            %>
        </div>
    </div>
</div>


<div class="templatemo-flex-row">
    <div class="templatemo-content col-1 light-gray-bg">
        <div class="templatemo-flex-row flex-content-row">
            <div class="templatemo-content-widget white-bg col-1 animated fadeInLeft">
                <h2>User Statistics</h2>
                <hr>
                <div id="type-pie-local" style="width: 100%;height:400px;"></div>
                <hr size="2">
                <div id="create_year" style="width: 100%;height:400px;"></div>
                <hr size="2">
                <div id="company-pie-local" style="width: 100%;height:400px;"></div>
                <hr size="2">
                <div id="blog" style="width: 100%;height:400px;"></div>
                <hr size="2">
                <div id="email" style="width: 100%;height:400px;"></div>
                <hr size="2">
                <div id="follow" style="width: 100%;height:400px;"></div>
                <hr size="2">
                <div id="chord" style="width: 100%;height:1000px;"></div>
                <div id="worldMap" style="width: 100%;height:600px;"></div>

            </div>
        </div>
    </div>
</div>

<footer class="text-right">
    <p><strong>Copyright &copy; 2A617.</strong> All Rights Reserved</p>
</footer>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.6/d3.min.js" charset="utf-8"></script>
<script src="/js/library/d3.min.js" charset="utf-8"></script>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/Chart.bundle.js"></script>
<script src="/js/echarts.min.js"></script>
<script src="/js/macarons.js"></script>
<script src="/js/userStatistics.js"></script>
<script src="/js/worldMap.js"></script>
<script src="/js/world.js"></script>
<script src="/js/chord.js"></script>
</body>
</html>
