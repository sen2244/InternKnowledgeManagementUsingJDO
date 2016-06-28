    window.onload = checkCookies();
    function checkCookies(){
    	var cookies = new Array();
    	cookies = unescape(document.cookie).split(';');
    	if(cookies.length > 1)
    	{
    		console.log("Cookies Available!");
    	}
    	else
    	{
    		console.log('No cookie');
    		window.location.href = "/HomePage.html";
    	}
    	setTimeout(checkCookies,15000);
    };
function clearMsg()
{
	$("#spnMsg").html("");
	return false;
}
function enable()
{
	$("#add").prop('disabled',false);
	return false;
}
$(document).ready(function(){
	$("#add").click(function(){
		$("#add").prop('disabled',true);
		setTimeout(enable,1000);
		var top = $("#topic").val().trim().toUpperCase();
		var subTop = $("#subTopic").val().trim().toUpperCase();
		var level = $("#level").val();
		var des = $("#description").val().trim();
		if(top == '' || top == ' ' || subTop == '' || subTop == ' ' || level == '' || level == ' ' || des == '' || des == ' ')
		{
			$("#spnMsg").html("*Please provide all inputs!");
		}
		else if(!isNaN(top) || !isNaN(subTop) || !isNaN(des))
		{
			$("#spnMsg").html("*Please provide <b>String inputs!</b>");
		}
		else
		{
			$("#tableDiv").prop('style','display:block;');
			$("#submit").prop('style','display:block;');
			$("#topic").val('');
			$("#subTopic").val('');
			$("#level").val('');
			$("#description").val('');
			$('#skillsTable').prepend('<tr onclick="disp(this);"><td>'+top+'</td><td>'+subTop+'</td><td>'+level+'</td></tr><tr style="display:none"><td colspan="3">'+des+'</td></tr>');
			$("#spnMsg").html("Added!");
			setTimeout(clearMsg,2000);
		}
	});
});
$(document).ready(function(){
	var click = 1;
	$("#submit").click(function(){
		var top = $("#topic").val().trim().toUpperCase();
		var subTop = $("#subTopic").val().trim().toUpperCase();
		var level = $("#level").val();
		var des = $("#description").val().trim();
		console.log(click);
		if((top != '' || subTop != ''|| level != '' || des != '') && click != 0)
		{
			$("#spnMsg").html("Hey there is some Entries in the Textboxes! To proceed click <b>Submit</b> Again!");
			click = 0;
		}
		else
		{
			$(this).prop('disabled',true);
			var tdCount = $('#skillsTable > tr > td').length;
			var trCount = $('#skillsTable > tr').length;
			if (trCount < 1 && (top != '' || subTop != ''|| level != '' || des != '')) 
			{
				var knowArr = new Array();
				var knowlDetails = new Object();
				knowlDetails.topic = top;
				knowlDetails.subTopic = subTop;
				knowlDetails.level = level;
				knowlDetails.description = des;
				knowArr[0] = knowlDetails;
				var Knodata = JSON.stringify(knowArr);
				var mailAdd = sessionStorage.getItem("mail");
				var name = sessionStorage.getItem("name");
				console.log(name);
				var transData = 'data='+Knodata;
				console.log(Knodata);
				$("#topic").prop('disabled',true);
				$("#subTopic").prop('disabled',true);
				$("#level").prop('disabled',true);
				$("#description").prop('disabled',true);
				$("#submit").prop('disabled',true);
				$.ajax({
					url: '/knowledgeservlet',
					type: "post",
					data: transData,
		        	success: function(data){
		        		console.log(data);
		        		$("#spnMsg").html(data+'!');
		        		if(data == 'Success')
		        		{
		        			$('#skillsTable').html('');
		        			window.location.href = "/HomePage.html";
		        		}
		        		else if(data == 'Error') $("#submit").prop('disabled',false);
		        	},
		        	error: function(jqxhr,status,thrownError){
		        		console.log('Error Contacting Server! '+jqxhr.status+' '+thrownError+'!');
						$("#spnMsg").html('Error Contacting Server! '+jqxhr.status+' '+thrownError+'!');
						$("#submit").prop('disabled',false);
						$("#topic").prop('disabled',false);
						$("#subTopic").prop('disabled',false);
						$("#level").prop('disabled',false);
						$("#description").prop('disabled',false);
		        	}
			});
			}
			else
			{
				var knowArr = new Array();
				var i = j = 0;
				while(i < tdCount)
				{
					var knowlDetails = new Object();
					knowlDetails.topic = $('#skillsTable > tr').children()[i].innerHTML;
					knowlDetails.subTopic = $('#skillsTable > tr').children()[i + 1].innerHTML;
					knowlDetails.level = $('#skillsTable > tr').children()[i + 2].innerHTML;
					knowlDetails.description = $('#skillsTable > tr').children()[i + 3].innerHTML;
					knowArr[j] = knowlDetails;
					i += 4;
					j++;
				}
				var Knodata = JSON.stringify(knowArr);
				var mailAdd = sessionStorage.getItem("mail");
				var name = sessionStorage.getItem("name");
				var transData = 'data='+Knodata;
				console.log(Knodata);
				$("#topic").prop('disabled',true);
				$("#subTopic").prop('disabled',true);
				$("#level").prop('disabled',true);
				$("#description").prop('disabled',true);
				$("#submit").prop('disabled',true);
				$.ajax({
					url: '/knowledgeservlet',
					type: "post",
					data: transData,
		        	success: function(data){
		        		console.log(data);
		        		$("#spnMsg").html(data+'!');
		        		if(data == 'Success')
		        		{
		        			$('#skillsTable').html('');
		        			window.location.href = "/HomePage.html";
		        		}
		        		else if(data == 'Error') $("#submit").prop('disabled',false);
		        	},
		        	error: function(jqxhr,status,thrownError){
		        		console.log('Error Contacting Server! '+jqxhr.status+' '+thrownError+'!');
						$("#spnMsg").html('Error Contacting Server! '+jqxhr.status+' '+thrownError+'!');
						$("#submit").prop('disabled',false);
						$("#topic").prop('disabled',false);
						$("#subTopic").prop('disabled',false);
						$("#level").prop('disabled',false);
						$("#description").prop('disabled',false);
		        	}
				});
			}
		}
	});
});
function check()
{
		var top = $("#topic").val().trim();
		var subTop = $("#subTopic").val().trim();
		var level = $("#level").val();
		var des = $("#description").val().trim();
		var trCount = $('#skillsTable > tr').length;
		if(top != '' && subTop != '' && level != '' && des != '')
		{
			$("#submit").prop('style','display:block;');
			return false;
		}
		else if(trCount < 1)
		{
			$("#submit").prop('style','display:none;');
			return false;
		}
}
$(document).ready(function(){
	$("#skillsTable > tr").click(function(){
		console.log(this.next());
	});
});
function disp(elem)
{
	$(elem).next().toggle(300);
}