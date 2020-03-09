<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<html>
<head><title>newEvent</title></head>
<body>
<fmt:message key="label.EDIT_EVENT" bundle="${rb}" />

<form action="${pageContext.request.contextPath}/mainservlet" id="form1" method="post" name="form1">
    <input type="hidden" name="command" />
    <input type="hidden" name="mode" value="edit">
  <div class="form-group">
    <label for="name"><fmt:message key="label.event_name" bundle="${rb}" /></label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name" value="${name}" maxlength="21">
            <font color="red">${errorEventNameMessage}</font>
    </div>
  </div>
  <div class="form-group">
    <label for="date"><fmt:message key="label.date" bundle="${rb}" /></label>
    <div class="col-sm-10">
      <input type="date" class="form-control" id="date" name="date" value="${date}">
            <font color="red">${errorEventDateMessage}</font>
  </div>
  <div class="form-group">
    <label for="time"><fmt:message key="label.time" bundle="${rb}" /></label>
    <div class="col-sm-10">
      <input type="time" class="form-control" id="time" name="time" value="${time}">
      <font color="red">${errorEventTimeMessage}</font>
    </div>
  </div>
    <div class="form-group">
      <label for="description"><fmt:message key="label.description" bundle="${rb}" /></label>
      <div class="col-sm-10">
        <textarea class="form-control" id="description" name="description" rows="2" maxlength="200">${description}</textarea>
              <font color="red">${errorEventDescriptionMessage}</font>
      </div>
  </div>
        <div class="form-group">
            <label for="venue"><fmt:message key="label.venue" bundle="${rb}" /></label>
            <select id="venue" class="form-control" name="event_venue">
               <option selected>${event.venue.name}</option>
            <c:forEach items="${venues}" var="venue">
               <option>${venue.name}</option>
            </c:forEach>
            </select>
            <fmt:message key="label.add_venue" bundle="${rb}" var="localizedAddVenue"/>
                     <input type="submit" value="${localizedAddVenue}" onclick="setCommand('new_venue_page')">
      </div>
  <c:forEach items="${tickets}" var="ticket">
    <div class="form-group">
      <label for="${ticket.ticketId}">${ticket.category} <fmt:message key="label.price" bundle="${rb}" /></label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="${ticket.ticketId}" name="price_${ticket.category}" value="${ticket.price}" min="1" max="9999">
          <font color="red">${errorStandardTicketPriceMessage}</font>
        </div>
    </div>
   </c:forEach>


        <div class="form-group">
        <c:if test="${event_path!=null}">
          <img src="${event_path}" class="img-fluid" alt="Responsive image">
          <br/>
        </c:if>
                    <fmt:message key="label.change_image" bundle="${rb}" var="localizedChangeImage"/>
          <input type="submit" value="${localizedChangeImage}" onclick="setCommand('upload_page')">
          <br/>
    </div>
                <fmt:message key="label.save_changes" bundle="${rb}" var="localizedSaveChanges"/>
          <input type="submit" value="${localizedSaveChanges}" onclick="setCommand('edit_event')">
  </form>

  <script>
  function setCommand (commandType){
  document.forms['form1'].elements.command.value=commandType;
  }
  </script>
<jsp:include page="footer.jsp"/>