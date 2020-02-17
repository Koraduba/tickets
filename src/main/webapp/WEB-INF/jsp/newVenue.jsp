<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="header.jsp"/>
<html>
<head><title>newVenue</title></head>
<body>
    <form action="${pageContext.request.contextPath}/mainservlet?command=add_venue" id="form1" method="post">
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
    </form>
    <form action = "uploadservlet" id="form2" method = "post" enctype = "multipart/form-data">
            <input type = "file" name = "file" size = "50" />
        <br />
    </form>
  <div class="form-group">
  <input type="hidden" name="command" value="layout_upload" />
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