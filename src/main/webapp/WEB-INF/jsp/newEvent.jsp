<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>Index</title></head>
<body>
NEW EVENT

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