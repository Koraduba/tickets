<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<fmt:message key="label.STATISTIC" bundle="${rb}"/>
<form class="form-inline" action="${pageContext.request.contextPath}/mainservlet?command=orders_below_threshold" method="post">
  <div class="form-group">
    <label for="input1"><fmt:message key="label.orders_below" bundle="${rb}"/></label>
    <input type="number" id="input1" class="form-control mx-sm-3" name="amount">
    <fmt:message key="label.get" bundle="${rb}" var="localizedGet" />
    <input type="submit" value="${localizedGet}">
  </div>
</form>
<form class="form-inline" action="${pageContext.request.contextPath}/mainservlet?command=orders_above_threshold" method="post">
  <div class="form-group">
    <label for="input1"><fmt:message key="label.orders_above" bundle="${rb}"/></label>
    <input type="number" id="input1" class="form-control mx-sm-3" name="amount">
    <input type="submit" value="${localizedGet}">
  </div>
</form>
<font color="red">${errorOrderAmountMessage}</font>

<jsp:include page="footer.jsp"/>