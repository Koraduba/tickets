<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.CART" bundle="${rb}" />

<c:if test="${car==null}">
<fmt:message key="label.is_empty" bundle="${rb}" />
</c:if>
 <c:if test="${cart!=null}">
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th><fmt:message key="label.event" bundle="${rb}" /></th>
            <th><fmt:message key="label.ticket" bundle="${rb}" /></th>
            <th><fmt:message key="label.price" bundle="${rb}" /></th>
            <th><fmt:message key="label.quantity" bundle="${rb}" /></th>
            <th><fmt:message key="label.total" bundle="${rb}" /></th>
        </tr>
        <c:forEach items="${cart}" var="orderLine">
            <tr>
                <td>${orderLine.ticket.event.name}</td>
                <td>${orderLine.ticket.category}</td>
                <td>${orderLine.ticket.price}</td>
                <td>${orderLine.ticketQuantity}</td>
                <td>${orderLine.ticket.price*orderLine.ticketQuantity}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
  <div class="btn-group" role="group">
            <form name="order" method="POST" action="mainservlet">
				<td>${ticket.category}</td>
				<td>${ticket.price}</td>
				<fmt:message key="label.clear" bundle="${rb}" var="localizedClear"/>
				<td><input type="submit" value="${localizedClear}"/></td>
                <input type="hidden" name="command" value="clear_cart" />
            </form>
  </div>

  <div class="btn-group" role="group">
            <form name="order" method="POST" action="mainservlet">
				<td>${ticket.category}</td>
				<td>${ticket.price}</td>
			     <fmt:message key="label.place_order" bundle="${rb}" var="localizedPlaceOrder"/>
				<td><input type="submit" value="${localizedPlaceOrder}"/></td>
                <input type="hidden" name="command" value="order" />
            </form>
  </div>
  </c:if>

</div>

<jsp:include page="footer.jsp"/>