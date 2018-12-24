<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>《${detail.name}》</title>
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


<div class="col-xs-6 col-md-offset-3" style="position: relative;top: 10%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">《 ${detail.name}》</h3>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <tr>
                    <th width="15%">Book name</th>
                    <td>${detail.name}</td>
                </tr>
                <tr>
                    <th>Author</th>
                    <td>${detail.author}</td>
                </tr>
                <tr>
                    <th>Publisher</th>
                    <td>${detail.publish}</td>
                </tr>
                <tr>
                    <th>Book ID</th>
                    <td>${detail.bookId}</td>
                </tr>
                <tr>
                    <th>ISBN</th>
                    <td>${detail.isbn}</td>
                </tr>
                <tr>
                    <th>Introduction</th>
                    <td>${detail.introduction}</td>
                </tr>
                <tr>
                    <th>Price</th>
                    <td>${detail.price}</td>
                </tr>
                <tr>
                    <th>Published date</th>
                    <td>${detail.pubdate}</td>
                </tr>
                <tr>
                    <th>Class ID</th>
                    <td>${detail.classId}</td>
                </tr>
                <tr>
                    <th>Location</th>
                    <td>${detail.pressmark}</td>
                </tr>
                <tr>
                    <th>State</th>
                    <c:if test="${detail.state==1}">
                        <td>Available</td>
                    </c:if>
                    <c:if test="${detail.state==0}">
                        <td>Lent</td>
                    </c:if>
                    <c:if test="${detail.state==-1}">
                        <td>Reserved</td>
                    </c:if>

                </tr>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
