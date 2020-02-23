<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newEvent</title></head>
<body>
EDIT EVENT

<form action="${pageContext.request.contextPath}/mainservlet?command=event_edited" id="form1" method="post">
    <input type="hidden" name="command" value="upload" />
  <div class="form-group">
    <label for="name">Name</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="name" name="name" value="${event.name}">
    </div>
  </div>
  <div class="form-group">
    <label for="date">Date</label>
    <div class="col-sm-10">
      <input type="date" class="form-control" id="date" name="date" value="${event.date}">
  </div>
  <div class="form-group">
    <label for="time">Time</label>
    <div class="col-sm-10">
      <input type="time" class="form-control" id="time" name="time" value="${event.time}">
    </div>
  </div>
    <div class="form-group">
      <label for="description">Description</label>
      <div class="col-sm-10">
        <textarea class="form-control" id="description" name="description" rows="3">${event.description}</textarea>
      </div>
  </div>
  <c:forEach items="${tickets}" var="ticket">
    <div class="form-group">
      <label for="${ticket.ticketId}">${ticket.category} ticket price</label>
      <div class="col-sm-10">
        <input type="number" class="form-control" id="${ticket.ticketId}" name="price-${ticket.category}" value="${ticket.price}">
    </div>
  </c:forEach>
    <div class="form-group">
      <label for="venue">Venue</label>
      <select id="venue" class="form-control" name="venue">
      <c:forEach items="${venues}" var="venue">
         <option>${venue.name}</option>
      </c:forEach>
      </select>
      <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=new_venue">Add venue</a>
    </div>
        <div class="form-group">
        <img src="${event.image}" class="img-fluid" alt="Responsive image">
      <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=upload">Change picture</a>
        </div>
              <button type="submit">SUBMIT</button>
  </form>
<jsp:include page="footer.jsp"/>