<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
EVENT

<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <tr>
                <td>${event.name}</td>
            </tr>
            <tr>
                <td>${event.description}</td>
            </tr>
            <tr>
                <td><img src="${event.image}" class="img-fluid" alt="Responsive image"></td>
            </tr>
            <tr>
                <td>${event.date}</td>
            </tr>
            <tr>
                <td>${event.time}</td>
            </tr>
            <tr>
                <td>${event.venue}</td>
            </tr>

            <c:forEach items="${tickets}" var="ticket">
            <tr>
            <form name="to_cart" method="POST" action="mainservlet">
				<td>${ticket.category}</td>
				<td>${ticket.price}</td>
				<td><input type="number" class="form-control" id="quantity" name="quantity"></td>
				<td><input type="submit" value="to Cart"/></td>
                <input type="hidden" name="command" value="order_line" />
                <input type="hidden" name="event" value="${eachEvent.eventId}" />
                <input type="hidden" name="ticket" value="${ticket.ticketId}" />
            </form>
            </tr>
			</c:forEach>
	</table>
 </div>
<jsp:include page="footer.jsp"/>
