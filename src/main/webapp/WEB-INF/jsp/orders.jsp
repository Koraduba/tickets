<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
ORDERS
<c:if test="${role=='ADMINISTRATOR'}">
    <form name="back" action="mainservlet?command=statistic">
        <td><input type="submit" value="Statistic"/></td>
    </form>
</c:if>
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Total sum</th>
        </tr>
        <c:forEach items="${orders}" var="order" varStatus="status">
        <tr>
                <td>${order.orderId}</td>
                <td>${order.date}</td>
                <td>${sums[status.index]}</td>
        </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp"/>