<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<font color="red">${errorUploadMessage}</font>
<form action = "uploadservlet" method = "post" enctype = "multipart/form-data">
<input type="hidden" name="entity" value="event">
<div class="form-group">
        <label for="exampleFormControlFile1">Upload image for event:</label>
        <input type="file" class="form-control-file" id="exampleFormControlFile1" name="file" size="50">
</div>
<br/>
    <button type="submit">ADD IMAGE</button>
</form>

${current_page}
<a class="nav-link" href="${pageContext.request.contextPath}${current_page}">BACK</a>
${mode}
<form action="mainservlet">
<c:if test="${mode=='new'}">
<input type="hidden" name="command" value="new_event_page">
</c:if>
<c:if test="${mode=='edit'}">
<input type="hidden" name="command" value="edit_event_page">
</c:if>
    <button type="submit">BACK</button>
</form>

<jsp:include page="footer.jsp"/>