<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head><title>Error 500</title></head>
<body>
Error 500

Request from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet name: ${pageContext.errorData.servletName}
<br/>
Status code: ${pageContext.errorData.statusCode}
<br/>
Exception: ${pageContext.exception}
<br/>
Exception stack: ${pageContext.exception.stacktrace}
<br/>
Message from exception: ${pageContext.exception.message}
<br/>
</body>
</html>