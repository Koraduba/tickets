<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
EVENT
<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <tr>
                <td>Name</td>
                <td>${event.name}</td>
            </tr>
            <tr>
                <td>Description</td>
                <td>${event.description}</td>
            </tr>
            <tr>
                <td>Image</td>
                <td><img src="${event.image}" class="img-fluid" alt="Responsive image"></td>
            </tr>
            <tr>
                <td>Date</td>
                <td>${event.date}</td>
            </tr>
            <tr>
                <td>Time</td>
                <td>${event.time}</td>
            </tr>
    </table>
</div>
VENUE
<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <tr>
                <td>Venue name</td>
                <td>${event.venue.name}</td>
            </tr>
            <tr>
                <td>Venue capacity</td>
                <td>${event.venue.capacity}</td>
            </tr>
    </table>
</div>
TICKETS
<div class="row col-md-6">
	<table class="table table-striped table-bordered table-sm">
            <c:forEach items="${tickets}" var="ticket">
            <tr>
            <form name="to_cart" method="POST" action="mainservlet">
				<td>${ticket.category}</td>
				<td>${ticket.price}</td>
				<td><input type="number" class="form-control" id="quantity" name="quantity">
				${errorOrderLineQuantityMessage}
				</td>
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
