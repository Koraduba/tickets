<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
CART

<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th>Event</th>
            <th>Ticket</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
        </tr>
        <c:forEach items="${cart}" var="orderLine">
            <tr>
                <td>${orderLine.ticket.event.name}</td>
                <td>${orderLine.ticket.category}</td>
                <td>${orderLine.ticket.price}</td>
                <td>${orderLine.ticketQuantity}</td>
                <td>${orderLine.ticket.price}*${orderLine.ticketQuantity}</td>
            </tr>
        </c:forEach>
    </table>
</div>
            <form name="order" method="POST" action="mainservlet">
				<td>${ticket.category}</td>
				<td>${ticket.price}</td>
				<td><input type="submit" value="ORDER"/></td>
                <input type="hidden" name="command" value="order" />
            </form>
<jsp:include page="footer.jsp"/>