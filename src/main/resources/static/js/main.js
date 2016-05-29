$( document ).ready(function(){
	
	$("#caretaker").hide();
	$( window  ).on('resize', function(){
		hideSidebar();
	});

	//WHEN PAGE LOADS

	$("#showSidebarButton").on('click', function(){
		showSidebar();
	});

	$("#hideSidebarButton").on('click', function(){
		hideSidebar();
	});
	
	$(document).on('change' , '#checkbox' , function(){

	    if(this.checked) {
	    	$("#caretaker").show();
	    }
	    else  {
	    	$("#caretaker").hide();
	    }
	});
	
	//SET CONTENT HEIGT!
	$(".content-container").css("min-height",$("#sidebarContent").height()+50);


	//SHOW MORE ON DSAHBOARD
	$("#showMoreCheckup").on('click', toggleShowMoreCheckup);
	$("#showMoreDisease").on('click', toggleShowMoreDisease);
	$("#showMoreAlergy").on('click', toggleShowMoreAlergy);
	$("#showMoreDiet").on('click', toggleShowMoreDiet);
	$("#showMoreMedicine").on('click', toggleShowMoreMedicine);
	$("#showMoreResult").on('click', toggleShowMoreResult);


	//DOCTORS SELECTED ON ORDER APPOINTMENT
	$("#orderCheckupDoctorInput").on('change', getDoctorsAvailableAppointments);

	//Calendar
	$(".calendar-appointment").on('click', appointmentOnCalendarClicked);
	$(".next-week-button").on('click', nextCalendar);
	$(".prev-week-button").on('click', previousCalendar);


	//User lists
	$("#getUserListNotCompletedButton").on('click', getUserListNotCompleted);
	$("#exportUserListButton").on('click', exportUserList);
});

function showSidebar(){
	$("#sidebar").removeClass("hidden-sm");
	$("#sidebar").removeClass("hidden-xs");
	$("#sidebar").addClass("sidebar-visible");
}

function hideSidebar(){
	$("#sidebar").addClass("hidden-sm");
	$("#sidebar").addClass("hidden-xs");
	$("#sidebar").removeClass("sidebar-visible");
}

function opencheckup(link){
	window.location.href = link;
}

function formatDate(dateString){
	var date = new Date(dateString);

	return ("0" + date.getDate()).slice(-2)+"."+("0" + date.getMonth()).slice(-2)+"."+date.getFullYear();
}

var checkupMoreData;
function toggleShowMoreCheckup(){
	var patientId = $("#selectedPatientId")[0].innerText;
	if(!checkupMoreData){
		$.ajax({
	  		url: appUrl+"api/patient/"+patientId+"/checkup"
		}).done(function(data) {
	  		checkupMoreData = data;
	  		if(checkupMoreData.length > 5){
	  			for(var i = 5; i < checkupMoreData.length; i++){
	  				//<tr onclick="opencheckup('#springUrl('/checkup/')'+ ${checkup.id})">
        	                                                               // <td scope="row">$checkup.appointment.date</td>
        	                                                               // <td>dr. $checkup.doctor.last_name</td> 
        	                                                               // <td>$checkup.reason</td>
        	                                                           // </tr> 
                   	var tr =	"<tr onclick=\"opencheckup('../checkup/"+checkupMoreData[i].id+"')\">"+
                   				"<td scope=\"row\">"+formatDate(checkupMoreData[i].appointment.date)+"</td>"+
                   				"<td>dr. "+checkupMoreData[i].doctor.last_name+"</td>"+
                   				"<td>"+checkupMoreData[i].reason+"</td>"+
                   				"</tr>";
       				$("#checkupMoreTbody").append(tr);
       				$("#showMoreCheckup").text("Skrij več");
	  			}
	  		}
	  	});
	}else{
		if($("#showMoreCheckup").text() == "Pokaži več"){
			$("#showMoreCheckup").text("Skrij več");
		}else{
			$("#showMoreCheckup").text("Pokaži več");
		}
		$("#checkupMoreTbody").toggle(500);

	}
}

