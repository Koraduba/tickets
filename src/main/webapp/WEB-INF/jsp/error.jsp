<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<html>
<head>
<title>Error</title>
</head>
    <body>
    Operation cannot be executed due to internal error!
    <form action="mainservlet">
    <input type="hidden" name="command" value="${command}">
        <button type="submit">BACK</button>
    </form>


  </body>
</html>