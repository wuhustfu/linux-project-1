<%@ page import="com.book.domain.ReserveInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: ambition
  Date: 17/11/15
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>My Reserve</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <style>
        body{
            background-color: rgb(240,242,245);
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default" role="navigation" style="background-color:#fff" style="background-color:#fff">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand " href="reader_main.html"><p class="text-primary">My Library</p></a>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li class="active">
                    <a href="reader_querybook.html" >
                        Book Query
                    </a>
                </li>
                <li>
                    <a href="reader_info.html" >
                        Personal Information
                    </a>
                </li>
                <li >
                    <a href="mylend.html" >
                        My Borrow
                    </a>
                </li>
                <li >
                    <a href="myReserve.html" >
                        My Reservation
                    </a>
                </li>
                <li >
                    <a href="reader_repasswd.html" >
                        Password Modification
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="reader_info.html"><span class="glyphicon glyphicon-user"></span>&nbsp;Welcome,${readercard.name}</a></li>
                <li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Log out</a></li>
            </ul>
        </div>
    </div>
</nav>


<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">
            My Reserving book
        </h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Book Id</th>
                <th>Reserved date</th>
            </tr>
            <font color="#FF0000">Your reservation books will be only kept 10 minutes, please borrow them as soon as possible! </font>
            <tbody>
            <c:forEach items="${list}" var="alog">
                <tr>
                    <td><c:out value="${alog.bookId}"></c:out></td>
                    <td><c:out value="${alog.date}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
