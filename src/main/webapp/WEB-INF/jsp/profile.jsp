<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.PROFILE" bundle="${rb}"/>
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th><fmt:message key="label.name" bundle="${rb}"/></th>
            <td>${user.name}</td>
        </tr>
        <tr>
            <th><fmt:message key="label.surname" bundle="${rb}"/></th>
            <td>${user.surname}</td>
        </tr>
        <tr>
            <th><fmt:message key="label.email" bundle="${rb}"/></th>
            <td>${user.email}</td>
        </tr>
    </table>
</div>
            <form name="changePassword" method="POST" action="mainservlet">
            <fmt:message key="label.change_password" bundle="${rb}" var="localisedChangePassword"/>
				<td><input type="submit" value="${localisedChangePassword}"/></td>
                <input type="hidden" name="command" value="new_password_page" />
            </form>
<jsp:include page="footer.jsp"/>