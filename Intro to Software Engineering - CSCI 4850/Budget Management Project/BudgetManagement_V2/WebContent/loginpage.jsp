<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="LogInServlet" method="GET">
	   <input type="hidden" name="action" value="retrieve"/>
  

  <div>
    <label><b>Username</b></label>
    <input type="text" placeholder="Enter Username" name="userID" required> <br><br>

    <label><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="password" required><br>

    <button type="submit">Login  </button>
    <input type="checkbox" checked="checked"> Remember me <br><br>
  </div>

  <div style="background-color:#f1f1f1">
    <button type="button">Cancel</button>
    <span>Forgot <a href="#">password?</a></span>
  </div>
</form>
</body>
</html>