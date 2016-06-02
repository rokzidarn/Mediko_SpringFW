$( document ).ready(function(){
	
	$("#caretaker").hide();
	$( window  ).on('resize', function(){
		hideSidebar();
		$("#orderCheckupDoctorInput").select2();
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
	$(".next-week-button").on('click', nextCalendar);
	$(".prev-week-button").on('click', previousCalendar);
	$(".save-work-week-button").on('click', saveWorkDay);
	$(".copy-current-week").on('click', copyCurrentWeek);


	//User lists
	$("#getUserListNotCompletedButton").on('click', getUserListNotCompleted);
	$("#getUserListNewButton").on('click', getUserListNew);
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
	return formatDateFromDate(date);
}

function formatTime(timeString){
	var date = new Date(timeString);
	return ("0" + date.getHours()).slice(-2)+":"+("0"+date.getMinutes()).slice(-2);
}

function formatDateFromDate(date){
	return ("0" + date.getDate()).slice(-2)+"."+("0" + (date.getMonth()+1)).slice(-2)+"."+date.getFullYear();
}

function addDays(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

jQuery.fn.extend({
    toggleText: function (a, b){
        var that = this;
            if (that.text() != a && that.text() != b){
                that.text(a);
            }
            else
            if (that.text() == a){
                that.text(b);
            }
            else
            if (that.text() == b){
                that.text(a);
            }
        return this;
    }
});

function usertypeLocaliaztion(userType){
	switch(userType){
		case "USER":
			return "uporabnik";
		case "DOCTOR":
			return "zdravnik";
		case "NURSE":
			return "medicinska sestra";
		case "ADMIN":
			return "administrator";
	}
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
	$("#orderCheckupSubmit").prop('disabled',true);
	var doctorId = $("#orderCheckupDoctorInput")[0].value;
	$.ajax({
	  		url: appUrl+"api/doctor/"+doctorId+"/workweek/available"
		}).done(function(outputData) {
			if(!outputData.canSelect){
				$(".disable-overlay").fadeIn(500);
				$("#timetableBlurDiv").addClass("blur-calendar")
			}else{
				$(".disable-overlay").fadeOut(500);
				$("#timetableBlurDiv").removeClass("blur-calendar")
			}
			var data = outputData.data;
	  		for(var i= 0; i < data.length; i++){
			//load calendar
				var date = new Date(data[i].startDate);
				for(var x = 0; x < 6; x++){
					$("#calendar"+(i+1)+"day"+(x+1)).text(formatDateFromDate(date));
					date = addDays(date,1);
					$("#calendar"+(i+1)+"day"+(x+1)).parent()[0].id = data[i].id;
					if(!outputData.canSelect || !data[i].workDays || data[i].workDays[x].appointments.length == 0){
						$("#calendar"+(i+1)+"day"+(x+1)+"Appointments").html("<div class=\"no-appointments\">prazno</div>");
					}else{
	                    $("#calendar"+(i+1)+"day"+(x+1)+"Appointments").html("");
	                    var appointments = data[i].workDays[x].appointments
	                    for(var a = 0; a < appointments.length; a++){
	                    	var div;
	                    	if(new Date(appointments[a].dateTime) < Date.now()){
	                    		div = "<div id=\""+appointments[a].idAppointment+"\" class=\"calendar-appointment-grayed \">"+formatTime(appointments[a].dateTime)+"</div>";
	                    	}else{
	                    	
		                    	if(a%2==0)
		                    		div = "<div id=\""+appointments[a].idAppointment+"\" class=\"calendar-appointment "+((appointments[a].doctorFreeTime || appointments[a].taken)?"appointment-taken":"")+" c-a-even\">"+formatTime(appointments[a].dateTime)+"</div>";
		                    	else
		                    		div = "<div id=\""+appointments[a].idAppointment+"\" class=\"calendar-appointment "+((appointments[a].doctorFreeTime || appointments[a].taken)?"appointment-taken":"")+" c-a-odd\">"+formatTime(appointments[a].dateTime)+"</div>";
								}
	                    	$("#calendar"+(i+1)+"day"+(x+1)+"Appointments").append(div);
	                    }
					}
				}
			}
			$(".calendar-appointment").on('click', appointmentOnCalendarClicked);
			if(outputData.canSelect)
				$("#orderCheckupSubmit").prop('disabled', false);
	  	});
}

function appointmentOnCalendarClicked(){
	if($(this)[0].className.indexOf("appointment-taken") == -1){
		var appointmentId = this.id;
		$(".calendar-appointment").removeClass("calendar-appointment-selected");
		$(this).addClass("calendar-appointment-selected");
		$("#orderCheckupAppointmentInput").val(appointmentId);
	}
}

var currentCalendar = 1;
function nextCalendar(){
	if(currentCalendar < 3){
		$("#calendar"+currentCalendar).slideToggle(500);
		currentCalendar++;
		$("#calendar"+currentCalendar).delay(500).slideToggle(500);
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
		$("#calendar"+currentCalendar).delay(500).slideToggle(500);
	}
	$(".next-week-button").show();
	if(currentCalendar == 1){
		$(".prev-week-button").hide();
	}
}

function doctorAppointmentOnCalendarClicked(){
	var appointmentId = $(this)[0].id;
  	
  	$.ajax({
    method: "post",
    url: appUrl+"/api/doctor/setfreetime",
    data: {
      _csrf:csrf, 
      appointmentId:appointmentId}
	})
	.done(function( data ) {
		if(data){
			if(data.doctorFreeTime)
				$("#"+data.idAppointment).addClass("doctor-free-time");
			else
				$("#"+data.idAppointment).removeClass("doctor-free-time");
		}
	});
}

function loadTimetableCalendar(){

	$.ajax({
  		url: appUrl+"api/doctor/"+doctorId+"/workweek"
	}).done(function(data) {
		for(var i= 0; i < data.length; i++){
			//load calendar
			var date = new Date(data[i].startDate);
			for(var x = 0; x < data[i].workDays.length; x++){
				$("#calendar"+(i+1)+"day"+(x+1)).text(formatDateFromDate(date));
				date = addDays(date,1);
				$("#calendar"+(i+1)+"day"+(x+1)).parent()[0].id = data[i].id;
				if(i > 0){
					$("#calendar"+(i+1)+"day"+(x+1)).siblings()[0].children[0].id = data[i].workDays[x].id;

					if(data[i].workDays[x].start) $("#calendar"+(i+1)+"day"+(x+1)).siblings()[0].children[0].children[1].value =data[i].workDays[x].start.slice(0,5);
					if(data[i].workDays[x].end) $("#calendar"+(i+1)+"day"+(x+1)).siblings()[0].children[0].children[3].value =data[i].workDays[x].end.slice(0,5);
					if(data[i].workDays[x].minuteInterval) $("#calendar"+(i+1)+"day"+(x+1)).siblings()[0].children[0].children[5].value =data[i].workDays[x].minuteInterval;
					if(data[i].workDays[x].note) $("#calendar"+(i+1)+"day"+(x+1)).siblings()[0].children[0].children[7].value =data[i].workDays[x].note;
				}
				if(data[i].workDays[x].appointments.length == 0){
					$("#calendar"+(i+1)+"day"+(x+1)+"Appointments").html("<div class=\"no-appointments\">prazno</div>");
				}else{
                    $("#calendar"+(i+1)+"day"+(x+1)+"Appointments").html("");
                    var appointments = data[i].workDays[x].appointments
                    for(var a = 0; a < appointments.length; a++){
                    	var div;
                    	if(a%2==0)
                    		div = "<div id=\""+appointments[a].idAppointment+"\" class=\"calendar-appointment doctor-calendar-appointment "+((appointments[a].taken)?"appointment-taken":"")+" "+((appointments[a].doctorFreeTime)?"doctor-free-time":"")+" c-a-even\">"+formatTime(appointments[a].dateTime)+"</div>";
                    	else
                    		div = "<div id=\""+appointments[a].idAppointment+"\" class=\"calendar-appointment doctor-calendar-appointment "+((appointments[a].taken)?"appointment-taken":"")+" "+((appointments[a].doctorFreeTime)?"doctor-free-time":"")+" c-a-odd\">"+formatTime(appointments[a].dateTime)+"</div>";

                    	$("#calendar"+(i+1)+"day"+(x+1)+"Appointments").append(div);
                    }
				}
			}
		}
		$(".doctor-calendar-appointment").on('click', doctorAppointmentOnCalendarClicked);

  	});
}

function saveWorkDay(){
	var workDayId = $(this).parent().siblings()[0].id
	var workDayStart = $(this).parent().siblings()[0].children[1].value;
	var workDayEnd = $(this).parent().siblings()[0].children[3].value;
	var workDayInterval = $(this).parent().siblings()[0].children[5].value;
	var workDayNote = $(this).parent().siblings()[0].children[7].value;

	$.ajax({
    method: "post",
    url: appUrl+"/api/doctor/updateworkday",
    data: {
      _csrf:csrf, 
      workDayId:workDayId,
      workDayStart:workDayStart,
      workDayEnd:workDayEnd,
      workDayInterval:workDayInterval,
      workDayNote:workDayNote}
  })
  .done(function( data ) {
  	if(data.length > 0){
	  	var workDayId = data[0].workDayId;
	  	var editorId = $("#"+workDayId).parent()[0].id
	  	$("#"+editorId).slideToggle(500);
	  	$($("#"+workDayId).parent().siblings()[1]).toggleText('Skrij', 'Uredi');
	  	loadTimetableCalendar();
  	}
  });

}

function copyCurrentWeek(){
	var currentWeekId = $("#calendar1").children()[1].id;
	var destinationWeekId = $(this).parent().parent().siblings()[0].id;
	$.ajax({
    method: "post",
    url: appUrl+"/api/doctor/copyworkweek",
    data: {
      _csrf:csrf, 
      currentWeekId:currentWeekId,
      destinationWeekId:destinationWeekId}
	})
	.done(function( data ) {
		console.log(data);
		if(data.length > 0){
	  		loadTimetableCalendar();
		}
	});
}                     


function getUserListNotCompleted(){
	var filterTypeInput = $("#filterTypeInput").val();
	var searchInput = $("#searchInput").val();
	var hitsNumberInput = $("#hitsNumberInput").val();
	var orderTypeInput = $("#orderTypeInput").val();
	var showUser = $("#showUser").prop("checked");
	var showDoctor = $("#showDoctor").prop("checked");
	var showNurse = $("#showNurse").prop("checked");

	
	$.ajax({
	  method: "post",
	  url: appUrl+"api/user/notcompleted",
	  data: {
	  	_csrf:csrf, 
	  	filterTypeInput: filterTypeInput,
	  	searchInput: searchInput,
	  	hitsNumberInput:hitsNumberInput,
	  	orderTypeInput:orderTypeInput,
	  	showUser:showUser,
	  	showDoctor:showDoctor,
	  	showNurse:showNurse }
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
				"<td>" + formatDate(data[i].registrationDate)+"</td>"+
				"<td>"+ usertypeLocaliaztion(data[i].userType) +"</td>";
			tableBody.append(tr);
		}
	});
}

function getUserListNew(){
	var filterTypeInput = $("#filterTypeInput").val();
	var searchInput = $("#searchInput").val();
	var hitsNumberInput = $("#hitsNumberInput").val();
	var orderTypeInput = $("#orderTypeInput").val();
	var showUser = $("#showUser").prop("checked");
	var showDoctor = $("#showDoctor").prop("checked");
	var showNurse = $("#showNurse").prop("checked");
	var fromInput = $("#fromInput").val();
	var toInput = $("#toInput").val();
	
	$.ajax({
	  method: "post",
	  url: appUrl+"api/user/new",
	  data: {
	  	_csrf:csrf, 
	  	filterTypeInput: filterTypeInput,
	  	searchInput: searchInput,
	  	hitsNumberInput:hitsNumberInput,
	  	orderTypeInput:orderTypeInput,
	  	showUser:showUser,
	  	showDoctor:showDoctor,
	  	showNurse:showNurse,
	  	fromInput:fromInput,
	  	toInput:toInput }
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
				"<td>" + formatDate(data[i].registrationDate)+"</td>"+
				"<td>"+ usertypeLocaliaztion(data[i].userType) +"</td>";
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
    $('#exportTable').css("left",($('#exportTable').offset().left* -1)+45);
    html2pdf ($('#exportTable'),doc,function(doc){
    	
        doc.save("SeznamUporabnikov.pdf");
        $('#exportTable').removeClass("user-list-export-settings");
        $('#exportTable').css("left","0");
    });
}