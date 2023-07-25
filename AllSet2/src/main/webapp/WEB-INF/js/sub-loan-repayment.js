
function getDate()
{
	var today = new Date();
	document.getElementById("date").value = today.getFullYear()+'-'+('0'+(today.getMonth()+1)).slice(-2)+'-'+('0'+today.getDate()).slice(-2);
}


function validateLoanWithdrawlForm() 
{
	let x = document.getElementById("date").value;
	let y = document.getElementById("amount").value;
	if (x == "") 
	{
		alert("date must be filled");
	    return false;
	}
	else if (y == "") 
	{
		alert("enter amount");
	    return false;
	}
	else if (y<=0 || y.includes(".")) 
	{
		alert("invalid amount");
	    return false;
	}
	else
	{
		depositMoney(); 
	}
}

