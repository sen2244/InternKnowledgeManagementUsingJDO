$(document).ready(function(){
	$("#login").click(function(){
		var mail = $("#mail").val().trim();
		var empId = $("#empId").val().trim();
		if(mail == '' || empId == '')
		{
			$("#spnMsg").html("*Please provide all inputs!");
		}
		else
		{
			if (!checkMobile(empId)) 
				{
					$('#spnMsg').html("*Please provide proper Employee Id!");
					$("#empId").focus();
				}
			else if (!testMail(mail)) 
				{
					$('#spnMsg').html("*Please provide correct Mail address!");
					$("#mail").focus();
				}
			else
			{
				$("#mail").val('');
				$("#empId").val('');
				$("#mail").prop('disabled',true);
				$("#empId").prop('disabled',true);
				$("#login").prop('disabled',true);
				$.ajax({
					url: '/loginservlet',
					type: 'POST',
					data: 'mail='+mail+'&empId='+empId,
					success: function(data)
					{
						if(data == 'Success')
						{
							$('#spnMsg').html('login Successfull!');
							window.location.href = '/AddKnowledgePage/AddKnowledgePage.html';
						}
						else
						{
							$('#spnMsg').html('login Failed! Please provide proper credentials!');
						}
					},
					error: function(jqxhr,status,errorThrown)
					{
						console.log('Error Contacting Server! '+jqxhr.status+' '+errorThrown+'!');
						$("#spnMsg").html('Error Contacting Server! '+jqxhr.status+' '+errorThrown+'!');
					},
					complete: function(data)
					{
						console.log('complete');
						$("#mail").prop('disabled',false);
						$("#empId").prop('disabled',false);
						$("#login").prop('disabled',false);
						$("#mail").val('');
						$("#empId").val('');
					}
				});
			}
		}
	});
});
function checkMobile(num)
{
	var phoneno = /^\d{6}$/;
	console.log(phoneno+'; '+num);
	if(!(num.match(phoneno))) return false;
	return true;
}
function testMail(email)
{
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (filter.test(email)) return true;
	else return false;
}