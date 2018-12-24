<%--
  Created by IntelliJ IDEA.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${readercard.name}'s Home Page</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
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

<c:if test="${!empty succ}">
    <div class="alert alert-success alert-dismissable">
        <button type="button" class="close" data-dismiss="alert"
                aria-hidden="true">
            &times;
        </button>
            ${succ}
    </div>
</c:if>
<c:if test="${!empty error}">
    <div class="alert alert-danger alert-dismissable">
        <button type="button" class="close" data-dismiss="alert"
                aria-hidden="true">
            &times;
        </button>
            ${error}
    </div>
</c:if>
<div class="col-xs-5 col-md-offset-3">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                My information
            </h3>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <tr>
                    <th width="20%">Reader Id</th>
                    <td>${readerinfo.readerId}</td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td>${readerinfo.name}</td>
                </tr>
                <tr>
                    <th>Sex</th>
                    <td>${readerinfo.sex}</td>
                </tr>
                <tr>
                    <th>Birthday</th>
                    <td>${readerinfo.birth}</td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td>${readerinfo.address}</td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>${readerinfo.telcode}</td>
                </tr>
                <tr>
                    <th>fine</th>
                    <td>${readerinfo.money}</td>
                </tr>
                <tr>
                    <th>Borrowing Books Number</th>
                    <td>${readerinfo.current_book}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <a class="btn btn-success btn-sm" href="reader_info_edit.html" role="button">Modify</a>
    </div>
</div>


</body>
</html>
