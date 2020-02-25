<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newEvent</title></head>
<body>
NEW EVENT
${errorNewEventMessage}
<form action="${pageContext.request.contextPath}/mainservlet" id="form1" method="post" name="form1">
    <input type="hidden" name="command"/>
  <div class="form-group">
    <label for="name">Name</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name" value="${name}">
    </div>
  </div>
  <div class="form-group">
    <label for="date">Date</label>
    <div class="col-sm-10">
      <input type="date" class="form-control" id="date" name="date" value="${date}">
  </div>
  <div class="form-group">
    <label for="time">Time</label>
    <div class="col-sm-10">
      <input type="time" class="form-control" id="time" name="time" value="${time}">
    </div>
  </div>
    <div class="form-group">
      <label for="description">Description</label>
      <div class="col-sm-10">
        <textarea class="form-control" id="description" name="description" rows="3" >${description}</textarea>
      </div>
  </div>
    <div class="form-group">
      <label for="price-standard">Standard ticket price</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="price-standard" name="price-standard" value="${price_standard}">
    </div>
      <div class="form-group">
        <label for="price-vip">VIP ticket price</label>
        <div class="col-sm-10">
          <input type="number" class="form-control" id="price-vip" name="price-vip" value="${price_vip}">
      </div>
    <div class="form-group">
      <label for="venue">Venue</label>
      <select id="venue" class="form-control" name="event_venue">

      <c:forEach items="${venues}" var="venue">
         <option>${venue.name}</option>
      </c:forEach>
      </select>
      <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=new_venue">Add venue</a>
    </div>
        <div class="form-group">
        <c:if test="${path!=null}">
          <img src="${path}" class="img-fluid" alt="Responsive image">
          <br/>
        </c:if>
        <c:if test="${path==null}">
          <input type="submit" value="Add image" onclick="setCommand('upload')">
          <br/>
        </c:if>
    </div>
          <input type="submit" value="Add event" onclick="setCommand('new_event')">
  </form>

  <script>
  function setCommand (commandType){
  document.forms['form1'].elements.command.value=commandType;
  }
  </script>
<jsp:include page="footer.jsp"/>