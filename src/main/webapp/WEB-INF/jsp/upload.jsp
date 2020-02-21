<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<form action = "uploadservlet" method = "post" enctype = "multipart/form-data">
    <input type="hidden" name="command" value="upload" />
<div class="form-group">
        <label for="exampleFormControlFile1">Upload image for event:</label>
        <input type="file" class="form-control-file" id="exampleFormControlFile1" name="file" size="50">
</div>
<br/>
    <button type="submit">ADD IMAGE</button>
</form>
${errorUploadMessage}

<jsp:include page="footer.jsp"/>