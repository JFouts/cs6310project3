package arms.com;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Course;

public class CatalogServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

    	super.doGet(request, response);
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	List<Course> courseList;
		try {
			courseList = api.getCatalog();
			request.setAttribute("courseList", courseList);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		} 
    	
		request.getRequestDispatcher("WEB-INF/Catalog.jsp").forward(request, response);
    }
}
