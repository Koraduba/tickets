<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<form action="mainservlet" method="POST">
<div class="form-group">
    <label for="oldPassword">Old password</label>
    <input type="password" name="oldPassword" class="form-control" id="oldPassword">
</div>
<div class="form-group">
    <label for="newPassword">New password</label>
    <input type="password" name="newPassword" class="form-control" id="newPassword">
    <input type="hidden" name="command" value="new_password">
</div>
${errorChangePasswordMessage}
</br>
<input type="submit" value="Change">
</form>

<jsp:include page="footer.jsp"/>