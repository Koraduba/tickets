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
                <td>${ticket.category}</td>
                <td>${ticket.price}</td>
                <td><a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=add_order_line&eventId=${eachEvent.eventId}&ticket=${ticket.ticketId}">To cart</a></td>
            </tr>
            </c:forEach>
    </table>
 </div>
 <form action="${pageContext.request.contextPath}/mainservlet?command=add_order_line&eventId=${event.eventId}" method="post">

          <div class="form-group row">
            <label for="quantity" class="col-sm-2 col-form-label">Quantity</label>
            <div class="col-sm-10">
              <input type="number" class="form-control" id="quantity" name="quantity">
            </div>
</form>


<jsp:include page="footer.jsp"/>
