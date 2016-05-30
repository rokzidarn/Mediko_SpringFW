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

	$("#showMoreCheckup").on('click', toggleShowMoreCheckup);
	$("#showMoreDisease").on('click', toggleShowMoreDisease);
	$("#showMoreAlergy").on('click', toggleShowMoreAlergy);
	$("#showMoreDiet").on('click', toggleShowMoreDiet);
	$("#showMoreMedicine").on('click', toggleShowMoreMedicine);
	$("#deleteMedicineB").on('click', addValueDiseaseId);
	$("#diseaseInput").on('change', showDiseaseInstructions);
	$("#dietInput").on('change', showDietInstructions);
	$("#medicineInput").on('change', showMedicineInstructions);
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

function addValueDiseaseId(){
	var e = document.getElementById("diseaseInput");
	var diseaseId = e.options[e.selectedIndex].value;
	$("#diseaseValue").val(diseaseId);
}

var diseaseInstructions;
function showDiseaseInstructions(){
	//var e = document.getElementById("diseaseInput");
	//var diseaseId = e.options[e.selectedIndex].value;
	var diseaseId = $("#diseaseInput").val();
	if(diseaseId === "virus ni" || diseaseId === "virusni"){
		diseaseId = "virus ni "
	}
	$.ajax({
  		url: appUrl+"api/instructions/disease/"+diseaseId
	}).done(function(data) {
  		diseaseInstructions = data;
  		$("#dis").empty();
  		//document.getElementById("dis").innerHTML = "";
		for(var i = 0; i<diseaseInstructions.length; i++){
			var dIns = diseaseInstructions[i];
			var info = "<form method=\"POST\" action=\"#springUrl('/admin')/ddel\">"+
                            "<input type=\"hidden\" name=\"${_csrf.parameterName}\" value=\"${_csrf.token}\"/>"+
                            "#if($commandidd) #springBind( \"commandidd.id\" ) #end"+
                            "<input type=\"hidden\" name=\"id\" value="+dIns.id+"/>"+
                            "<p><button type=\"submit\" class=\"btn btn-info\">ODSTRANI</button>&nbsp; <a style=\"color: dodgerblue;\" href="+dIns.text+">"+dIns.text+"</a></p>"+
                            "#foreach($error in $status.errorMessages) <div class=\"alert-box alert\">$error</div> #end"+
                        "</form>";

			$("#dis").append(info);
		}
  	});
}

var dietInstructions;
function showDietInstructions(){
	var dietId = $("#dietInput").val();
	$.ajax({
  		url: appUrl+"api/instructions/diet/"+dietId
	}).done(function(data) {
  		dietInstructions = data;
  		$("#die").empty();
  		//document.getElementById("dis").innerHTML = "";
		for(var i = 0; i<dietInstructions.length; i++){
			var diIns = dietInstructions[i];
			var info = "<form method=\"POST\" action=\"#springUrl('/admin')/didel\">"+
                            "<input type=\"hidden\" name=\"${_csrf.parameterName}\" value=\"${_csrf.token}\"/>"+
                            "#if($commandiddi) #springBind( \"commandiddi.id\" ) #end"+
                            "<input type=\"hidden\" name=\"id\" value="+diIns.id+"/>"+
                            "<p><button type=\"submit\" class=\"btn btn-info\">ODSTRANI</button>&nbsp; <a style=\"color: dodgerblue;\" href="+diIns.text+">"+diIns.text+"</a></p>"+
                            "#foreach($error in $status.errorMessages) <div class=\"alert-box alert\">$error</div> #end"+
                        "</form>";

			$("#die").append(info);
		}
  	});
}

var medicineInstructions;
function showMedicineInstructions(){
	var medicineId = $("#medicineInput").val();
	$.ajax({
  		url: appUrl+"api/instructions/medicine/"+medicineId
	}).done(function(data) {
  		medicineInstructions = data;
  		$("#med").empty();
  		//document.getElementById("dis").innerHTML = "";
		for(var i = 0; i<medicineInstructions.length; i++){
			var mIns = medicineInstructions[i];
			var info = "<form method=\"POST\" action=\"#springUrl('/admin')/didel\">"+
                            "<input type=\"hidden\" name=\"${_csrf.parameterName}\" value=\"${_csrf.token}\"/>"+
                            "#if($commandidm) #springBind( \"commandidm.id\" ) #end"+
                            "<input type=\"hidden\" name=\"id\" value="+mIns.id+"/>"+
                            "<p><button type=\"submit\" class=\"btn btn-info\">ODSTRANI</button>&nbsp; <a style=\"color: dodgerblue;\" href="+mIns.text+">"+mIns.text+"</a></p>"+
                            "#foreach($error in $status.errorMessages) <div class=\"alert-box alert\">$error</div> #end"+
                        "</form>";

			$("#med").append(info);
		}
  	});
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
                   				"<td scope=\"row\">"+checkupMoreData[i].appointment.date+"</td>"+
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