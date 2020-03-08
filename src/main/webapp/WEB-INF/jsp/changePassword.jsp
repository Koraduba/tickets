<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<form action="mainservlet" method="POST">
<div class="form-group">
    <label for="oldPassword"><fmt:message key="label.old_password" bundle="${rb}" /></label>
    <input type="password" name="oldPassword" class="form-control" id="oldPassword">
<font color="red">${errorChangePasswordMessage}</font>
</div>
<div class="form-group">
    <label for="newPassword"><fmt:message key="label.new_password" bundle="${rb}" /></label>
    <input type="password" name="newPassword" class="form-control" id="newPassword">
    <input type="hidden" name="command" value="new_password">
    <font color="red">${errorUserPasswordMessage}</font>
</div>

</br>
<fmt:message key="label.change" bundle="${rb}" var="localizedChange" />
<input type="submit" value="${localizedChange}">
</form>

<jsp:include page="footer.jsp"/>