var diseaseMoreData;
function toggleShowMoreDisease(){
	var patientId = $("#selectedPatientId")[0].innerText;
	if(!diseaseMoreData){
		$.ajax({
	  		url: appUrl+"api/patient/"+patientId+"/disease/0"
		}).done(function(data) {
	  		diseaseMoreData = data;
	  		if(diseaseMoreData.length > 5){
	  			for(var i = 5; i < diseaseMoreData.length; i++){

	  				var instructions = "";
	  				for(var k = 0; k < diseaseMoreData[i].instructions.length; k++){
	  					if(k!=0) instructions += "<br>";
	  					instructions+="<a style=\"color:#36B8D5\" href=\""+diseaseMoreData[i].instructions[k].text+"\">Povezava</a>";
	  				}

                   	var tr =	"<tr>"+
                   				"<td>"+diseaseMoreData[i].name+"</td>"+
                   				"<td>"+((instructions == "")? "-" : instructions)+" </td>"+
                   				"</tr>";
       				$("#diseaseMoreTbody").append(tr);
       				$("#showMoreDisease").text("Skrij več");
	  			}
	  		}
	  	});
	}else{
		if($("#showMoreDisease").text() == "Pokaži več"){
			$("#showMoreDisease").text("Skrij več");
		}else{
			$("#showMoreDisease").text("Pokaži več");
		}
		$("#diseaseMoreTbody").toggle(500);

	}
}

var alergyMoreData;
function toggleShowMoreAlergy(){
	var patientId = $("#selectedPatientId")[0].innerText;
	if(!alergyMoreData){
		$.ajax({
	  		url: appUrl+"api/patient/"+patientId+"/disease/1"
		}).done(function(data) {
	  		alergyMoreData = data;
	  		if(alergyMoreData.length > 5){
	  			for(var i = 5; i < alergyMoreData.length; i++){

	  				var instructions = "";
	  				for(var k = 0; k < alergyMoreData[i].instructions.length; k++){
	  					if(k!=0) instructions += "<br>";
	  					instructions+="<a style=\"color:#36B8D5\" href=\""+alergyMoreData[i].instructions[k].text+"\">Povezava</a>";
	  				}

                   	var tr =	"<tr>"+
                   				"<td>"+alergyMoreData[i].name+"</td>"+
                   				"<td>"+((instructions == "")? "-" : instructions)+"</td>"+
                   				"</tr>";
       				$("#alergyMoreTbody").append(tr);
       				$("#showMoreAlergy").text("Skrij več");
	  			}
	  		}
	  	});
	}else{
		if($("#showMoreAlergy").text() == "Pokaži več"){
			$("#showMoreAlergy").text("Skrij več");
		}else{
			$("#showMoreAlergy").text("Pokaži več");
		}
		$("#alergyMoreTbody").toggle(500);

	}
}

var dietMoreData;
function toggleShowMoreDiet(){
	var patientId = $("#selectedPatientId")[0].innerText;
	if(!dietMoreData){
		$.ajax({
	  		url: appUrl+"api/patient/"+patientId+"/diet"
		}).done(function(data) {
	  		dietMoreData = data;
	  		if(dietMoreData.length > 5){
	  			for(var i = 5; i < dietMoreData.length; i++){

	  				var instructions = "";
	  				for(var k = 0; k < dietMoreData[i].instructions.length; k++){
	  					if(k!=0) instructions += "<br>";
	  					instructions+="<a style=\"color:#36B8D5\" href=\""+dietMoreData[i].instructions[k].text+"\">"+dietMoreData[i].instructions_Diets[k].text+"</a>";
	  				}

                   	var tr =	"<tr>"+
                   				"<td scope=\"row\">"+dietMoreData[i].name+"</td>"+
                   				"<td>"+
                   					instructions+
                   				"</td>"+
                   				"</tr>";

       				$("#dietMoreTbody").append(tr);
       				$("#showMoreDiet").text("Skrij več");
	  			}
	  		}
	  	});
	}else{
		if($("#showMoreDiet").text() == "Pokaži več"){
			$("#showMoreDiet").text("Skrij več");
		}else{
			$("#showMoreDiet").text("Pokaži več");
		}
		$("#dietMoreTbody").toggle(500);

	}
}

