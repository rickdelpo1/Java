package json;

//originates from page3 in xamp

//this is the time out servlet..when user is about to exit a page, substitute for php session cookie
      //db gets his timeout and updates record based on unique servlet session id created on time in page
     //but if second user (at the same time) updates the attribute then first user timeout may not be recorded
         // and most likely will update the second person instead of the first person..we can live with this for now

//this servlet get hit when user presses back button or x's out of page, originated from jquery ajax beforeunload function
//originally had problem with get attribute not picking up set but changed imports and used context application scope
  //i think that with forms request scope is ok to work but our servlets are not connected by form so hence application scope



/*
         attribute rules
Request Scope: are most useful when processing the results of a submitted form.
 With request scope you are also guaranteed that no other request will be able to affect your objects
  in that request scope while your request is being handled. This scope has the shortest lifspan.
  
Session Scope: is associated with a user. When a user visits your web application, a session is created by the web container.
 So the session’s lifespan lives as long as the user interacts with your application or when session.invalidate() is called.
      --we dont need whole session like in PHP, just a unique id for each page view until time out is registered in db
Application Scope: or global scope is associated with your web application.
 This scope lives as long as the web application is deployed.
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
import javax.servlet.*;  //used this  to work with context scope
import javax.servlet.http.*; //used this  to work with context scope, session not working with above imports


@WebServlet("/Update")   //need web.xml for cors policy..register this servlet under cors
public class Update extends HttpServlet {
private static final long serialVersionUID = 1L;
Connection cn2 = null;   //using separate connection object so same connection does not conflict or cause db to lock
String session3;
    
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
	/*
	conditions for exiting program. First select count on how many records have timeout field as none.
	 This will show how many users have not signed out yet. Use if logic to narrow down which record to time out.
	           above seems it will work since we are already inserting session id into tracker table
	Then find out what page the person is on. If more than 1 person is on the same page then we need to get
	 their session id. To get the page, set a flag on the js page in the beforeunload script to capture page number
	 to be used in this servlet. For each php page we might consider having a different servlet or a different
	 attribute name, preferably a different attribute name.
	 
	 
	 2-1-20 try appending session id to url and then adding each to db, then if statement iterating thru all to find
	 a specific person/user
	 
	 I think easiest way is to store each session in db, pull array of users and then iterate thru each using if session
	 is x then get time for exiting. This is for multiple users at same time...otherwise right now timeout uses most recent
	 session id and there is no way of knowing anbout the other user in the que
	      just query into tracker table to find which records are not timed out yet as suggested above
	 
	*/
	try {
	response.setContentType("text/html");  
                    //use doPost to get param and return it to html . refer to pass_variable.html in files folder
	                
	                //then enable CORS in servlet or in alternative do this in web xml if we r using it
	 //((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
     //((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
 	//((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
 	//((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
	
  	//String name = request.getParameter("country");	
 // String name = request.getParameter("name");    //pulls user input from pass variable 1.html
  //String name11 = request.getParameter("name1"); //pulls val = 1, then send to db as a string
  
  //String city = request.getParameter("country");
   //System.out.println(name);
  //System.out.println(name11);
  System.out.println("we are in the update servlet");
       //this other syntax was not working, originally instead of request we used  session23=session.getSession
  //session23 = request.getSession(false); //must always start session in order to use session methods
  //session3 = (String) session23.getAttribute("sessionKey");
  //session3 = request.getAttribute("sessionKey");
  
  ServletContext sc = getServletContext();
        //passes attribute correctly using servlet context but not with request, also I changed imports
        //context is the application scope and request method is the request scope
  session3 = (String) sc.getAttribute("sessionKey");  //getting attribute from context scope
  
  System.out.println(session3);
  
}

catch(Exception exp){
    System.out.println(exp);
 }

  
  loadDriver();       //connect to sqlite
  
  //String SQL_QUERY = "insert into book5 values(?,?)";
  String SQL_QUERY = "update book5 set plant_name='8xxx' where region = ?";
             // setting timeout = get time where session = session from previous attribute
  
  /*
   * or query tracker where timeout is null and then in result set do if logic
   *      select session id from tracker where timeout is null
   *      while in rs iterate thru each session id with if logic
   *         string sess = response .get session id
   *         if session3 = sess (get session val from each iteration) then grab time out and new query to update
   * based on if session id is this one or that one then grab time out
   */
  
  
  try {
	    PreparedStatement pst = cn2.prepareStatement(SQL_QUERY);
	    pst.setString(1, session3); //gets original session passed thru attribute, this is getting only most recent session if multiple users are on at same time
	    //pst.setString(2, name11);
	   // pst.setString(2, city);
	   int rowCount = pst.executeUpdate();
	   
	 cn2.close(); //is this definately the correct syntax or do we need finally
	 } catch (Exception e) {
	 e.printStackTrace();
	 }
  
  System.out.println("updated");
  
  
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
	   cn2 = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rickd\\desktop\\SQLite\\test.db");
	   System.out.println("Connection to Server was Established");
	    } catch (Exception E2) {
		System.out.println(" Exceptions : " + E2.toString());
	    }


	} //end load driver method

}