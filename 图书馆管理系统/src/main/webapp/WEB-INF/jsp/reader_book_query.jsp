<%@ page import="com.book.domain.Book" %><%--
  Created by IntelliJ IDEA.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Query</title>
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


<div style="padding: 30px 550px 10px">
    <form   method="post" action="reader_querybook_do.html" class="form-inline"  id="searchform">
        <div class="input-group">
            <input type="text" placeholder="Input book name or ISBN or BookId" class="form-control" id="search" name="searchWord" class="form-control">
            <span class="input-group-btn">
                            <input type="submit" value="Search now!" class="btn btn-default">
            </span>
        </div>
    </form>
    <script>
        function mySubmit(flag){
            return flag;
        }
        $("#searchform").submit(function () {
            var val=$("#search").val();
            if(val==''){
                alert("Please input book name or ISBN or BookId");
                return mySubmit(false);
            }
        })
    </script>
</div>
<div style="position: relative;top: 10%">
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
</div>
<c:if test="${!empty books}">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                Result：
            </h3>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Book name</th>
                    <th>Author</th>
                    <th>Publisher</th>
                    <th>Book ID</th>
                    <th>ISBN</th>
                    <th>Price</th>
                    <th>State</th>
                    <th>Details</th>
                    <th>Reserve</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td><c:out value="${book.name}"></c:out></td>
                        <td><c:out value="${book.author}"></c:out></td>
                        <td><c:out value="${book.publish}"></c:out></td>
                        <td><c:out value="${book.bookId}"></c:out></td>
                        <td><c:out value="${book.isbn}"></c:out></td>
                        <td><c:out value="${book.price}"></c:out></td>
                        <c:if test="${book.state==1}">
                            <td style="color: green">Available</td>
                        </c:if>
                        <c:if test="${book.state==0}">
                            <td style="color: blue">Lent</td>
                        </c:if>
                        <c:if test="${book.state==-1}">
                            <td style="color: red">Reserved</td>
                        </c:if>
                        <td><a href="readerbookdetail.html?bookId=<c:out value="${book.bookId}"></c:out>"><button type="button" class="btn btn-success btn-xs">Details</button></a></td>
                        <td><button id="update" type="button" class="btn btn-success btn-sm" class="text-left" onclick="location='readerreservedetail.html?readerId=${readercard.readerId}&bookId=${book.bookId}'">Reserve</button></td>
                            <%--<td><a href="readerReservedetail.html?readerId=<c:out value="${readercard.readerId}"></c:out>&bookId=<c:out value="${book.bookId}"></c:out>"><button type="button" class="btn btn-success btn-xs">Reserve</button></a></td>--%>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>


</body>
</html>
