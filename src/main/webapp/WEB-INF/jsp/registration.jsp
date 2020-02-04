<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <title>$Start Page$</title>
</head>
<body>
<div class="container">
    <div class="row">
    </div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">

            <form action="${pageContext.request.contextPath}/mainservlet?command=register" method="post">
                <div class="form-group">
                    <label for="exampleInputEmail1">Email address</label>
                    <input type="email" name="email" class="form-control" id="exampleInputEmail1"
                           aria-describedby="emailHelp" placeholder=${email} value=${email}>
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.
                    </small>
                </div>
                <div class="form-group">
                    <label for="firstName">First name</label>
                    <input type="firstName" name="name" class="form-control" id="firstName"
                           value=${name}>
                </div>
                <div class="form-group">
                    <label for="lastName">Last name</label>
                    <input type="lastName" name="surname" class="form-control" id="lastName"
                           placeholder=${surname} value=${surname}>
                </div>
                <div class="form-group">
                    <label for="login">Login</label>
                    <input type="login" name="login" class="form-control" id="login" value=${login}>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" name="password" class="form-control" id="password">
                </div>
                <br/>
                ${errorLoginPassMessage}
                <div>
                    <fieldset>
                        <legend>Select a user role</legend>

                        <div>
                            <input type="radio" id="user" name="role" checked value="USER" />
                            <label for="user">USER</label>
                        </div>

                        <div>
                            <input type="radio" id="admin" name="role" value="ADMINISTRATOR" />
                            <label for="admin">ADMIN</label>
                        </div>
                    </fieldset>
                </div>


                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
