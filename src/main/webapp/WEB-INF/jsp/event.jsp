<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.EVENT" bundle="${rb}" />
<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <tr>
                <td><fmt:message key="label.name" bundle="${rb}" /></td>
                <td>${event.name}</td>
            </tr>
            <tr>
                <td><fmt:message key="label.description" bundle="${rb}" /></td>
                <td>${event.description}</td>
            </tr>
            <tr>
                <td><fmt:message key="label.image" bundle="${rb}" /></td>
                <td><img src="${event.image}" class="img-fluid" alt="Responsive image"></td>
            </tr>
            <tr>
                <td><fmt:message key="label.date" bundle="${rb}" /></td>
                <td>${event.date}</td>
            </tr>
            <tr>
                <td><fmt:message key="label.time" bundle="${rb}" /></td>
                <td>${event.time}</td>
            </tr>
    </table>
</div>
<fmt:message key="label.VENUE" bundle="${rb}" />
<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <tr>
                <td><fmt:message key="label.venuename" bundle="${rb}" /></td>
                <td>${event.venue.name}</td>
            </tr>
            <tr>
                <td><fmt:message key="label.venuecapacity" bundle="${rb}" /></td>
                <td>${event.venue.capacity}</td>
            </tr>
    </table>
</div>
<fmt:message key="label.TICKETS" bundle="${rb}" />
<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <c:forEach items="${tickets}" var="ticket">
            <tr>
            <form name="to_cart" method="POST" action="mainservlet">
				<td>${ticket.category}</td>
				<td>${ticket.price}</td>
				<td><input type="number" class="form-control" id="quantity" name="quantity">
				<font color="red>"${errorOrderLineQuantityMessage}</font>
				</td>
				<fmt:message key="label.tocart" bundle="${rb}" var="localizedToCart" />
				<td><input type="submit" value="${localizedToCart}"/></td>
                <input type="hidden" name="command" value="order_line" />
                <input type="hidden" name="event" value="${eachEvent.eventId}" />
                <input type="hidden" name="ticket" value="${ticket.ticketId}" />
            </form>
            </tr>
			</c:forEach>
	</table>
 </div>
<jsp:include page="footer.jsp"/>
