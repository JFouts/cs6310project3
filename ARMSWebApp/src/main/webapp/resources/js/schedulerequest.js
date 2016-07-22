var courseList = ["6035 - Intro To Information Security",
                  "6210 - Advanced Operating Systems",
                  "6250 - Computer Networks",
                  "6262 - Network Security",
                  "6290 - High Performance Computer Architecture",
                  "6300 - Software Development Process",
                  "6310 - Software Architecture & Design",
                  "6340 - Software Analysis & Test",
                  "6400 - Database Systems Concepts & Design",
                  "6440 - Intro Health Informatics",
                  "6460 - Education Tech-Foundations",
                  "6475 - Computational Photography",
                  "6476 - Computer Vision",
                  "6505 - Computability & Algorithms",
                  "6601 - Artificial Intelligence",
                  "7637 - Knowledge-Based AI",
                  "7641 - Machine Learning",
                  "7646 - Mach Learning For Trading"
                  ];

function addCourseDropdown() {
	var n = $(".coursedd-line").length;
	if (n > 0)
		$(".another:last").css("display", "none");
	
	var ddhtml = '<div class="coursedd-line">\n';
	ddhtml += '  <div class="form-group courseddgrp">\n';
	ddhtml += '    <select class="form-control coursedd">\n';
	courseList.forEach(function(course) {
		ddhtml += '      <option>' + course + '</option>\n';
	});
	ddhtml += '    </select>\n';
	ddhtml += '  </div>\n';
	ddhtml += '  <button type="button" class="btn btn-danger btn-sm delete"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>\n';
	ddhtml += '  <button type="button" class="btn btn-default another">Add Another Course</button>\n';
	ddhtml += '</div>\n';
	
	$("#srf-dropdowns").append(ddhtml);
	
	if (n == 0) // just added the first one
		$(".delete").css("visibility", "hidden");
	else // there are at least two now
		$(".delete").css("visibility", "visible");
	
}

function displaySchedule() {
	$(".schedule").html("<hr>Here's your system generated schedule!");
}

function validScheduleRequest() {
	var requests = [];
	var error = false;
	$(".coursedd").each(function(index) {
	  var course = $(this).val();
	  if (requests.indexOf(course) > -1) {
		  var errorhtml = "";
		  errorhtml += '<div class="alert alert-danger alert-dismissible" role="alert">\n';
	      errorhtml += '  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>\n';
		  errorhtml += '  <strong>Error!</strong> Your schedule request contains the same course twice: ' + course + '\n';
		  errorhtml += '</div>\n';
		  $(".validation-error").html(errorhtml);
		  error = true;
		  return false;
	  }
	  requests.push(course);
	});
	if (error)
		return false;
	$(".validation-error").html("");
	return true;
}

$("#submit").click(function() {
	var valid = validScheduleRequest(); 
	console.log(valid);
	if (valid) {
		$("#submit,.coursedd,.another,.delete").prop('disabled', true);
		// get schedule
		displaySchedule();
	}
});

$(document).on('click', '.delete', function(){ 
	$(this).parents()[0].remove();
	var n = $(".coursedd-line").length;
	if (n == 1) // down to the last one
		$(".delete").css("visibility", "hidden");
	$(".another:last").css("display", "inline-block");
	
});

$(document).on('click', '.another', function(){ 
	addCourseDropdown();
});

$(document).ready(function() {
	addCourseDropdown();
});