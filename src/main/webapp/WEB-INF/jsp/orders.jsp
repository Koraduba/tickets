<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.ORDERS" bundle="${rb}"/>
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <c:if test="${role=='ADMINISTRATOR'}">
            <th>User login</th>
            </c:if>
            <th><fmt:message key="label.id" bundle="${rb}" /></th>
            <th><fmt:message key="label.date" bundle="${rb}" /></th>
            <th><fmt:message key="label.total_sum" bundle="${rb}" /></th>
            <th></th>

        </tr>
        <c:forEach items="${orders}" var="order" varStatus="status">
        <tr>
            <c:if test="${role=='ADMINISTRATOR'}">
                <td>${order.user.login}</td>
            </c:if>
                <td>${order.orderId}</td>
                <td>${order.date}</td>
                <td>${sums[status.index]}</td>
                <td><a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=order_info&order_id=${order.orderId}"><fmt:message key="label.details" bundle="${rb}"/><br/></a></td>

        </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp"/>