package json;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;  //need this so we dont use web xml to register servlet
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;  //replaced above so session context will work
import javax.servlet.http.*; //replaced above so session context will work

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

import javax.servlet.annotation.WebServlet;


@WebServlet("/AjaxHandler4")   //need web.xml for cors policy..register this servlet under cors
public class AjaxHandler4 extends HttpServlet {
private static final long serialVersionUID = 1L;
Connection cn = null;
HttpSession session;
String session2;
PrintWriter out;

    
public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	response.setContentType("text/html");  
	//response.setContentType("application/json");  //put back to text/html so file open does not occur
	response.setCharacterEncoding("UTF-8");
	out = response.getWriter();
	
                    //use doPost to get param and return it to html . refer to pass_variable.html in files folder
	         //changed to service method, dbase is a post but send redirect is a get
	         //redirect blocked, no cors for requested resource
	                
	                //then enable CORS in servlet or in alternative do this in web xml if we r using it
	 ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
     ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
 	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
 	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
		
 	session2 = request.getSession().getId();
 	System.out.println(session2);
 	//ServletContext sc = getServletContext();  //this syntax works , use later
 	//sc.setAttribute("sessionKey",session2);   //use later
 	                                            //also missing try and catch block 
 	
 	 out.print(session2);  //can also pass session as string
	  //out.print(jsonObject);
		out.flush();
		
	
	 //create new string to get first var from js data pair
  //String name = request.getParameter("name");    //pulls session from pass variable 1.html, now using session2 below
  //String name11 = request.getParameter("name1"); //pulls val = 1, then send to db as a string
		String name11 = request.getParameter("name111");
  
 // String page22 = request.getParameter("page2");
  //String city22 = request.getParameter("city2");
  //String state22 = request.getParameter("state2");
  //String country22 = request.getParameter("country2");
  //String eu22 = request.getParameter("eu2");
  //response.getWriter().print("Hello "+ name + "!!");
		
		
  //System.out.println(name);  //replaced by session 2, this still pulls from jquery
  System.out.println(name11);
  
  
  //System.out.println(city22);
  //System.out.println(state22);
  //System.out.println(country22);
  //System.out.println(eu22);
  
  LocalDate currentDate = LocalDate.now();   //java 8 and above only
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-dd-yyyy"); //using LLLL vs MMMM will show month as number
  String formattedDate = currentDate.format(formatter);
  //System.out.println(formattedDate);
  LocalTime now = LocalTime.now();
  String time = now.toString();
  
  //loadDriver();       //connect to sqlite
  
  //String SQL_QUERY = "insert into book5(region,plant_name) values(?,?)";
  
  /*
  try {
	  //  Class.forName(driverName);
	   // Connection con = DriverManager.getConnection(URL, uname, pass);
	    PreparedStatement pst = cn.prepareStatement(SQL_QUERY);
	    pst.setString(1, name); //old var was 'name' pulled from jquery php session
	    pst.setString(2, name11);
	    //pst.setString(3, formattedDate);
	    //pst.setString(4, page22);
	    //pst.setString(5, country22);
	    //pst.setString(6, state22);
	    //pst.setString(7, city22);
	    //pst.setString(8, eu22);
	    //pst.setString(9, time);
	    
	   int rowCount = pst.executeUpdate();
	   
	 cn.close();
	 
	 
	 } catch (Exception e) {
	 e.printStackTrace();
	 }
  
  */
        //cors problem with below
  //response.sendRedirect("https://cloudwithasilverlining.com");
  //response.sendRedirect("http://3.18.106.249");
  //response.sendRedirect("http://3.18.106.249:8080/Frontend_Javascript_Connection/Ajaxhandler3");
  
}

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
		   //cn = DriverManager.getConnection("jdbc:sqlite:test2.db");
			cn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rickd\\desktop\\SQLite\\test.db");
			System.out.println("Connection to Server was Established");
	        } catch (Exception E2) {
			System.out.println(" Exceptions : " + E2.toString());
	        }
		

	
	} //end load driver method

}