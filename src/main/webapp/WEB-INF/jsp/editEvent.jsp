<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newEvent</title></head>
<body>
EDIT EVENT

<form action="${pageContext.request.contextPath}/mainservlet" id="form1" method="post" name="form1">
    <input type="hidden" name="command" />
    <input type="hidden" name="mode" value="edit">
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
        <textarea class="form-control" id="description" name="description" rows="3">${description}</textarea>
              <font color="red">${errorEventDescriptionMessage}</font>
      </div>
  </div>
  <c:forEach items="${tickets}" var="ticket">
    <div class="form-group">
      <label for="${ticket.ticketId}">${ticket.category} ticket price</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="${ticket.ticketId}" name="price_${ticket.category}" value="${ticket.price}">
          <font color="red">${errorStandardTicketPriceMessage}</font>
        </div>
    </div>
   </c:forEach>
      <div class="form-group">
          <label for="venue">Venue</label>
          <select id="venue" class="form-control" name="event_venue">
             <option selected>${event.venue.name}</option>
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
          <input type="submit" value="Change image" onclick="setCommand('upload_page')">
          <br/>
    </div>
          <input type="submit" value="Make changes" onclick="setCommand('edit_event')">
  </form>

  <script>
  function setCommand (commandType){
  document.forms['form1'].elements.command.value=commandType;
  }
  </script>
<jsp:include page="footer.jsp"/>