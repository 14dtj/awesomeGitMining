<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Awesome gitmining</title>
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/indexpage.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/animate.css"/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value="/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css" media="all">
</head>
<body>
    <div class="templatemo-top-nav-container">
        <div class="row">
            <nav class="templatemo-top-nav">
                <ul>
                    <li><a href="#" class="active"><img src="/img/test.png"></a></li>
                    <li><a href="/repo/repos?pager.offset=0">Repository</a></li>
                    <li><a href="/user/users?pager.offset=0">User</a></li>
                    <li><a href="/statistics/repository">Repository Statistics</a></li>
                    <li><a href="/statistics/user">User Statistics</a></li>
                    <li><a href="/statistics/bigQuery">Big Query</a></li>
                    <li><a href="/recommend">Recommended</a></li>
                </ul>
            </nav>

            <div class="dropdown navbar-right">
                <%session.setAttribute("backuri","/");%>
                <%
                    if(session.getAttribute("loginMember")==null){
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
                    }else{
                %>
                <a href="#" id="drop_a" data-toggle="dropdown">
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

    <div class="intro-header animated fadeInUp">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h1>Awesome gitmining</h1>
                        <h2>Search what you are interested in on Github</h2>
                        <hr class="intro-divider">
                        <ul class="list-inline intro-social-buttons">
                            <li>
                                <a href="/repo/repos?pager.offset=0" class="btn btn-default btn-lg"><strong>Get Started</strong>
                                    <i class="glyphicon glyphicon-chevron-right"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer class="text-right">
        <p><strong>Copyright &copy; 2A617.</strong> All Rights Reserved</p>
    </footer>

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</body>
</html>
