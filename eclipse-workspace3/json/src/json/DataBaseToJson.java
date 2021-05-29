package json;



/*
 * object is to capture current session and write it back to the page, then post unique session to timeOut servlet
 * looks like we need to add 2 more ajax calls to the page, one to get and one to post
 *      see sample11.js for this get query
 * after creating java session in ajaxhandler 3 set attribute then grab session attribute here
 * then pass session to response object so we can pick it up with jquery
 * do a get ajax request in page 1 to bring this session id into the page
 * then create var to hold session...i think this page will always hold its own unique session
 * then for update servlet pass this session into update with ajax post so update knows it is from this page
 * then finally use this session in where clause, update where session = this one not the most recent
 * 
 * 
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

import java.io.FileWriter;
import java.io.PrintWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;


@WebServlet("/DataBaseToJson")   //need web.xml for cors policy..register this servlet under cors
public class DataBaseToJson extends HttpServlet {
private static final long serialVersionUID = 1L;
Connection cn2 = null;   //using separate connection object so same connection does not conflict or cause db to lock
String session3;
PrintWriter out;
    
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");

	
	
	try {
	response.setContentType("text/html");  
	//response.setContentType("application/json");  //put back to text/html so file open does not occur
	response.setCharacterEncoding("UTF-8");
	out = response.getWriter();
	
	
	//need response to carry the json object, so instead of filewriter use
	
	/*
	Write the JSON object to the response object's output stream.

	You should also set the content type as follows, which will specify what you are returning:

	response.setContentType("application/json");
	// Get the printwriter object from response to write the required json object to the output stream      
	PrintWriter out = response.getWriter();
	// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	out.print(jsonObject);
	out.flush();
	*/
	
                   
  
  System.out.println("we are in the json servlet");
       
  ServletContext sc = getServletContext();
        //passes attribute correctly using servlet context but not with request, also I changed imports
        //context is the application scope and request method is the request scope
  //session3 = (String) sc.getAttribute("sessionKey");  //getting attribute from context scope
  session3 = request.getSession().getId();
  
  System.out.println(session3);
  
}

catch(Exception exp){
    System.out.println(exp);
 }

  
  loadDriver();       //connect to sqlite
  
  //String SQL_QUERY = "insert into book5 values(?,?)";
  //String SQL_QUERY = "update book5 set plant_name='8xxx' where region = ?";
  String SQL_QUERY = "select * from book5";
  
  
  
  try {
	    PreparedStatement pst = cn2.prepareStatement(SQL_QUERY);
	    
	    
	  //Creating a JSONObject object
	    /*
	      JSONObject jsonObject = new JSONObject();
	      //Creating a json array
	      JSONArray array = new JSONArray();
	      ResultSet rs =pst.executeQuery();
	      //ResultSet rs = RetrieveData();
	      //Inserting ResutlSet data into the json object
	      while(rs.next()) {
	         JSONObject record = new JSONObject();
	         //Inserting key-value pairs into the json object
	         record.put("region", rs.getInt("region"));
	         record.put("plant_name", rs.getString("plant_name"));
	         //record.put("Last_Name", rs.getString("Last_Name"));
	         //record.put("Date_Of_Birth", rs.getDate("Date_Of_Birth"));
	         //record.put("Place_Of_Birth", rs.getString("Place_Of_Birth"));
	         //record.put("Country", rs.getString("Country"));
	         array.add(record);
	      }
	      jsonObject.put("Players_data", array);
	      */
	      
	      
	             //use gson then pass parsed session string to response object below
	                    //dont forget to change response content type above
	      //String employeeJsonString = new Gson().toJson(session3);
	      
	      
	      
	      //try {
	
	     
	      //out.print(employeeJsonString); //with gson
	      out.print(session3);  //can also pass session as string
	    	  //out.print(jsonObject);
	    		out.flush();
	    		
	    	        //writing to file only puts quotes around session when in json
	    	// FileWriter file = new FileWriter("C:/sqlite/output.json");
	         //file.write(jsonObject.toJSONString());
	    	// file.write(employeeJsonString);
	    	// file.close();
	     // } catch (IOException e) {
	         // TODO Auto-generated catch block
	       //  e.printStackTrace();
	     // }
	      System.out.println("JSON file created......");
	  // }
	//}
	//Output
	//JSON file created......
	    
	    
	    
	    //pst.setString(1, session3); //gets original session passed thru attribute, this is getting only most recent session if multiple users are on at same time
	    //pst.setString(2, name11);
	   // pst.setString(2, city);
	   //int rowCount = pst.executeUpdate();
	   
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









