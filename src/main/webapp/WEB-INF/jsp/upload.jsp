<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<font color="red">${errorUploadMessage}</font>
<form action = "uploadservlet" method = "post" enctype = "multipart/form-data">
<fmt:message key="label.event" bundle="${rb}" var="localizedEvent"/>
<input type="hidden" name="entity" value="${localizedEvent}">
<div class="form-group">
        <label for="exampleFormControlFile1"><fmt:message key="label.upload_image" bundle="${rb}" /></label>
        <input type="file" class="form-control-file" id="exampleFormControlFile1" name="file" size="50">
</div>
<br/>
    <button type="submit"><fmt:message key="label.add_image" bundle="${rb}" /></button>
</form>
<form action="mainservlet">
<c:if test="${mode=='new'}">
<input type="hidden" name="command" value="new_event_page">
</c:if>
<c:if test="${mode=='edit'}">
<input type="hidden" name="command" value="edit_event_page">
</c:if>
    <button type="submit"><fmt:message key="label.back" bundle="${rb}" /></button>
</form>

<jsp:include page="footer.jsp"/>