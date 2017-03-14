<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add User</title>
    <style>.error { color: red; } .success { color: green; }</style>
</head>
<style>
.nuser{
position: absolute;
top: 50%;
margin-right: -50%;
left: 50%;
transform: translate(-50%, -50%);
border-style: double;
width: 500px;
height: 400px;
//background: #f06d06;
background: #cad3ef;
margin:20px;
box-shadow: 0 0 0 10px hsl(0, 0%, 60%),
			0 0 0 20px hsl(0, 0%, 70%),
			0 0 0 30px hsl(0, 0%, 80%), 
			0 0 0 40px hsl(0, 0%, 90%);
}
</style>
<body bgcolor="black">
<div class="nuser" style="text-align:center;">
<h1>Create New User</h1>
<form action="newuser" method="post">
	 <p>
         <label for="username">New username:</label>
         <input id="username" name="username" value="${username}">
     </p>
     <p>
     	<span class="error">${messages.username}</span> 
     </p>
     <p>
         <label for="password">Password:</label>
         <input id="password" name="password" type="password">
     </p>
     <p>
         <label for="confirmpassword">Confirm password:</label>
         <input id="confirmpassword" name="confirmpassword" type="password">
     </p>
     <p>
     	 <span class="error">${messages.confirmpassword}</span>
         <span class="error">${messages.password}</span>
     </p>
     <p>
         <input type="submit" value="Create User">
     </p>
     <p>
     	<span class="success">${messages.success}</span>
     </p>  
</form>
<a href="${pageContext.request.contextPath}/budget">Back to Login Page</a>
</div>
</body>
</html>