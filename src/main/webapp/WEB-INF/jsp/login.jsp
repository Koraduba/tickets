<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<html>
<head>
<title>Login</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
    <body>
    <img src="${pageContext.request.contextPath}/img/login.jpg" class="rounded mx-auto d-block" alt="...">
    <hr>
    <div class="mx-auto" style="width: 200px;">
        <form name="loginForm" method="POST" action="mainservlet">
            <input type="hidden" name="command" value="login" />
            <fmt:message key="label.login" bundle="${rb}" /><br/>
            <input type="text" name="login" required/>
            <br/><fmt:message key="label.password" bundle="${rb}" /><br/>
            <input type="password" name="password" required/>
            <br/>
            ${errorLoginPassMessage}
            <br/>
            <fmt:message key="label.enter" bundle="${rb}" var="localizedEnter"/><br/>
            <input type="submit" value="${localizedEnter}"/>
        </form>
        <form name="register" method="POST" action="mainservlet">
             <input type="hidden" name="command" value="new_user" />
             <fmt:message key="label.signin" bundle="${rb}" var="localizedSignIn"/><br/>
             <input type="submit" value="${localizedSignIn}"/>
        </form>
        <form name="register" method="POST" action="mainservlet">
             <input type="hidden" name="command" value="guest" />
             <fmt:message key="label.guest" bundle="${rb}" var="localizedGuest"/><br/>
             <input type="submit" value="${localizedGuest}"/>
        </form>

    </div>
<jsp:include page="footer.jsp"/>