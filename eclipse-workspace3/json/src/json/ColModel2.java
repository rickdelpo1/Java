package json;

import java.io.FileWriter;
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

//import org.json.simple.JSONObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import java.io.PrintWriter; 

//import com.google.gson.Gson;  //needed to get array list to json string



/**
 * Servlet implementation class ColModel
 * pass RS meta data column names to json string and writer, then ajax call from JQuery to this response object
 * 
 */
@WebServlet("/ColModel2")   //set web.xml via annotation
public class ColModel2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//JSONObject jo = new JSONObject();
	JSONArray ja = new JSONArray();
	
	//JSONObject jo = null;
	//JSONArray ja = null;
	
	
    Connection cn = null;
    ArrayList<String> myList;
    PrintWriter out;
   
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
		//JSONArray jsonArray = new JSONArray();
		//response.getWriter().write(array.toJSONString());
		
		     //get print writer
		//out = response.getWriter();
		    
		 
		try{
                    //change this to connection class
			 loadDriver();       //connect to sqlite
			 
			 view_data();
			     //using GSON library to convert array list to json string
			// Gson gson = new Gson();  //added jar file to web inf lib for runtime to find gson class
			// gson.toJson(myList, response.getWriter());  //pass json string to Writer object
                     //do another servlet to write response object for col model in js
		
			 //JSONArray jsonArray2 = new JSONArray();
			 //JSONArray jsonArray = new JSONArray();
			 //response.getWriter().write(jsonArray.toJSONString());
			 
			 
			 //response.getWriter().write(jo.toJSONString());
			 //PrintWriter out;
			 
			 response.getWriter().write(ja.toJSONString());
			 //Gson gson = new Gson();  //added jar file to web inf lib for runtime to find gson class
			 //gson.toJson(ja, response.getWriter());  //pass json string to Writer object
             
			 ja = new JSONArray(); //reset array so each load only has 2 elements
			 
		} //end of try block
		  
		  catch (Exception e) {
		     throw new ServletException("Exception in XLS  Servlet", e);
		 } 
		
		
	}  //end doGet method
	
	public void view_data(){

	     try{
	    	        //need to be able to get select statement automatically
	    	 //PreparedStatement view = cn.prepareStatement("select CustomerID as cust from customers");
	    	 PreparedStatement view = cn.prepareStatement("select * from book5");
	    	
	 ResultSet rs =view.executeQuery();

	//using meta data will return col name as whatever we name it, the column model, response = col names
		 ResultSetMetaData rsMetaData = rs.getMetaData();
		 int numberOfColumns = rsMetaData.getColumnCount();
		 
		 
		 //String myArray[] = new String[numberOfColumns];
		// String myArray[] = new String[0];  //array list needed because u cannot add string elements directly to this array
		 //int counter=0;     //count number of passes
		 
		      //convert myArray to array list in order to add elements
		// myList = new ArrayList<String>(Arrays.asList(myArray));
		 
		// get the column names; column indexes start from 1
		 for (int i = 1; i <= numberOfColumns; i++) {
		   String columnName = rsMetaData.getColumnName(i);
		   
		  		   //myList.add("{ name : "+columnName+"}");
		   
		   //jo = new JSONObject();
		   JSONObject jo = new JSONObject(); //without this for each pass array does not append properly
		   //jo.put("Name", ""+columnName+"");
		   jo.put("name", columnName);   //getting rid of quotes made uncaught type error go away
		                                  //now we have length of col names <> col model
		                //***************SOLUTION name is case sensative, must start with small n
		   System.out.println(jo);
		   //myList.add(jo);
		   
		   //ja.add(jo.toString());   //shows escaped vals
		   ja.add(jo);      //this is not pulling all, only gets last object..email not first and email
		   //ja.put(jo);
		   
		   //String json = (new JSONArray(ja)).toString();
		   //JSONArray ja = new JSONArray();
		   
		   // ja.put(jo);  //.put does not work anymore after jdk7 .add is used
		   
		   System.out.println(ja);
		  // ja.put(jo.toString());
		   
		   //ja = new JSONArray();
		   
		        //trying to write to printwriter
		   //jo.writeJSONString(out);
		   //String jsonText = out.toString();
		   //System.out.print("jtext " +jsonText);
		   
		 }  //end for loop
		 
		 
		// System.out.println(ja);
      
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
