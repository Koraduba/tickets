<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
STATISTIC
<form class="form-inline" action="${pageContext.request.contextPath}/mainservlet?command=orders_below_threshold" method="post">
  <div class="form-group">
    <label for="input1">Orders below:</label>
    <input type="number" id="input1" class="form-control mx-sm-3" name="amount">
    <input type="submit" value="GET">
  </div>
</form>
<form class="form-inline" action="${pageContext.request.contextPath}/mainservlet?command=orders_above_threshold" method="post">
  <div class="form-group">
    <label for="input1">Orders above:</label>
    <input type="number" id="input1" class="form-control mx-sm-3" name="amount">
    <input type="submit" value="GET">
  </div>
</form>

<jsp:include page="footer.jsp"/>