package json;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;  //needed to get array list to json string

/**
 * Servlet implementation class ColModel
 * pass RS meta data column names to json string and writer, then ajax call from JQuery to this response object
 * 
 */
@WebServlet("/ColModel")
public class ColModel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    Connection cn = null;
    ArrayList<String> myList;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
	        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
	    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
		
		       //response needs to be an array of items 
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		 // response.setContentType("text/html");
		 // out = response.getWriter();
		    
		 
		try{
                      //change this to share connection class also col model 2 does not use gson
			 loadDriver();       //connect to sqlite
			 
			 view_data();
			     //using GSON library to convert array list to json string
			 Gson gson = new Gson();  //added jar file to web inf lib for runtime to find gson class
			 gson.toJson(myList, response.getWriter());  //pass json string to Writer object
                     //do another servlet to write response object for col model in js
			 
			
		} //end of try block
		  
		  catch (Exception e) {
		     throw new ServletException("Exception in XLS  Servlet", e);
		 } 
		
		
	}  //end doGet method
	
	public void view_data(){

	     try{
	    	           //left off where we need to auto recognize the query here...right now recruiters query is hard coded
	    	                 //sample11.js now auto pulls responseText and responseText2 in startscript ajax query
	    	                 //program calls on col model and col model 2 to auto load from query received in AjaxHandler
	    	 //PreparedStatement view = cn.prepareStatement("select CustomerID as cust from customers");
	    	 PreparedStatement view = cn.prepareStatement("select * from book5");
	    	
	 ResultSet rs =view.executeQuery();
	 
	   //using meta data will return col name as whatever we name it, the column model, response = col names
	 ResultSetMetaData rsMetaData = rs.getMetaData();
	 int numberOfColumns = rsMetaData.getColumnCount();
	 
	 
	 //String myArray[] = new String[numberOfColumns];
	 String myArray[] = new String[0];  //array list needed because u cannot add string elements directly to this array
	 int counter=0;     //count number of passes
	 
	      //convert myArray to array list in order to add elements
	 myList = new ArrayList<String>(Arrays.asList(myArray));
	 
	// get the column names; column indexes start from 1
	 for (int i = 1; i <= numberOfColumns; i++) {
	   String columnName = rsMetaData.getColumnName(i);
	   // Get the name of the column's table name
	   //String tableName = rsMetaData.getTableName(i);
	   
	   //System.out.println("column name=" + columnName);
	   //System.out.println(numberOfColumns);
	   
	          //add logic to determine if we are at last col, then add closing brace
	   
	   counter++;  //stop process when counter = number of cols found in rs meta data
	     if (counter==numberOfColumns) {
			     
	    	// myList.add(""+columnName+"");  //add an element for each pass
	    	 myList.add(columnName);
		}   else {
			
		 	//myList.add(""+columnName+"");
			myList.add(columnName);
			     }
	    
	     
	 }  //end for loop
	 
	 	      
		     }  catch(Exception e){
		    
		    		System.out.println(" Exception : " + e.toString());
		    	} 

	     //then close connection
		   try{
		   cn.close();
		   } catch (SQLException E3) {
				System.out.println(" Exceptions : " + E3.toString());
		        }
	     
	} //end view data method

	

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
