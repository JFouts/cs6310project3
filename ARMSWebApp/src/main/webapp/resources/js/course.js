function validEditCourse() {
	var errorList = [];
	if($("#coursename").val().length == 0)
		errorList.push("Course Name cannot be empty.");
	
	var prereqs = $('#prereqs').val();
	if (prereqs == null || (prereqs.length > 1 && prereqs.indexOf("none") > -1))
		errorList.push("Preprequisities must be None OR one or more class selections.");
	
	var availability = $('#availability').val();
	if (availability == null)
		errorList.push("Availability must be set to at least one semester.");
	
	var ms = $("#maxsize").val();
	if(ms.length == 0 || isNaN(ms) || Number(ms) == 0 ||  Number(ms) < -1)
		errorList.push("Max Classroom Size must be a positive integer or -1.");
	
	if (errorList.length > 0) {
		var errorhtml = '<div class="alert alert-danger alert-dismissible" role="alert">\n';
		errorhtml += '  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>\n';
		if (errorList.length == 1)
			errorhtml += '  <strong>Error!</strong> ' + errorList[0] + "\n";
		else // multiple errors
			errorhtml += '  <strong>Errors!</strong><br>' + errorList.join("<br>") + "\n";
		errorhtml += '</div>\n';
		$(".validation-error").html(errorhtml);
		return false;
	}
	$(".validation-error").html("");
	return true;
}

$("#submit").click(function() {
	var valid = validEditCourse(); 
	if (valid) {
		// do stuff
	}
});