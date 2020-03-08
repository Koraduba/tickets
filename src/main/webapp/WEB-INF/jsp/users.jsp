<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.USERS" bundle="${rb}"/>
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th><fmt:message key="label.id" bundle="${rb}" /></th>
            <th><fmt:message key="label.login" bundle="${rb}" /></th>
            <th><fmt:message key="label.name" bundle="${rb}" /></th>
            <th><fmt:message key="label.surname" bundle="${rb}" /></th>
            <th><fmt:message key="label.email" bundle="${rb}" /></th>
            <th><fmt:message key="label.role" bundle="${rb}" /></th>
        </tr>
        <c:forEach items="${users}" var="eachUser">
            <tr>
                <td>${eachUser.userId}</td>
                <td>${eachUser.login}</td>
                <td>${eachUser.name}</td>
                <td>${eachUser.surname}</td>
                <td>${eachUser.email}</td>
                <td>${eachUser.role}</td>

            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Navigation for users">
    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li class="page-item"><a class="page-link"
                href="mainservlet?command=users&currentPage=${currentPage-1}"><fmt:message key="label.previous" bundle="${rb}" /></a>
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
                        href="mainservlet?command=users&currentPage=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt nOfPages}">
            <li class="page-item"><a class="page-link"
                href="mainservlet?command=users&currentPage=${currentPage+1}"><fmt:message key="label.next" bundle="${rb}" /></a>
            </li>
        </c:if>
    </ul>
</nav>
<jsp:include page="footer.jsp"/>
