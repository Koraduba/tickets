<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.ORDER" bundle="${rb}"/>
<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <tr>
                <td><fmt:message key="label.name" bundle="${rb}" /></td>
                <td>${order.orderId}</td>
            </tr>
            <tr>
                <td><fmt:message key="label.description" bundle="${rb}" /></td>
                <td>${order.date}</td>
            </tr>
    </table>
</div>
</b>
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th><fmt:message key="label.event_name" bundle="${rb}" /></th>
            <th><fmt:message key="label.ticket" bundle="${rb}" /></th>
            <th><fmt:message key="label.price" bundle="${rb}" /></th>
            <th><fmt:message key="label.quantity" bundle="${rb}" /></th>
            <th><fmt:message key="label.total" bundle="${rb}" /></th>
        </tr>
        <c:forEach items="${orderLines}" var="orderLine">
            <tr>
                <td>${orderLine.ticket.event.name}</td>
                <td>${orderLine.ticket.category}</td>
                <td>${orderLine.ticket.price}</td>
                <td>${orderLine.ticketQuantity}</td>
                <td>${orderLine.ticket.price*orderLine.ticketQuantity}</td>
            </tr>
        </c:forEach>
    </table>


<jsp:include page="footer.jsp"/>