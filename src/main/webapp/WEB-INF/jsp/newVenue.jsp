<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newVenue</title></head>
<body>
    <form action="${pageContext.request.contextPath}/mainservlet" id="form1" method="post" name="form1">
        <input type="hidden" name="command"/>
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
        <div class="form-group">
        <c:if test="${path!=null}">
          <img src="${path}" class="img-fluid" alt="Responsive image">
          <br/>
        </c:if>
          <input type="submit" value="Add image" onclick="setCommand('upload')">
          <br/>
    </div>
          <input type="submit" value="Add venue" onclick="setCommand('add_venue')">
    </form>

      <script>
      function setCommand (commandType){
      document.forms['form1'].elements.command.value=commandType;
      }
      </script>
<jsp:include page="footer.jsp"/>