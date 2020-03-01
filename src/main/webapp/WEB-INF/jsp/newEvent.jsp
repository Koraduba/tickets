<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newEvent</title></head>
<body>
NEW EVENT
</b>
${errorNewEventMessage}
<form action="${pageContext.request.contextPath}/mainservlet" id="form1" method="post" name="form1">
    <input type="hidden" name="command"/>
    <input type="hidden" name="mode" value="new">
  <div class="form-group">
    <label for="name">Name</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name" value="${name}">
      <font color="red">${errorEventNameMessage}</font>
    </div>
  </div>
  <div class="form-group">
    <label for="date">Date</label>
    <div class="col-sm-10">
      <input type="date" class="form-control" id="date" name="date" value="${date}">
      <font color="red">${errorEventDateMessage}</font>
  </div>
  <div class="form-group">
    <label for="time">Time</label>
    <div class="col-sm-10">
      <input type="time" class="form-control" id="time" name="time" value="${time}">
      <font color="red">${errorEventTimeMessage}</font>
    </div>
  </div>
    <div class="form-group">
      <label for="description">Description</label>
      <div class="col-sm-10">
        <textarea class="form-control" id="description" name="description" rows="3" >${description}</textarea>
        <font color="red">${errorEventDescriptionMessage}</font>
      </div>
  </div>
    <div class="form-group">
      <label for="price-standard">Standard ticket price</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="price-standard" name="price_STANDARD" value="${price_standard}">
          <font color="red">${errorStandardTicketPriceMessage}</font>
    </div>
      <div class="form-group">
        <label for="price-vip">VIP ticket price</label>
        <div class="col-sm-10">
          <input type="number" class="form-control" id="price-vip" name="price_VIP" value="${price_vip}">
          <font color="red">${errorVipTicketPriceMessage}</font>
      </div>
    <div class="form-group">
      <label for="venue">Venue</label>
      <select id="venue" class="form-control" name="event_venue">
      <c:forEach items="${venues}" var="venue">
         <option>${venue.name}</option>
      </c:forEach>
      </select>
      <input type="submit" value="Add venue" onclick="setCommand('new_venue')">
    </div>
        <div class="form-group">
        <c:if test="${event_path!=null}">
          <img src="${event_path}" class="img-fluid" alt="Responsive image">
          <br/>
        </c:if>
        <c:if test="${event_path==null}">
          <input type="submit" value="Add image" onclick="setCommand('upload_page')">
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