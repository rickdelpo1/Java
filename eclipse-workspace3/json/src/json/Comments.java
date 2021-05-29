package json;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.sql.*;



/**
 * Servlet implementation class Comments
 */
@WebServlet("/Comments") //might have to use web.xml in standalone tomcat
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	  Connection cn = null;
	  String receive_param_from_form1;
	  String session2;
	  PrintWriter out;


       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
    }
	

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//servlet returns a response object and js picks it up in success function by getting the servlet response object, js posts and returns 1 get with post method
response.setContentType("text/html");  
	response.setCharacterEncoding("UTF-8");
	out = response.getWriter();

String response2 = "thanks for ur input..if u want an answer please resend with an email address";
	//session2 = request.getSession().getId(); //or create new var with message to send back to js
 	         //also missing try and catch block , do we need it?
 	
 	 out.print(response2);  //can also pass session as string, pass string to response writer object which then goes to success function in js
	 	out.flush();


   loadDriver();
   
	  receive_param_from_form1 = request.getParameter("message");   //get feedback comments from form 1

   //receive_param_from_form1 = "hi"; //test on db, put back to actual param
  
   //get current date
   LocalDate currentDate = LocalDate.now();
   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-dd-yyyy");
   String formattedString = currentDate.format(formatter);
   
   
   //update forms_example table with user vals
   try{
   PreparedStatement ps = cn.prepareStatement("insert into book5 (region, plant_name) values (?,?) ");
   ps.setString(1,formattedString);  //put current date into name col
   ps.setString(2,receive_param_from_form1);  //pass comments into field 2 of forms_example db
   ps.executeUpdate();
   ps.close();
	
   }
   catch(SQLException e) {   }  
   
   System.out.println("comments have been added");   
   
//disconnect from the database
   try {
    if(cn != null) {
 cn.close();
 cn = null;
   }
}
catch(SQLException e) {   }

}  //end service method
  
  
  public void loadDriver() {
		try {
		  //Class.forName("org.gjt.mm.mysql.Driver");
		  Class.forName("org.sqlite.JDBC");
		  System.out.println("The Driver has been loaded successfully!");
		}   catch (Exception E1) {
	          System.out.println("Unable to load the Driver!");
		  System.out.println("Exceptions:" + E1.toString());
		  System.exit(1);
		}

		System.out.println("Establishing connection to Server");
		
		
	 //Establishing the connection with the database
			
		try {
		   //cn = DriverManager.getConnection(url,username,password);
		   //cn = DriverManager.getConnection("jdbc:sqlite:test.db");
		   cn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rickd\\desktop\\SQLite\\test.db");
			System.out.println("Connection to Server was Established");
	        } catch (Exception E2) {
			System.out.println(" Exceptions : " + E2.toString());
	        }
		
		//get_next_number();
		
	} //end method




	

}





