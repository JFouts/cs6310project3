function addCourseDropdown(selection) {
	var n = $(".coursedd-line").length;
	if (n > 0)
		$(".another:last").css("display", "none");
	
	var ddhtml = '<div class="coursedd-line">\n';
	ddhtml += '  <div class="form-group courseddgrp">\n';
	ddhtml += '    <select class="form-control coursedd" name="course">\n';
	
	$.each(courseMap, function(courseId, courseName) {
		if (selection == courseName)
    		ddhtml += '      <option value="' + courseId + '" selected="selected">' + courseName + '</option>\n';
    	else
    		ddhtml += '      <option value="' + courseId + '">' + courseName + '</option>\n';
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

function validScheduleRequest() {
	var requests = [];
	var error = false;
	$(".coursedd").each(function(index) {
	  var course = $(this).val();
	  if (requests.indexOf(course) > -1) {
		  var errorhtml = "";
		  errorhtml += '<div class="alert alert-danger alert-dismissible" role="alert">\n';
	      errorhtml += '  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>\n';
		  errorhtml += '  <strong>Error!</strong> Your schedule request contains the same course twice: ' + courseMap[course] + '\n';
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

$("#scheduleRequestForm").submit(function(e) {
	var valid = validScheduleRequest(); 
	if (!valid) {
		e.preventDefault();
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
	addCourseDropdown(null);
});

$(document).ready(function() {
	var rc = requestedCourses.length;
	if (rc == 0)
		addCourseDropdown(null);
	else {
		for (var i = 0; i < rc; i++)
			addCourseDropdown(requestedCourses[i]);
		$("#submit,.coursedd,.another,.delete").prop('disabled', true);
	}	
});


