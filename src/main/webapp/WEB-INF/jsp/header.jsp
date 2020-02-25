<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Hello, world!</title>
<script type="text/javascript"
    src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
    src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>

  </head>
  <body>
      <ctg:info-time/>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">TICKETS</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=logout">Logout</a>
        </li>
         <li class="nav-item">
           <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=catalog&currentPage=1">Events</a>
         </li>
         <c:if test="${role=='USER'}">
         <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=cart">Cart</a>
        </li>
        <li class="nav-item">
                 <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=orders">Orders</a>
        </li>
        </c:if>
        <c:if test="${role=='USER'||role=='HOST'}">
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=profile">Profile</a>
        </li>
        </c:if>
        <c:if test="${role=='ADMINISTRATOR'}">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=users&currentPage=1">Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=new_user">New user</a>
        </li>
         <li class="nav-item">
           <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=statistic">Statistic</a>
         </li>
        </c:if>
          <c:if test="${role=='HOST'}">
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=new_event_page">New event</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/mainservlet?command=my_events&currentPage=1">My events</a>
          </li>
        </c:if>
      </ul>
    </div>
  </nav>