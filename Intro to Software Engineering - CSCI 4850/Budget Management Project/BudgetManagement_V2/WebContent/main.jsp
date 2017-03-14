<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget Manager</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
	/*tab stuff*/
	body {font-family: "Tahoma", Geneva, sans-serif;}
	ul.tab {
    	list-style-type: none;
    	margin: 0;
   	 	padding: 0;
    	overflow: hidden;
    	border: 1px solid #ccc;
    	background-color: #cad3ef;
	}
	/* Float the list items side by side */
	ul.tab li {float: left;}

	/* Style the links inside the list items */
	ul.tab li a {
    	display: inline-block;
    	color: black;
    	text-align: center;
    	padding: 14px 16px;
    	text-decoration: none;
    	transition: 0.3s;
    	font-size: 17px;
	}

	/* Change background color of links on hover */
	ul.tab li a:hover {
    	background-color: #ddd;
	}
	/* Create an active/current tablink class */
	ul.tab li a:focus, .active {
    	background-color: #ccc;
	}

	/* Style the tab content */
	.tabcontent {
    	display: none;
    	padding: 6px 12px;
    	border: 1px solid #ccc;
    	border-top: none;
	}
	/*Table stuff*/
	table{
		border-collapse: collapse;
		width 100%;
	}
	td, th{
		border: 1px solid #dddddd;
		text-align: left;
		padding: 8px;
	}
	tr:nth-child(even){
		background-color: #dddddd;
	}
	tr.income{
		background-color: #98fb98;
	}
	tr.spending{
		background-color: #ff9999;
	}
	tr.tablehead {
		font-weight:bold;
	}
</style>
<body>
<c:set value="${user}" var="u"/>
<c:set value="${totalspending * -1}" var="totalspending"/>

<ul class="tab">
  <li><a href="#" class="tablinks" onclick="openTab(event, 'Overview')" id="Overviewtab">Overview</a></li>
  <li><a href="#" class="tablinks" onclick="openTab(event, 'Transactions')" id="Transactiontab">Transactions</a></li>
  <li><a href="#" class="tablinks" onclick="openTab(event, 'Budget')" id="Budgettab">Budget</a></li>
  <li style="float:right;width:20px;">&nbsp;</li>
  <li style="float:right;margin-top:2%;"><form action="logout" method="get"><button type="submit">Logout</button></form>
  <li style="float:right;margin-top:2%;">Hello <b><c:out value="${u.username}"/></b>!!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>

  
</ul>

