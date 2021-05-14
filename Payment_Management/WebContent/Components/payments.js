$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//Save--------------------------------
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onPaymentSaveComplete(response.responseText, status);
		}
		});
	
	
});

function onPaymentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}

//remove
$(document).on("click", ".btnRemove", function(event)
		{
		$.ajax(
		{
		url : "PaymentsAPI",
		type : "DELETE",
		data : "payment_id=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status)
		{
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});


function onPaymentDeleteComplete(response, status)
{
	if (status == "success")
	{
	var resultSet = JSON.parse(response);
	if (resultSet.status.trim() == "success")
	{
		$("#alertSuccess").text("Successfully deleted.");
		$("#alertSuccess").show();
		$("#divPaymentsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error")
	{
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
	}
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidPaymentIDSave").val($(this).data("paymentid"));
	$("#date").val($(this).closest("tr").find('td:eq(0)').text());
	$("#payment_amount").val($(this).closest("tr").find('td:eq(1)').text());
	$("#payment_description").val($(this).closest("tr").find('td:eq(2)').text());

});


//CLIENT-MODEL================================================================
function validatePaymentForm()
{
	// CODE
	if ($("#date").val().trim() == "")
	{
		return "Insert Payment date.";
	}
	
	// PRICE-------------------------------
	if ($("#payment_amount").val().trim() == "")
	{
		return "Insert Payment Amount.";
	}
	
	// is numerical value
	var tmpPrice = $("#payment_amount").val().trim();
	if (!$.isNumeric(tmpPrice))
	{
		return "Insert a numerical value for Payment Amount.";
	}
	
	// convert to decimal price
	$("#payment_amount").val(parseFloat(tmpPrice).toFixed(2));
	
	// DESCRIPTION------------------------
	if ($("#payment_description").val().trim() == "")
	{
		return "Insert Payment Description.";
	}
	
	return true;
}