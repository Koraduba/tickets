<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<html><head><title>Login</title></head>
    <body>
    <ctg:info-time/>
        <form name="loginForm" method="POST" action="mainservlet">
            <input type="hidden" name="command" value="login" />
            <fmt:message key="label.login" bundle="${rb}" /><br/>
            <input type="text" name="login" value=""/>
            <br/><fmt:message key="label.password" bundle="${rb}" /><br/>
            <input type="password" name="password" value=""/>
            <br/>
            ${errorLoginPassMessage}
            <br/>
            ${wrongAction}
            <br/>
            ${nullPage}
            <br/>
            <input type="submit" value="Login"/>
        </form><hr/>
              <h3>File Upload:</h3>
              Select a file to upload: <br />
              <form action = "uploadservlet" method = "post"
                 enctype = "multipart/form-data">
                 <input type = "file" name = "file" size = "50" />
                 <br />
                 <input type = "submit" value = "Upload File" />
              </form>

              <form action = "mainservlet" method = "post">
               <input type="hidden" name="command" value="new_user" />
                 <br />
                 <input type = "submit" value = "Register" />
              </form>

              <form action = "emailservlet" method = "get">
                 <input type = "submit" value = "Email" />
              </form>

    </body></html>