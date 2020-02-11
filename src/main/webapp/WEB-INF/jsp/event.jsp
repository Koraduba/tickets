<h3>File Upload:</h3>
Select a image to upload: <br />
<form action = "uploadservlet" method = "post"
 enctype = "multipart/form-data">
 <input type = "file" name = "file" size = "50" />
<br />
<input type = "submit" value = "Upload File" /></form>
<%--
              <form action = "mainservlet" method = "post">
               <input type="hidden" name="command" value="new_user" />
                 <br />
                 <input type = "submit" value = "Register" />
              </form>

              <form action = "emailservlet" method = "get">
                 <input type = "submit" value = "Email" />
              </form>
--%>