var medicineMoreData;
function toggleShowMoreMedicine(){
	var patientId = $("#selectedPatientId")[0].innerText;
	if(!medicineMoreData){
		$.ajax({
	  		url: appUrl+"api/patient/"+patientId+"/medicine"
		}).done(function(data) {
	  		medicineMoreData = data;
	  		if(medicineMoreData.length > 5){
	  			for(var i = 5; i < medicineMoreData.length; i++){
	  				//<tr> 
                    //                                                <td scope="row">$medicine.getName()</td> 
                     //                                               <td><a style="color:#36B8D5" href="$medicine.link">Povezava</a></td> 
                     //                                           </tr> 
                   	var tr =	"<tr>"+
                   				"<td scope=\"row\">"+medicineMoreData[i].name+"</td>"+
                   				"<td><a style=\"color:#36B8D5\" href=\""+medicineMoreData[i].link+"\">Povezava</a></td>"+
                   				"</tr>";
       				$("#medicineMoreTbody").append(tr);
       				$("#showMoreMedicine").text("Skrij več");
	  			}
	  		}
	  	});
	}else{
		if($("#showMoreMedicine").text() == "Pokaži več"){
			$("#showMoreMedicine").text("Skrij več");
		}else{
			$("#showMoreMedicine").text("Pokaži več");
		}
		$("#medicineMoreTbody").toggle(500);

	}
}  

var resultMoreData;
function toggleShowMoreResult(){
	var patientId = $("#selectedPatientId")[0].innerText;
	if(!resultMoreData){
		$.ajax({
	  		url: appUrl+"api/patient/"+patientId+"/result"
		}).done(function(data) {
	  		resultMoreData = data;
	  		if(resultMoreData.length > 5){
	  			/*
				<tr onclick="$('#meritev-$velocityCount').toggle(500)"> 
			        <td scope="row">$result.type</td> 
			        <td>$_utils.format($result.checkup.appointment.date)</td> 
			        <td>-</td>                                                                
			    </tr>
			    <tr class="info" id="meritev-$velocityCount" style="display: none">
			        <td colspan="3">
			            <div>
			                $result.value <br>
			                $result.text
			            </div>
			        </td>
			    </tr>
	  			*/
	  			for(var i = 5; i < resultMoreData.length; i++){
	  				var tr = "<tr onclick=\"$('#meritev-"+(i+1)+"').toggle(500)\">"+
	  							"<td scope=\"row\">"+resultMoreData[i].type+"</td>"+
	  							"<td>"+formatDate(resultMoreData[i].checkup.appointment.date)+"</td>"+
	  							"<td>-</td>"+
  							"</tr>"+
  							"<tr class=\"info\" id=\"meritev-"+(i+1)+"\" style=\"display:none\">"+
	  							"<td colspan=\"3\">"+
		  							"<div>"+
			  							resultMoreData[i].value+" <br>"+
			  							resultMoreData[i].text+
		  							"</div>"+
	  							"</td>"+
  							"</tr>";
  					$("#resultMoreTbody").append(tr);
  					$("#showMoreResult").text("Skrij več");
	  			}
	  			/*
	  			for(var i = 5; i < medicineMoreData.length; i++){
	  				//<tr> 
                    //                                                <td scope="row">$medicine.getName()</td> 
                     //                                               <td><a style="color:#36B8D5" href="$medicine.link">Povezava</a></td> 
                     //                                           </tr> 
                   	var tr =	"<tr>"+
                   				"<td scope=\"row\">"+medicineMoreData[i].name+"</td>"+
                   				"<td><a style=\"color:#36B8D5\" href=\""+medicineMoreData[i].link+"\">Povezava</a></td>"+
                   				"</tr>";
       				$("#medicineMoreTbody").append(tr);
       				$("#showMoreMedicine").text("Skrij več");
	  			}*/
	  		}
	  	});
	}else{
		if($("#showMoreResult").text() == "Pokaži več"){
			$("#showMoreResult").text("Skrij več");
		}else{
			$("#showMoreResult").text("Pokaži več");
		}
		$("#resultMoreTbody").toggle(500);

	}
}

