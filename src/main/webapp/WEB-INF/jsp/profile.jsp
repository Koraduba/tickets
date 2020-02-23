<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
PROFILE
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <td>Name</th>
            <td>${user.name}</td>
        </tr>
        <tr>
            <td>Surname</th>
            <td>${user.surname}</td>
        </tr>
        <tr>
            <td>Email</th>
            <td>${user.email}</td>
        </tr>
    </table>
</div>
            <form name="changePassword" method="POST" action="mainservlet">
				<td><input type="submit" value="Change password"/></td>
                <input type="hidden" name="command" value="change_password" />
            </form>
<jsp:include page="footer.jsp"/>