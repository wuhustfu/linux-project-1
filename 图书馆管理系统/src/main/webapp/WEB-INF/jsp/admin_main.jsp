
<%@ page contentType="text/html;charset=UTF-8"  %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Librarian Home Page</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <style>
        body{
            margin: 0;
            padding: 0;
            overflow: visible;
            background-color: rgb(240,242,245);
        }
        #newsa{
            width:500px;
            height: 200px;
            position: fixed;
            left: 35%;
            top:30%;
        }
    </style>

</head>
<body background="img/admin-homepage.jpg">
<!-- 导航栏是一个很好的功能，是 Bootstrap 网站的一个突出特点。
导航栏在您的应用或网站中作为导航页头的响应式基础组件。
导航栏在移动设备的视图中是折叠的，随着可用视口宽度的增加，导航栏也会水平展开。
在 Bootstrap 导航栏的核心中，导航栏包括了站点名称和基本的导航定义样式。-->
<nav  style="position:fixed;z-index: 999;width: 100%;background-color: #fff" class="navbar navbar-default" role="navigation" >
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="admin_main.html"><p class="text-primary">Library Management System</p></a>
        </div>
        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Book Management
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="allbooks.html">All Books</a></li>
                        <li class="divider"></li>
                        <li><a href="book_add.html">Add Book</a></li>
                        <li class="divider"></li>
                        <li><a href="book_addol.html">Add Book Online</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Reader Management
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="allreaders.html">All Readers</a></li>
                        <li class="divider"></li>
                        <li><a href="reader_add.html">Add Reader</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Log
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="lendlist.html">Lend and Return Log</a></li>
                        <li class="divider"></li>
                        <li><a href="reservelist.html">Reserve Log</a></li>
                    </ul>
                </li>
                <li >
                    <a href="admin_fine.html" >
                        Fine
                    </a>
                </li>
                <li >
                    <a href="admin_repasswd.html" >
                        Password Modification
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="admin_main.html"><span class="glyphicon glyphicon-user"></span>&nbsp;Welcome,${admin.adminId}</a></li>
                <li><a href="logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;log out</a></li>
            </ul>
        </div>
    </div>
</nav>

<c:if test="${!empty login}">
    <script>
        $(function () {
            $("#myModal").modal({
                show: true
            })
        })
    </script>
</c:if>

</body>
</html>