<div id="Overview" class="tabcontent">
Your current standing: <fmt:formatNumber value="${totalincome - totalspending}" type="currency" pattern="$#,##0.00;-$#,##0.00"/>
<br/>
<br/>
<br/>
  <h4>Upcoming Bills</h4>
  <table>
	<tr class="tablehead">
		<td style="width:22%;">Description </td>
		<td style="width:10%;">Amount </td>
		<td style="width:18%;">Date Due </td>
		<td style="width:18%">Category </td>
		<td style="width:22%;">Additional Comments </td>
		<td style="width:10%;">Pay Bill</td>
	</tr>
	<fmt:setLocale value="en_US"/>
    <c:forEach items="${upcomingpayments}" var="up">
		<tr class="income">
	        <td><c:out value="${up.name}" /></td>
	        <td><fmt:formatNumber value="${up.amount}" type="currency"/></td>
	        <td><c:out value="${month}"/> <c:out value="${up.daydue}"/></td>
			<td><c:out value="${up.category}" /></td>
			<td><c:out value="${up.comment}" /></td>
			<td><form action="PayBill" method="post"><input name="paymentid" type="image" value="${up.paymentid}" id="submit" src="${pageContext.request.contextPath}/images/paymybillicon.png" width="25" height="25" onclick="return paybill()"/></form></td>
		</tr>
    </c:forEach>
  </table>
  <br/>
  <br/>
  <br/>
  <h4>Overdue Bills</h4>
  <table>
	<tr class="tablehead">
		<td style="width:22%;">Description </td>
		<td style="width:10%;">Amount </td>
		<td style="width:18%;">Date Due </td>
		<td style="width:18%">Category </td>
		<td style="width:22%;">Additional Comments </td>
		<td style="width:10%;">Pay Bill</td>
	</tr>
	<fmt:setLocale value="en_US"/>
    <c:forEach items="${overduepayments}" var="op">
		<tr class="spending">
	        <td><c:out value="${op.name}" /></td>
	        <td><fmt:formatNumber value="${op.amount}" type="currency"/></td>
	        <td><c:out value="${month}"/> <c:out value="${op.daydue}"/></td>
			<td><c:out value="${op.category}" /></td>
			<td><c:out value="${op.comment}" /></td>
			<td><form action="PayBill" method="post"><input name="paymentid" type="image" value="${op.paymentid}" id="submit" src="${pageContext.request.contextPath}/images/paymybillicon.png" width="25" height="25" onclick="return paybill()"/></form></td>
		</tr>
    </c:forEach>
  </table>
  
  <br/>
  <br/>
  <br/>
  
  <h4>Paid</h4>
  <table>
	<tr class="tablehead">
		<td style="width:25%;">Description </td>
		<td style="width:10%;">Amount </td>
		<td style="width:20%;">Date Paid </td>
		<td style="width:20%">Category </td>
		<td style="width:25%;">Additional Comments </td>
	</tr>
	<fmt:setLocale value="en_US"/>
    <c:forEach items="${paid}" var="pd">
		<tr>
	        <td><c:out value="${pd.name}" /></td>
	        <td><fmt:formatNumber value="${pd.amount}" type="currency"/></td>
			<td><c:out value="${month}"/> <c:out value="${pd.datepaid}" /></td>
			<td><c:out value="${pd.category}" /></td>
			<td><c:out value="${pd.comment}" /></td>
		</tr>
    </c:forEach>
  </table>
  
  </div>

<div id="Transactions" class="tabcontent">

<div class="container">
  <!-- Trigger the modal with a button -->
 
  <!-- Modal -->
  <div class="modal fade" id="ModalIncome">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add New Income</h4>
        </div>
        <form action="AddTransaction" method="post">
        <input type="hidden" name="tabfield" value="2">
	        <div class="modal-body">
					 <p>
				         <label for="description">Description:</label>
				         <input id="description" name="description" required="required">
				     </p>
				     <p>
				         <label for="amount">Amount:</label>
				         <input id="amount" name="income" pattern="[0-9.]+" title="numbers only!!" required="required">
				     </p>
				   
				     <p>
				     	<label for="recurring">Yearly Salary?:</label>
				     	<select id="recurring" name="recurring">
							<option value="0">No</option>
							<option value="1">Yes</option>
						</select>
						<br/>**If the income is entered as a yearly salary, it will show up as the income for each month in the transaction table
				     </p>
				     
				     <p>
				         <label for="category">Category:</label>
						 <select id="category" name="category">
							<option value="Main Income">Main Income</option>
							<option value="Side Job">Side Job</option>
							<option value="Other">Other</option>
						 </select>
				     </p>
				        <p>
				         <label for="comment">Comments:</label>
				         <input id="comment" name="comment">
				     </p>

	        </div>
	        <div class="modal-footer">
	          <input type="submit" class="btn btn-default" value="Add Income" />	        
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
		</form>
      </div>
      
    </div>
  </div>

  <div class="modal fade" id="ModalExpense">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add New Expense</h4>
        </div>
        <form action="AddTransaction" method="post" onsubmit="validateForm()">
        <input type="hidden" name="tabfield" value="2">
	        <div class="modal-body">
					 <p>
				         <label for="description">Description:</label>
				         <input id="description" name="description">
				     </p>
				     <p>
				         <label for="amount">Amount:</label>
				         <input id="amount" name="expense" pattern="[0-9.]+" title="numbers only!!" required>
				     </p>
				    
				     <p>
				     	<label for="recurring2">Recurring?</label>
				     	<select id="recurring2" name="recurring" onchange="switchdateprompt()">
							<option value="0">No</option>
							<option value="1">Yes</option>
						</select>
					 <br/>
					 **A recurring payment will show up every month as an expense
				     </p>
				     
				     <p>
				         <label for="categoryexpense">Category:</label>
						 <select id="categoryexpense" name="category" onchange="addnewcategoryforexpense()">
								<c:forEach items="${categories}" var="category">
									<option value="${category}">${category}</option>
								</c:forEach>
								<option value="addnewcategoryexpense" style="font-weight:bold;">Add Custom Category</option>
						 </select>
				     </p>
				     <div id="newcategoryexpense" style="display:none;">
					     <p>
						     <label for="newcategoryexpenseinput">New Category:</label>
					         <input id="newcategoryexpenseinput" name="newcategoryexpense">
					     </p>
				     </div>
				      <p>
				         <label for="comment">Comments:</label>
				         <input id="comment" name="comment">
				     </p>
				     <div id="due" style="display:none">
					     <p>
					         <label for="duefield">Day of month payment due:</label>
					         <input id="duefield" name="paydue" pattern="(?:\b[1-9]\b)|(?:[12][0-8])|19" title="Between 1-28 please!!" >
					         <br/>
					         **Filling out this field will make put this payment in the "upcoming payments" tab to remind you when payments are due each month
					     </p>
				     </div>

	        </div>
	        <div class="modal-footer">
	          <input type="submit" class="btn btn-default" value="Add Expense" />
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
		</form>
      </div>
      
    </div>
  </div>
  
