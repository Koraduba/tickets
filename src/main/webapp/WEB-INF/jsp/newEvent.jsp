<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="property.pagecontent" var="rb" />
<jsp:include page="header.jsp"/>
<html>
<head><title>newEvent</title></head>
<body>
<fmt:message key="label.NEW_EVENT" bundle="${rb}"/>
</b>
${errorNewEventMessage}
<form action="${pageContext.request.contextPath}/mainservlet" id="form1" method="post" name="form1">
    <input type="hidden" name="command"/>
    <input type="hidden" name="mode" value="new">
  <div class="form-group">
    <label for="name"><fmt:message key="label.event_name" bundle="${rb}" /></label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name" value="${name}">
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
        <textarea class="form-control" id="description" name="description" rows="2" >${description}</textarea>
        <font color="red">${errorEventDescriptionMessage}</font>
      </div>
  </div>
      <div class="form-group">
        <label for="venue"><fmt:message key="label.venue" bundle="${rb}" /></label>
        <select id="venue" class="form-control" name="event_venue">
        <c:forEach items="${venues}" var="venue">
           <option>${venue.name}</option>
        </c:forEach>
        </select>
        <fmt:message key="label.add_venue" bundle="${rb}" var="localizedAddVenue"  />
        <input type="submit" value="${localizedAddVenue}" onclick="setCommand('new_venue_page')">
      </div>
    <div class="form-group">
      <label for="price-standard"><fmt:message key="label.standard_ticket_price" bundle="${rb}" /></label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="price-standard" name="price_STANDARD" value="${price_standard}">
          <font color="red">${errorStandardTicketPriceMessage}</font>
    </div>
      <div class="form-group">
        <label for="price-vip"><fmt:message key="label.vip_ticket_price" bundle="${rb}" /></label>
        <div class="col-sm-10">
          <input type="number" class="form-control" id="price-vip" name="price_VIP" value="${price_vip}">
          <font color="red">${errorVipTicketPriceMessage}</font>
      </div>
        <div class="form-group">
            <label for="image"><fmt:message key="label.image" bundle="${rb}" /></label>
        <c:if test="${event_path!=null}">
          <img src="${event_path}" class="img-fluid" alt="Responsive image">
          <br/>
        </c:if>
        <c:if test="${event_path==null}">
        <fmt:message key="label.add_image" bundle="${rb}" var="localizedAddImage" />
          <input type="submit" value="${localizedAddImage}" name="image" id="image" onclick="setCommand('upload_page')">
          <br/>
        </c:if>
    </div>
    <fmt:message key="label.save_event" bundle="${rb}" var="localizedSaveEvent"/>
          <input type="submit" value="${localizedSaveEvent}" onclick="setCommand('new_event')">
  </form>

  <script>
  function setCommand (commandType){
  document.forms['form1'].elements.command.value=commandType;
  }
  </script>
<jsp:include page="footer.jsp"/>