package json;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Encode
 */
@WebServlet("/Encode")
public class Encode extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		   

        //SESSION ATTRIBUTE.

        String user2 = (String) request.getSession().getAttribute("user2");

        if    (user2 == null) { request.getSession().setAttribute("user2","John2");        }

        else                 { request.getSession().setAttribute("user",user2+"_extra"); }                

   

        //URL ENCODE.

        String servletURL = response.encodeURL("Encode");
        
        System.out.println(servletURL);

        

        //OUTPUT.

        response.getWriter().println("<body>user="+user2+"<br><a href="+servletURL+">MyServlet</a></body>");
        
        
        String session2 = request.getSession().getId();
        System.out.println(session2);

   

      } 
	
	
	
	
}
