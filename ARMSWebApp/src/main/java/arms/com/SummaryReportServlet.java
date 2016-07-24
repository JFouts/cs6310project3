package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.SummaryReport;

public class SummaryReportServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
        
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	SummaryReport report;
		try {
			report = api.generateSummaryReport();
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
    	request.setAttribute("report", report);
    	    	
		request.getRequestDispatcher("WEB-INF/SummaryReport.jsp").forward(request, response);
    }
}
