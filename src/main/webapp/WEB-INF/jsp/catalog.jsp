<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.EVENTS" bundle="${rb}" />
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th><fmt:message key="label.name" bundle="${rb}" /></th>
            <th><fmt:message key="label.date" bundle="${rb}" /></th>
            <th><fmt:message key="label.time" bundle="${rb}" /></th>
            <th><fmt:message key="label.venue" bundle="${rb}" /></th>
            <th></th>
        </tr>
        <c:forEach items="${events}" var="eachEvent">
            <tr>
                <td>${eachEvent.name}</td>
                <td>${eachEvent.date}</td>
                <td>${eachEvent.time}</td>
                <td>${eachEvent.venue.name}</td>

                <td><a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=event&eventId=${eachEvent.eventId}"><fmt:message key="label.details" bundle="${rb}"/><br/></a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Navigation for events">
    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li class="page-item"><a class="page-link"
                href="mainservlet?command=catalog&currentPage=${currentPage-1}"><fmt:message key="label.previous" bundle="${rb}"/><br/></a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${nOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="page-item active"><a class="page-link">
                            ${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link"
                        href="mainservlet?command=catalog&currentPage=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt nOfPages}">
            <li class="page-item"><a class="page-link"
                href="mainservlet?command=catalog&currentPage=${currentPage+1}"><fmt:message key="label.next" bundle="${rb}"/><br/></a>
            </li>
        </c:if>
    </ul>
</nav>
<jsp:include page="footer.jsp"/>