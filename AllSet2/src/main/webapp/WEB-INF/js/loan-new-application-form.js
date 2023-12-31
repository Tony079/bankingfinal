function Next() {
	document.getElementById("second-form").classList.add("hidden");
	document.getElementById("first-form").classList.remove("hidden");
}

function submit() {
	var customerId = document.getElementById("apcust_id").value;
	var applicationDate = document.getElementById("apdate").value;
	var loanTypeId = document.getElementById("lnty_id").value;
	var loanAmount = document.getElementById("amount").value;
	var interest = document.getElementById("intrest").value;
	var emiLimitFrom = document.getElementById("emilimitfrom").value;
	var emiLimitTo = document.getElementById("emilimitto").value;
	var tenureRequested = document.getElementById("tenure_requested").value;
	var nominee = document.getElementById("nominee").value;
	var createdBy = document.getElementById("createdby").value;
	var createdDate = document.getElementById("createddate").value;
	var processedBy = document.getElementById("processedby").value;
	var processDate = document.getElementById("processdate").value;
	var processedStatus = document.getElementById("processedstatus").value;
	var lastUpdatedDate = document.getElementById("luudate").value;
	var lastUpdatedUser = document.getElementById("luuser").value;
	var status = document.getElementById("status").value;
	var remarks = document.getElementById("remarks").value;
	var attachment = document.getElementById("attachment").value;

	$.ajax({
		url: 'newLoanApplication',
		type: 'post',
		data: {
			CustId: customerId,
			ApplicationDate: applicationDate,
			LoanTypeId: loanTypeId,
			Amount: loanAmount,
			Intrest: interest,
			EmiLimitFrom: emiLimitFrom,
			EmiLimitTo: emiLimitTo,
			TenureRequested: tenureRequested,
			nominee: nominee,
			CreatedBy: createdBy,
			CreatedDate: createdDate,
			ProcessedBy: processedBy,
			ProcessDate: processDate,
			ProcessedStatus: processedStatus,
			LastUpdatedDate: lastUpdatedDate,
			LastUpdatedUser: lastUpdatedUser,
			status: status,
			remarks: remarks,
			Attachment: attachment,

		},
		success: function(resultText) {
			
					alert(' form submitted successfully');
					  document.getElementById("apcust_id").value = "";
					  document.getElementById("apdate").value = "";
					  document.getElementById("lnty_id").value = "";
					  document.getElementById("amount").value = "";
					  document.getElementById("intrest").value = "";
					  document.getElementById("emilimitfrom").value = "";
					  document.getElementById("emilimitto").value = "";
					  document.getElementById("tenure_requested").value = "";
					  document.getElementById("nominee").value = "";
					  document.getElementById("createdby").value = "";
					  document.getElementById("createddate").value = "";
					  document.getElementById("processedby").value = "";
					  document.getElementById("processdate").value = "";
					  document.getElementById("processedstatus").value = "";
					  document.getElementById("luudate").value = "";
					  document.getElementById("luuser").value = "";
					  document.getElementById("status").value = "";
					  document.getElementById("remarks").value = "";
					  document.getElementById("attachment").value = "";
		},
		error: function(jqXHR, exception) {
			alert('Error occurred!');
		}
	});
}

function showFirstForm() {
	document.getElementById("second-form").classList.add("hidden");
	document.getElementById("first-form").classList.remove("hidden");
}

function submit2() {
	document.getElementById('apcust_id').value = document.getElementById('cust_id').value;
	Next();
	var custId = document.getElementById("cust_id").value;
	var custTitle = document.getElementById("cust_title").value;
	var custType = document.getElementById("cust_type").value;
	var custCAddress = document.getElementById("cust_caddress").value;
	var custCAPincode = document.getElementById("cust_capincode").value;
	var custDOB = document.getElementById("cust_dob").value;
	var custMobile1 = document.getElementById("cust_mobile1").value;
	var custMobile2 = document.getElementById("cust_mobile2").value;
	var custRPhone = document.getElementById("cust_rphone").value;
	var custRAddress = document.getElementById("cust_raddress").value;
    var LoanCount = document.getElementById("otherloans").value;
    var Annualincom = document.getElementById("annualincome").value;
	
     console.log(Annualincom);
     console.log(LoanCount);
	




	// Make AJAX call to save the form data
	$.ajax({
		url: "customer_data_trail_save",
		type: "POST",
		data: {
			Id: custId,
			custTitle: custTitle,
			cust_type: custType,
			cust_caddress: custCAddress,
			cust_capincode: custCAPincode,
			cust_dob: custDOB,
			cust_mobile1: custMobile1,
			cust_mobile2: custMobile2,
			cust_rphone: custRPhone,
			cust_raddress: custRAddress,
			AnnualInccome:Annualincom,
			Otherloans:LoanCount
		},
		success: function(response) {
			// Handle success response
			
					alert(' form submitted successfully');
		loaction.reload();
		},
		error: function(xhr, status, error) {
			// Handle error response
			alert("Error occurred while saving form data");
		}
	});
}

function submitBothForms() {
	submit();
	submit2();
}