<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th>Name</th>
            <th>Date</th>
            <th>Time</th>
            <th>Venue</th>
            <th></th>
        </tr>
        ${currentPage}
        ${nOfPages}
        <c:forEach items="${events}" var="eachEvent">
            <tr>
                <td>${eachEvent.name}</td>
                <td>${eachEvent.date}</td>
                <td>${eachEvent.time}</td>
                <td>${eachEvent.venue.name}</td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Navigation for events">
    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li class="page-item"><a class="page-link"
                href="mainservlet?command=users&currentPage=${currentPage-1}">Previous</a>
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
                href="mainservlet?command=users&currentPage=${currentPage+1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>
<jsp:include page="footer.jsp"/>