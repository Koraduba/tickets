<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<form action = "uploadservlet" id="form2" method = "post" enctype = "multipart/form-data">
    <input type = "file" name = "file" size = "50" />
    <br />
    <button type="submit">ADD IMAGE</button>
</form>
<jsp:include page="footer.jsp"/>
