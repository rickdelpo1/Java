package json;
/**
note we are passing session id as an attribute because php passing is not working
and we do not want to use php session because of cookie issue
we will be starting a new session for each page hit
program is only measuring time spent on a current page and not total session of all pages by a user
php session would hold same session id for all pages of same user
but second servlet only needs the where element of the recent session set
   remember that each servlet visit creates new session id, but we only need the previous session id, hence using where clause
*/
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;  //need this so we dont use web xml to register servlet
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import javax.servlet.*;
import javax.servlet.http.*;


@WebServlet("/AjaxHandler1")   //need web.xml for cors policy..register this servlet under cors
public class AjaxHandler1 extends HttpServlet {
private static final long serialVersionUID = 1L;
Connection cn = null;
String session2;
HttpSession session;
String name11;
    
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                    //use doPost to get param and return it to html . refer to pass_variable.html in files folder
	                
	                //then enable CORS in servlet or in alternative do this in web xml if we r using it
	 ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
     ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
 	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
 	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
		
 	try {
 		
 		response.setContentType("text/html");
 	//session = request.getSession(); //will always get new session id
 	//System.out.println("servlet session id = "+request.getSession().getId());
	session2 = request.getSession().getId(); //this request method works..maybe changing imports was the trick
 	//session2 = "1111";
	     //pass session id to next servlet..this syntax not working
	//session.setAttribute("sessionKey",session2);
 	//request.setAttribute("sessionKey",session2);

 	ServletContext sc = getServletContext();  //this syntax works
 	sc.setAttribute("sessionKey",session2);
 	
 	//String name = request.getParameter("country");	
	//String name = "rick";
 // String name = request.getParameter("name");    //pulls user input from pass variable 1.html
  //name11 = request.getParameter("name1"); //pulls val = 1, then send to db as a string
     //name11= "delpo";  
	
  //String city = request.getParameter("country");
    // String city ="plainville";
  //response.getWriter().print("Hello "+ name + "!!");
  //System.out.println(name);
  //System.out.println(name11);
  
 	}
  
  catch(Exception exp){
      System.out.println(exp);
   }
 	
  
  loadDriver();       //connect to sqlite
  
  String SQL_QUERY = "insert into book5 values(?,?)";
  
  try {
	  //  Class.forName(driverName);
	   // Connection con = DriverManager.getConnection(URL, uname, pass);
	    PreparedStatement pst = cn.prepareStatement(SQL_QUERY);
	    //pst.setString(1, name);
	    pst.setString(1, session2);  //gets actual servlet session id, when new user comes we get new random id
	    pst.setString(2, name11);
	   // pst.setString(2, city);
	   int rowCount = pst.executeUpdate();
	   
	 cn.close();
	 } catch (Exception e) {
	 e.printStackTrace();
	 }
  
  
  
  
}

public void loadDriver() {
	try {
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
	  // cn = DriverManager.getConnection("jdbc:sqlite:test.db");    //default is now desktop..needed to create to find
	   cn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rickd\\desktop\\SQLite\\test.db");
	   System.out.println("Connection to Server was Established");
	    } catch (Exception E2) {
		System.out.println(" Exceptions : " + E2.toString());
	    }


	} //end load driver method

}