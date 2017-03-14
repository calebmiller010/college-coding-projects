<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
    <head>
        <style>.error { color: red; } .success { color: green; }</style>
    </head>
    <style>
  	  body {
 		 background: #efefef;
 		 padding: 10px;
 		 font-family: "Tahoma", Geneva, sans-serif;
 		 text-align: center;
		}
		/*=== 2. Anchor Link ===*/
		a {
  			color: #aaaaaa;
 			transition: all ease-in-out 200ms;
		}
		a:hover {
 			color: #333333;
  			text-decoration: none;
		}
		.logo {
  			padding: 15px 0;
  			font-size: 25px;
  			color: #aaaaaa;
  			font-weight: bold;
		}
		.login-form-1 {
  max-width: 300px;
  border-radius: 5px;
  display: inline-block;
}
.login-form-1 .form-control {
  border: 0;
  box-shadow: 0 0 0;
  border-radius: 0;
  background: transparent;
  color: #555555;
  padding: 7px 0;
  font-weight: bold;
  height:auto;
}
.login-form-1 .form-control::-webkit-input-placeholder {
  color: #999999;
}
.login-form-1 .form-control:-moz-placeholder,
.login-form-1 .form-control::-moz-placeholder,
.login-form-1 .form-control:-ms-input-placeholder {
  color: #999999;
}
.login-form-1 .form-group {
  margin-bottom: 0;
  border-bottom: 2px solid #efefef;
  padding-right: 20px;
  position: relative;
}
.login-form-1 .form-group:last-child {
  border-bottom: 0;
}
.login-group {
  background: #ffffff;
  color: #999999;
  border-radius: 8px;
  padding: 10px 20px;
}
.login-group-checkbox {
  padding: 5px 0;
}

.login-form-1 .login-button {
  position: absolute;
  right: -25px;
  top: 50%;
  background: #ffffff;
  color: #999999;
  padding: 11px 0;
  width: 50px;
  height: 50px;
  margin-top: -25px;
  border: 5px solid #efefef;
  border-radius: 50%;
  transition: all ease-in-out 500ms;
}
.login-form-1 .login-button:hover {
  color: #555555;
  transform: rotate(450deg);
}

		
    </style>
    <body >
	<div class="logo">login</div>
	
        <form class="login-button" action="budget" method="post">
        	<div class="login-form-1">
            
            
            <p>
                <label for="username">Username:</label>
                <input id="username" name="username" value="${tryusername}">
            </p>
            <p>
                <label for="password">Password:</label>
                <input id="password" name="password" type="password">
            </p>
            <span class="error">${messages.username}</span>
			<span class="error">${messages.password}</span>
			<p>
                <input type="submit" value="Login">
            </p>
            </div>
        </form>
        
        <form action="newuser" method="get">
        	<input type="submit" value="New User">
        </form>
        </div>
    </body>
</html>