</div>


<h4>Monthly Incomes and Expenses</h4>
<br/>
Incomes:
<br/><br/>
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ModalIncome">Add New Income</button>
<br/><br/>
<table>
	<tr class="tablehead">
		<td style="width:19%;">Description </td>
		<td style = "width:10%;">Amount </td>
		<td style = "width:19%;">Type of Income </td>
		<td style = "width:18%">Category </td>
		<td style = "width:29%;">Additional Comments </td>
		<td style="width:5%;"></td>
	</tr>
	
<fmt:setLocale value="en_US"/>
    <c:forEach items="${transactions}" var="transaction">
	    <c:if test="${transaction.amount >= 0}"> 
			<tr> <!-- class="income"> --> 
		        <td><c:out value="${transaction.name}" /></td>
		        <td><fmt:formatNumber value="${transaction.amount}" type="currency"/></td>
		        <c:choose>
				    <c:when test="${transaction.recurring}">
				        <td>Yearly Income</td>
				    </c:when>    
				    <c:otherwise>
				        <td>One-Time Income</td>
				    </c:otherwise>
				</c:choose>
				<td><c:out value="${transaction.category}" /></td>
				<td><c:out value="${transaction.comment}" /></td>
				<td><form action="DeleteTransaction" method="post"><input name="removeid" type="image" value="${transaction.transactionid}" id="submit" src="${pageContext.request.contextPath}/images/delete1.ico" width="15" height="15" onclick="return shouldsubmit()"/>
				<input type="hidden" name="tabfield" value="2"></form></td>
			</tr>
		</c:if>
    </c:forEach>
</table>
<c:set value="${totalincome}" var="totalincome"/>
<br/>
Total Income This Month: <fmt:formatNumber value="${totalincome}" type="currency"/> <br>

<br/><br/>
Expenses:<br/>
<br/>
<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ModalExpense">Add New Expense</button>
<br/>
<br/>
<table>
<tr class="tablehead">
	<td style="width:19%;">Description </td>
	<td style = "width:10%;">Amount </td>
	<td style = "width:19%;">One-Time/Monthly? </td>
	<td style = "width:18%">Category </td>
	<td style = "width:29%;">Additional Comments </td>
	<td style="width:5%;"></td>
</tr>
	<c:forEach items="${transactions}" var="transaction">
	    <c:if test="${transaction.amount < 0}"> 
			<tr> <!--  class="spending">  -->
		        <td><c:out value="${transaction.name}" /></td>
		        <td><fmt:formatNumber value="${transaction.amount * -1}" type="currency"/></td>
		        <c:choose>
				    <c:when test="${transaction.recurring}">
				        <td>Monthly Payment</td>
				    </c:when>    
				    <c:otherwise>
				        <td>One-Time Payment</td>
				    </c:otherwise>
				</c:choose>
				<td><c:out value="${transaction.category}" /></td>
		        <td><c:out value="${transaction.comment}" /></td>
				<td><form action="DeleteTransaction" method="post"><input name="removeid" type="image" value="${transaction.transactionid}" id="submit" src="${pageContext.request.contextPath}/images/delete1.ico" width="15" height="15" onclick="return shouldsubmit()"/>
				<input type="hidden" name="tabfield" value="2"></form>
		    </tr>
		</c:if>
    </c:forEach>
