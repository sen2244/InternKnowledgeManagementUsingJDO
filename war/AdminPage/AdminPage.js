$(document).ready(function(){
	$('#load').click(function(){
		var mailAdd = $('#mail').val().trim();
		var topic = $('#topic').val().trim().toUpperCase();
		var subTopic = $('#subTopic').val().trim().toUpperCase();
		var level = $('#level').val().trim();
		var month = $('#month').val().trim();
		if(mailAdd == '' && topic == '' && subTopic == '' && level == '' && month == '')
		{
			$("#load").prop('disabled',true);
			$("#spnMsg").html('Fetching sample data!');
			$.ajax({
				url:'/loadsampledetails',
				type:'get',
				success: function(data){
					$("#spnMsg").html('');
						if(data != "Error" || data != "Empty")
						{
							$("#loadDetails").html("<div class='knowDetails'></div>");
							for(var i = 0; i < data.length; i++)
							{
								$(".knowDetails").append("<div class='eachDiv'><div class='divLeft'>Name: "+data[i].name+"</div><div class='divRight'>Mail: "+data[i].mail+"</div><div class='divLeft'>Topic: "+data[i].topic+"</div><div class='divRight'>SubTopic: "+data[i].subTopic+"</div><div class='divLeft'>level: "+data[i].level+"</div><div class='divRight'>Description: "+data[i].description+"</div></div>");
							}
						}
						else if(data == "Empty")
						{
							$("#spnMsg").html('<b>No Entries Found!</b>');
							$("#loadDetails").html('');
						}
						else
						{
							$("#spnMsg").html('<b>Error occurred!</b>');
							$("#loadDetails").html('');
						}
				},
				error: function(jqxhr,status,thrownError){
	        		console.log('Error Contacting Server! '+jqxhr.status+' '+thrownError+'!');
					$("#spnMsg").html('Error in Server! '+jqxhr.status+' '+thrownError+'!');
					$("#loadDetails").html('');
	        	},
				complete: function(){
					$('#load').prop('disabled',false);
					console.log('complete');
				}
			});
		}
		else if(!testMail(mailAdd) && !(mailAdd == ''))
		{
			$("#spnMsg").html('Please provide proper Mail Address!');
		}
		else if (testMail(mailAdd))
		{
			$('#topic').val('');
			$('#subTopic').val('');
			$('#level').val('');
			$('#month').val('');
			$("#spnMsg").html('');
			$('#mail').val('');
			console.log(mailAdd);
			$('#load').prop('disabled',true);
			$('#topic').prop('disabled',true);
			$('#subTopic').prop('disabled',true);
			$('#level').prop('disabled',true);
			$('#month').prop('disabled',true);
			$("#mail").prop('disabled',true);
			$.ajax({
				url:'/fetchservlet',
				type:'post',
				data: 'mail=' + mailAdd + '&topic=' + topic + '&subTopic=' + subTopic + '&level=' + level,
				success: function(data){
					$("#spnMsg").html('');
					if(data[0].name != null || data[0].name != undefined)
					{
						$("#loadDetails").html("<div class='details'><div class='divLeft'>Name: "+data[0].name+"</div><div class='divRight'>Mail: "+data[0].mail+"</div><div class='divLeft'>Phone: "+data[0].phone+"</div><div class='divRight'>Qualification: "+data[0].qual+"</div><div class='divLeft'>Type: "+data[0].type+"</div><div class='divRight'>Month: "+data[0].month+"</div></div>");
						$("#loadDetails").append("<div class='knowDetails'></div>");
						for(var i = 1; i < data.length; i++)
						{
							$(".knowDetails").append("<div class='eachDiv'><div class='divLeft'>Topic: "+data[i].topic+"</div><div class='divRight'>SubTopic: "+data[i].subtopic+"</div><div class='divLeft'>Level: "+data[i].level+"</div><div class='divRight'>Description: "+data[i].descr+"</div></div>");
						}
					}
					else
					{
						$("#spnMsg").html('<b>No Match found for your Query!</b> Please check the MailAddress!');
						$("#loadDetails").html('');
					}
				},
				error: function(jqxhr,status,thrownError){
	        		console.log('Error Contacting Server! '+jqxhr.status+' '+thrownError+'!');
					$("#spnMsg").html('Error in Server! '+jqxhr.status+' '+thrownError+'!');
					$("#loadDetails").html('');
					$('#load').prop('disabled',false);
					$('#topic').prop('disabled',false);
					$('#subTopic').prop('disabled',false);
					$('#level').prop('disabled',false);
					$('#month').prop('disabled',false);
					$("#mail").prop('disabled',false);
	        	},
				complete: function(){
					$('#topic').prop('disabled',false);
					$('#subTopic').prop('disabled',false);
					$('#level').prop('disabled',false);
					$('#month').prop('disabled',false);
					$('#load').prop('disabled',false);
					$("#mail").prop('disabled',false);
					console.log('complete');
				}
			});
		}
		else if (!(topic == '') || !(subTopic == '') || !(level == '')  || !(month == ''))
		{
			$('#topic').val('');
			$('#subTopic').val('');
			$('#level').val('');
			$('#month').val('');
			$("#spnMsg").html('');
			$('#load').prop('disabled',true);
			$('#topic').prop('disabled',true);
			$('#subTopic').prop('disabled',true);
			$('#level').prop('disabled',true);
			$('#month').prop('disabled',true);
			$("#mail").prop('disabled',true);
			console.log('topic=' + topic + '&subTopic=' + subTopic + '&level=' + level + '&month=' + month);
			$.ajax({
				url:'/internknowledgemanagementusingjdo',
				type:'post',
				data: 'topic=' + topic + '&subTopic=' + subTopic + '&level=' + level + '&month=' + month,
				success: function(data){
					$("#spnMsg").html('');
					if(month != '' && topic == '' && subTopic == '' && level == '')
					{
						if(data[0] != undefined)
						{
							$("#loadDetails").html('');
							for(var i = 0; i < data.length; i++)
							{
								$("#loadDetails").append("<div class='details'><div class='divLeft'>Name: "+data[i].name+"</div><div class='divRight'>Mail: "+data[i].mail+"</div><div class='divLeft'>Phone: "+data[i].phone+"</div><div class='divRight'>Qualification: "+data[i].qual+"</div><div class='divLeft'>Type: "+data[i].type+"</div><div class='divRight'>Month: "+data[i].month+"</div></div>");
							}
						}
						else
						{
							$("#spnMsg").html('<b>No Match found for your Query!</b>');
							$("#loadDetails").html('');
						}
					}
					else
					{
						if(data[0] != undefined)
						{
							$("#loadDetails").html("<div class='knowDetails'></div>");
							for(var i = 0; i < data.length; i++)
							{
								$(".knowDetails").append("<div class='eachDiv'><div class='divLeft'>Name: "+data[i].name+"</div><div class='divRight'>Mail: "+data[i].mail+"</div><div class='divLeft'>Topic: "+data[i].topic+"</div><div class='divRight'>SubTopic: "+data[i].subTopic+"</div><div class='divLeft'>level: "+data[i].level+"</div><div class='divRight'>Description: "+data[i].description+"</div></div>");
							}
						}
						else
						{
							$("#spnMsg").html('<b>No Match found for your Query!</b>');
							$("#loadDetails").html('');
						}
					}
				},
				error: function(jqxhr,status,thrownError){
	        		console.log('Error Contacting Server! '+jqxhr.status+' '+thrownError+'!');
					$("#spnMsg").html('Error in Server! '+jqxhr.status+' '+thrownError+'!');
					$("#loadDetails").html('');
					$('#topic').prop('disabled',false);
					$('#subTopic').prop('disabled',false);
					$('#level').prop('disabled',false);
					$('#month').prop('disabled',false);
					$("#mail").prop('disabled',false);
	        	},
				complete: function(){
					$('#load').prop('disabled',false);
					$('#topic').prop('disabled',false);
					$('#subTopic').prop('disabled',false);
					$('#level').prop('disabled',false);
					$('#month').prop('disabled',false);
					$("#mail").prop('disabled',false);
					console.log('complete');
				}
			});
		}
	});
$("#mail").keyup(checkMail);
$("#mail").keydown(checkMail);
});
function checkMail()
{
	if ($("#mail").val().trim() != '') {
		$("#month").prop('disabled',true);
	}
	else
	{
		$("#month").prop('disabled',false);
	}
}
function testMail(email)
{
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (filter.test(email)) return true;
	else return false;
}