<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<html>
<head>
    <meta charset="UTF-8">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>$Registration$</title>
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

                <c:if test="${role=='ADMINISTRATOR'}">
                <div>
                    <fieldset>
                        <legend>Select a user role</legend>
                        <div>
                            <input type="radio" id="user" name="role" checked value="USER" />
                            <label for="user">USER</label>
                        </div>
                        <div>
                            <input type="radio" id="vip_user" name="role" value="VIP_USER" />
                            <label for="vip_user">VIP USER</label>
                        </div>
                        <div>
                            <input type="radio" id="host" name="role" value="HOST" />
                            <label for="host">HOST</label>
                        </div>
                        <div>
                            <input type="radio" id="admin" name="role" value="ADMINISTRATOR" />
                            <label for="admin">ADMINISTRATOR</label>
                        </div>
                    </fieldset>
                </div>
                </c:if>
                <c:if test="${role!='ADMINISTRATOR'}">
                    <input type="hidden" name="role" value="USER" />
                </c:if>


                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