</table>
<br/>
Total Spending This Month: <fmt:formatNumber value="${totalspending}" type="currency"/>
<br/>

<br/>
<br/>
<br/>
</div>





<div id="Budget" class="tabcontent">

<br/>
<h3>Budget Breakdown:</h3>

<div class="modal fade" id="ModalCreateBudget">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add New Expense</h4>
        </div>
        <form action="AddUpdateCustomBudget" method="post">
	        <div class="modal-body">
	        		 <p>
	        		 ** Enter the percentages for each category to create your own custom budget - it will use that to suggest your spending amount for that category every month **
	        		 </p>
	        		 <p>
				         <label for="food">Food:</label>
				         <input id="food" name="food" pattern="[0-9][0-9]?" value="${budgetpercentvalues[0]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				      <p>
				         <label for="shelter">Shelter:</label>
				         <input id="shelter" name="shelter" pattern="[0-9][0-9]?" value="${budgetpercentvalues[1]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				      <p>
				         <label for="utilities">Utilities:</label>
				        <input id="utilities" name="utilities" pattern="[0-9][0-9]?" value="${budgetpercentvalues[2]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				      <p>
				         <label for="clothing">Clothing:</label>
				         <input id="clothing" name="clothing" pattern="[0-9][0-9]?" value="${budgetpercentvalues[3]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				      <p>
				         <label for="transportation">Transportation:</label>
				         <input id="transportation" name="transportation" pattern="[0-9][0-9]?" value="${budgetpercentvalues[4]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				      <p>
				         <label for="medical">Medical:</label>
				         <input id="medical" name="medical" pattern="[0-9][0-9]?" value="${budgetpercentvalues[5]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				      <p>
				         <label for="insurance">Insurance:</label>
				         <input id="insurance" name="insurance" pattern="[0-9][0-9]?" value="${budgetpercentvalues[6]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				      <p>
				         <label for="personal">Personal:</label>
				         <input id="personal" name="personal" pattern="[0-9][0-9]?" value="${budgetpercentvalues[7]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				     <p>
				         <label for="education">Education:</label>
				         <input id="education" name="education" pattern="[0-9][0-9]?" value="${budgetpercentvalues[8]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				     <p>
				         <label for="savings">Savings:</label>
				         <input id="savings" name="savings" pattern="[0-9][0-9]?" value="${budgetpercentvalues[9]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required>%
				     </p>
				     <p>
				         <label for="extraspending">Extra Spending:</label>
				         <input id="extraspending" name="extraspending" pattern="[0-9][0-9]?" value="${budgetpercentvalues[10]}" title="Must be integer 0-100"  onchange="updateother()" style="width:30px;" required > %
				     </p>
				     <p>
				         <label for="other">Other:</label>
				         <input id="other" name="other" pattern="[0-9][0-9]?" value="${budgetpercentvalues[11]}" title="Must be integer 0-100" style="width:30px;" required > %
				     </p> 
				     
	        </div>
	        <div class="modal-footer">
	          <input type="submit" class="btn btn-default" value="Add/Update Custom Budget" onclick="return validateBudget();" />
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
		</form>
      </div>
      
    </div>
  </div>


<br/>
<div style="">
	<div style="float:left;margin-right:20px;">
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ModalCreateBudget">Create or Update Custom Budget</button>
	</div>
	<div>
		<form action="DeleteCustomBudget" method="post">
			<button type="submit" class="btn btn-info btn-lg" onclick="return removebudget()">Reset to Default Budget</button>
		</form>
	</div>
</div>

