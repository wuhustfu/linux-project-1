<%--
  Created by IntelliJ IDEA.
  User: ambition
  Date: 17/11/18
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Add Book Online</title>
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

<div style="padding: 300px 550px 100px">
    <form   method="post" action="book_addol_do.html" class="form-inline"  id="searchform">
        <div class="input-group">
            <input type="text" placeholder="Input book name or ISBN or BookId" class="form-control" id="search" name="isbn" class="form-control">
            <span class="input-group-btn">
                            <input type="submit" value="Add now!" class="btn btn-default">
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
                alert("Please input book's ISBN");
                return mySubmit(false);
            }
        })
    </script>
</div>


</body>
</html>
