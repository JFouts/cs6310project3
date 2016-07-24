function loadStudentSearchPage(divID) {
	highlightSidebarDiv(divID);	
}

function populateStudentForm(x) {
	document.getElementById("studentID").value = x.cells[0].textContent;
	document.getElementById("shadowButton").disabled = false;
	document.getElementById("hoursTaken").value = x.cells[1].textContent;
  }
		
  function validateShadowEnablement() {
  	  var studentID = $('#studentSelectList').find(":selected").text();
  
	if (studentID=="") {
	  $('#shadowButton').prop('disabled', true);
    } else {
    	$('#shadowButton').prop('disabled', false);
    }
  }
  
  function scheduleRequest() {
  	  var studentID = $('#studentSelectList').find(":selected").text();
    window.location.href = "schedulerequest.html?studentID="+studentID;
  }
  
  function deregisterStudent() {
  	  var studentID = $('#studentSelectList').find(":selected").text();
  	  if (studentID!="") {
	  	  if (confirm("Are sure that you would like to deregister Student "+studentID+"?")) {
	  	  	alert("Deregister in progress..");
	  	  }
  	  }
  }
  
  function registerStudent() {
     alert("Student added.");
  }