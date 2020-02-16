<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newEvent</title></head>
<body>
NEW EVENT

<form action="${pageContext.request.contextPath}/mainservlet?command=add_event" id="form1" method="post">
  <div class="form-group">
    <label for="name">Name</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name">
    </div>
  </div>
  <div class="form-group">
    <label for="date">Date</label>
    <div class="col-sm-10">
      <input type="date" class="form-control" id="date" name="date">
  </div>
  <div class="form-group">
    <label for="time">Time</label>
    <div class="col-sm-10">
      <input type="time" class="form-control" id="time" name="time">
    </div>
  </div>
    <div class="form-group">
      <label for="description">Description</label>
      <div class="col-sm-10">
        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
      </div>
  </div>
    <div class="form-group">
      <label for="price-standard">Standard ticket price</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="price-standard" name="price-standard">
    </div>
      <div class="form-group">
        <label for="price-vip">VIP ticket price</label>
        <div class="col-sm-10">
          <input type="number" class="form-control" id="price-vip" name="price-vip">
      </div>
    <div class="form-group">
      <label for="venue">Venue</label>
      <select id="venue" class="form-control" name="venue">
      <c:forEach items="${venues}" var="venue">
         <option>${venue.name}</option>
      </c:forEach>
      </select>
      <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=new_venue">Add venue</a>
    </div>
  </form>
  <form action = "uploadservlet" id="form2" method = "post" enctype = "multipart/form-data">
            <input type = "file" name = "file" size = "50" />
        <br />
  </form>
  <div class="form-group">
    <div class="col-sm-10">
      <button type="button" onclick="submitForms()">SUBMIT</button>
    </div>
  </div>
<script>
  function submitForms(){
    document.getElementById("form2").submit();
    document.getElementById("form1").submit();
  }
</script>

<jsp:include page="footer.jsp"/>