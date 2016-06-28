$(document).ready(function(){
	$('#save').click(function(){
		var nam = $('#name').val().trim();
		var mai = $('#mail').val().trim();
		var pho = $('#phone').val().trim();
		var qua = $('#qual').val().trim();
		var empId = $('#empId').val().trim();
		if(qua == 'others')
		{
			qua = $('#otherBox').val().trim();
		}
		console.log(qua);
		var typ = $('#type').val().trim();
		var mon = $('#month').val().trim();
		if(nam == '' || mai == '' | pho == '' || qua == '' || typ == '' || mon == '' || empId == '')
		{
			$("#spnMsg").html("*Please provide all inputs!");
		}
		else
		{
			if (!testMail(mai)) $('#spnMsg').html("*Please provide correct Mail address!");
			else if (!checkMobile(pho)) $('#spnMsg').html("*Please provide correct Phone Number!");
			else if (empId.length != 6) $('#spnMsg').html("*Please provide correct Employee ID!");
			else
				{
				var urlString = 'name='+ nam +'&mail='+ mai +'&phone='+pho+'&qual='+qua+'&type='+typ+'&month='+mon+'&empId='+empId;
				var newUrlString = urlString.replace(/ /g,'+');
				console.log(newUrlString);
				$("#name").prop('disabled',true);
				$("#mail").prop('disabled',true);
				$("#phone").prop('disabled',true);
				$("#qual").prop('disabled',true);
				$("#others").prop('disabled',true);
				$("#type").prop('disabled',true);
				$("#month").prop('disabled',true);
				$("#save").prop('disabled',true);
				$("#empId").prop('disabled',true);
					$('#spnMsg').html("");
					$('#spnMsg').html("Registering!!!");
					$.ajax({
						url:"/registerservlet",
						type:"post",
						data:newUrlString,
						success: function(data)
						{
							console.log(data);
							if(data == 'Error') $("#spnMsg").html('An Error Ocurred!');
							else if(data == 'Success'){
								$("#spnMsg").html('');
								window.location.href = "/AddKnowledgePage/AddKnowledgePage.html";
							}
							else if(data == 'Exists')
							{
								$("#spnMsg").html('Mail address already Exists! Try login!');

							}
						},
						error: function(jqxhr,status,errorThrown)
						{
							console.log('Error Contacting Server! '+jqxhr.status+' '+errorThrown+'!');
							$("#spnMsg").html('Error Contacting Server! '+jqxhr.status+' '+errorThrown+'!');
						},
						complete: function()
						{
							$("#name").prop('disabled',false);
							$("#mail").prop('disabled',false);
							$("#phone").prop('disabled',false);
							$("#qual").prop('disabled',false);
							$("#others").prop('disabled',false);
							$("#type").prop('disabled',false);
							$("#month").prop('disabled',false);
							$("#empId").prop('disabled',false);
							$("#name").val('');
							$("#mail").val('');
							$("#phone").val('');
							$("#qual").val('');
							$("#others").val('');
							$("#type").val('');
							$("#month").val('');
							$("#empId").val('');
							$("#save").prop('disabled',false);
						}
					});
				}
		}
	});
});
function testMail(email)
{
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (filter.test(email)) return true;
	else return false;
}
function checkMobile(num)
{
	var phoneno = /^\d{1,12}$/; 
	if(!(num.match(phoneno))) return false;
	return true;
}
$(document).ready(function(){
	$("#qual").change(function(){
		var qual = $("#qual").val();
		if(qual == 'others')
		{
			$('#qual').addClass('qual');
			$("#otherBox").prop('style','display:block;');
			$("#otherBox").val('');
		}
		else
		{
			$("#otherBox").prop('style','display:none;');
			$('#qual').removeClass('qual');
		}
	});
});