function loadSummaryPage(divID) {
	highlightSidebarDiv(divID);
	populateTotalStudentCount();
	populateTotalRequestCount();
}

function populateTotalStudentCount() {
	var studentCountElement = $('#totalStudentCount');
	studentCountElement.text("45");
}

function populateTotalRequestCount() {
	var totalRequestElement = $('#totalRequestCount');
	totalRequestElement.text("100");
}