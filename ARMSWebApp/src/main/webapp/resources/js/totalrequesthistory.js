function loadTotalRequestsPage(divID) {
	highlightSidebarDiv(divID);
	populateTotalStudentCount();
	populateTotalRequestCount();
	
	$("#requestList tr").click(function(){
    	alert (this.rowIndex);
    });
}

function populateTotalStudentCount() {
	var studentCountElement = $('#totalStudentCount');
	studentCountElement.text("45");
}

function populateTotalRequestCount() {
	var totalRequestElement = $('#totalRequestCount');
	totalRequestElement.text("100");
}

function populateStudentList() {
	var studentList = $('#studentList');
}

function populateCourseList() {
	var courseList = $('#courseList');
}

function populateTable() {
	var studentListVal = $('#studentList').find(":selected").text();
	var courseListVal = $('#courseList').find(":selected").text();
	var scheduleRequestList;
	
	if (studentListVal!=null) {
		//make call to retrieve list filtered by student
	} else {
		if (courseListVal!=null) {
			//make call to retrieve list filtered by student		
		}
	}
}