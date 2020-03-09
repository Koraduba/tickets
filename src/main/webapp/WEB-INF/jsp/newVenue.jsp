<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<html>
<head><title>newVenue</title></head>
<body>
    <form action="${pageContext.request.contextPath}/mainservlet" id="form1" method="post" name="form1">
        <input type="hidden" name="command" value="add_venue"/>
        <input type="hidden" name="entity" value="venue">
          <div class="form-group">
            <label for="name" class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="name" name="name" value="${venueName}">
              <font color="red">${errorVenueNameMessage}</font>
            </div>
          </div>
          <div class="form-group">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity</label>
            <div class="col-sm-10">
              <input type="number" class="form-control" id="capacity" name="capacity" value="${capacity}">
                <font color="red">${errorVenueCapacityMessage}</font>
            </div>
                      <input type="submit" value="Add venue"">
    </form>
<jsp:include page="footer.jsp"/>