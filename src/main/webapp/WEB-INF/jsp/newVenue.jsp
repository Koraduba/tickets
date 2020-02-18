<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newVenue</title></head>
<body>
    <form action="${pageContext.request.contextPath}/mainservlet?command=add_venue" method="post">
          <div class="form-group row">
            <label for="name" class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="name" name="name">
            </div>
          </div>
          <div class="form-group row">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity</label>
            <div class="col-sm-10">
              <input type="number" class="form-control" id="capacity" name="capacity">
            </div>
           <input type="submit" value="SUBMIT">
    </form>
<jsp:include page="footer.jsp"/>