<br/>
<br/>

  <table>
	<tr class="tablehead">
  		<td>Category</td>
  		<td>Budget Recommendation</td>
  		<td>Actual Spending</td>
  		<td>Difference</td>
  		<td>Budget Percentage</td>
  	</tr>
  	
  	<c:forEach items="${categoriesoverview}" var="categoryoverview">
  	 	<c:if test="${categoryoverview.difference >= 0}"> 
  			<c:set value="income" var="cssClass"></c:set>
		</c:if>
		<c:if test="${categoryoverview.difference < 0}"> 
  			<c:set value="spending" var="cssClass"></c:set>
		</c:if>
  		<tr class="${cssClass}">
  			<td><c:out value="${categoryoverview.category}"/></td>
  			<td><fmt:formatNumber value="${categoryoverview.budgetrecommendation}" type="currency"/></td>
  			<td><fmt:formatNumber value="${categoryoverview.actualspending}" type="currency"/></td>
  			<td><fmt:formatNumber value="${categoryoverview.difference}" type="currency" pattern="$#,##0.00;-$#,##0.00"/></td>
  			<td><c:out value="${categoryoverview.budgetpercent}%"/></td>
  		</tr>  	
  	</c:forEach>
  	<tr>
		<td><b>TOTAL:</b></td>
		<td><fmt:formatNumber value="${totalincome}" type="currency"/></td>
		<td><fmt:formatNumber value="${totalspending}" type="currency"/></td>
		<td><fmt:formatNumber value="${totalincome - totalspending}" type="currency" pattern="$#,##0.00;-$#,##0.00"/></td>
		<td>100%</td>
	</tr>
  	
  	 
  </table>
</div>

<script>
function openTab(evt, tabName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
   	
    evt.currentTarget.className += " active";
}

if(${tab}==2) {
	document.getElementById("Transactiontab").click();
}
else if(${tab}==3) {
	document.getElementById("Budgettab").click();
}
else {
	document.getElementById("Overviewtab").click();
}
function shouldsubmit() {
	var r = window.confirm('Are you sure you want to remove this user?');
	if(r==true) {
		return true;	
	}
	return false;
}

function paybill() {
	var r = window.confirm("Click OK when you've paid this bill for the month!");
	if(r==true) {
		return true;	
	}
	return false;
}

function removebudget() {
	var r = window.confirm("Are you sure you want to remove the custom budget, and reset it to the default budget?");
	if(r==true) {
		return true;	
	}
	return false;
}

function switchdateprompt() {
	 if (document.getElementById("recurring2").value == "1"){
	     $("#due").attr("style", "display:block");
	 }
	 else {
	    $("#due").attr("style", "display:none");
	 }
		 
}

function addnewcategoryforexpense() {
	   	var n = document.getElementById("newcategoryexpenseinput");
		if(document.getElementById("categoryexpense").value == "addnewcategoryexpense") {
	        $("#newcategoryexpense").attr("style", "display:block");
		     n.setAttribute("required", "required");

	    }
	    else {
	    	$("#newcategoryexpense").attr("style", "display:none");
	    	n.removeAttribute("required");
	    }
}

function updateother() {
	var sum = getsumofbudgetvaluesexceptother();
	if(sum<100) {$("#other").attr("value", 100-sum);}
	else {$("#other").attr("value", 0);}
}

function validateBudget() {
	var check = 0;
	check = getsumofbudgetvaluesexceptother();
	check += parseInt(document.getElementById("other").value);
	if(check == 100) 
		return true;
	alert("Values must add up to 100!");
	return false;	
}

function getsumofbudgetvaluesexceptother() {
	var sum = 0;
	sum += parseInt(document.getElementById("food").value);
	sum += parseInt(document.getElementById("shelter").value);
	sum += parseInt(document.getElementById("utilities").value);
	sum += parseInt(document.getElementById("clothing").value);
	sum += parseInt(document.getElementById("transportation").value);
	sum += parseInt(document.getElementById("medical").value);
	sum += parseInt(document.getElementById("insurance").value);
	sum += parseInt(document.getElementById("personal").value);
	sum += parseInt(document.getElementById("education").value);
	sum += parseInt(document.getElementById("savings").value);
	sum += parseInt(document.getElementById("extraspending").value);
	return sum;
}



</script>
</body>
</html>