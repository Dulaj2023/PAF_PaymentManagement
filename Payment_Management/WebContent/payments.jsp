<%@ page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<meta charset="ISO-8859-1">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payments.js"></script>
<meta name="viewpoint" content="width=device-width, initial-scale=1">
<style>

input[type=text] {
  width: 100%;
  padding: 7px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 5px;
  margin-bottom: 10px;
  resize: vertical;
}


h1{
	font-family: Arial, Helvetica, sans-serif;
	font-size: 35px;
	text-decoration : underline;
	font-weight : bold;
	color : #fff;
}

.container {
  border-radius: 5px;
  padding: 30px;
}
.background{
	background : #1407a6;
	Border-radius : 15px;
	padding : 25px;
}
form{
	font-size : 18px;
	color:#fff;
}

table{
	color:#fff;
	border: #fff;
}
table th{
	padding : 5px;
}
table td{
	padding : 5px;
}

</style>
</head>
<body>

<div class="container">
 	<div class="row">
 			<div class="col-md-12 background"> 
 			
 			<h1>Payment Details</h1>
 			
			 	<form id='formPayment' name='formPayment' method='post' action='payments.jsp'>
			
					Payment Date: <input id='date' name='date' placeholder='Please insert date' type='text' class='form-control form-control-sm'><br>
					Payment Amount: <input id='payment_amount' name='payment_amount' placeholder='Please enter numerical value' type='text' class='form-control form-control-sm'><br> 
					Payment Description: <input id='payment_description' name='payment_description' placeholder='please enter some description' type='text' class='form-control form-control-sm'><br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class='btn btn-primary'>
					<input type='hidden' id='hidPaymentIDSave' name='hidPaymentIDSave' value=''>
						
				</form>
				
				<br><br>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
	
				<br>
				<div id="divPaymentsGrid">
				<%
					Payment paymentObj = new Payment();
					out.print(paymentObj.readPayment());
				%>
				</div>
		 </div>
	</div>

</div>

</body>
</html>