function getDoctorsAvailableAppointments(){
	$("#orderCheckupAppointmentInput").prop('disabled', true);
	$("#orderCheckupSubmit").prop('disabled',true);
	var doctorId = this.value;
	$.ajax({
	  		url: appUrl+"api/doctor/"+doctorId+"/appointment/available"
		}).done(function(data) {
	  		alert(doctorId);
	  		/*console.log(data);
	  		var appointmentsInput = $("#orderCheckupAppointmentInput");
	  		appointmentsInput.html("");
	  		for(var i = 0; i < data.length;i++){
	  			

	  			var option = $("<option />");
	  			option.val(data[i].idAppointment);
	  			option.text( data[i].date);

	  			appointmentsInput.append(option);
	  		}
	  		appointmentsInput.prop('disabled', false);
			*/


			//todo
			//BUILD urnik!!!
			$("#orderCheckupSubmit").prop('disabled',false);
	  	});
}

function appointmentOnCalendarClicked(){
	var appointmentId = this.id;
	$(".calendar-appointment").removeClass("calendar-appointment-selected");
	$(this).addClass("calendar-appointment-selected");
	$("#orderCheckupAppointmentInput").val(appointmentId);
}

var currentCalendar = 1;
function nextCalendar(){
	if(currentCalendar < 3){
		$("#calendar"+currentCalendar).slideToggle(500);
		currentCalendar++;
		$("#calendar"+currentCalendar).slideToggle(500);
	}

	$(".prev-week-button").show();
	if(currentCalendar == 3){
		$(".next-week-button").hide();
	}
}         
function previousCalendar(){
	if(currentCalendar > 1){
		$("#calendar"+currentCalendar).slideToggle(500);
		currentCalendar--;
		$("#calendar"+currentCalendar).slideToggle(500);
	}
	$(".next-week-button").show();
	if(currentCalendar == 1){
		$(".prev-week-button").hide();
	}
<<<<<<< HEAD
}                     


function getUserListNotCompleted(){
	var filterTypeInput = $("#filterTypeInput").val();
	var searchInput = $("#searchInput").val();
	var hitsNumberInput = $("#hitsNumberInput").val();
	$.ajax({
	  method: "post",
	  url: appUrl+"api/user/notcompleted",
	  data: {_csrf:csrf, filterTypeInput: filterTypeInput, searchInput: searchInput,hitsNumberInput:hitsNumberInput }
	})
	.done(function( data ) {
		var tableBody = $("#tableTbody");
		tableBody.html("");
		for(var i = 0; i < data.length; i++){
			/*
			<tr"> 
				<th scope="row">1</th> 
				<td>Column content</td> 
				<td>Column content</td> 
				<td>Column content</td>
			</tr> 
			*/
			var tr = "<tr>"+
				"<th scope=\"row\">"+(i+1)+"</th>"+
				"<td>" + data[i].username+"</td>"+
				"<td>" + data[i].registrationDate+"</td>"+
				"<td> - </td>";
			tableBody.append(tr);
		}
	});
}

function exportUserList(){
	var doc = new jsPDF("p", "pt", "a4");

    // We'll make our own renderer to skip this editor
    var specialElementHandlers = {
        '#editor': function(element, renderer){
            return true;
        }
    };

    // All units are in the set measurement for the document
    // This can be changed to "pt" (points), "mm" (Default), "cm", "in"
    //doc.save('Test.pdf');
    $('#exportTable').addClass("user-list-export-settings");
    html2pdf ($('#exportTable'),doc,function(doc){
    	
        doc.save("SeznameUporabnikov.pdf");
        $('#exportTable').removeClass("user-list-export-settings");
    });
=======
>>>>>>> develop
}