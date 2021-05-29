//this program gets the table names into a result set, we can then pass into array

package json;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
//import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.ResultSet;

//import com.google.gson.Gson; //need this for parsing arraylist into json

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Servlet implementation class Tables
 */
@WebServlet("/Tables")
public class Tables extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	  Connection cn = null;
	  PrintWriter out;
	  String table_name;
	  ArrayList<String> myList;
	  
	  String aa;
	  String bb, cc;
	  
	  JSONArray ja = new JSONArray();  //stop using gson as we need a real json array with defined cols
	  
	  //int hitCount=0;

	

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//servlet returns a response object and js picks it up in success function by getting the servlet response object, js posts and returns 1 get with post method

		//response.setContentType("text/html");  //this works but use next to see the actual json array
         response.setContentType("application/json"); //use this to see the actual json
	     response.setCharacterEncoding("UTF-8");
	     
	  // Set refresh, autoload time as 5 seconds
	    //  response.setIntHeader("Refresh", 5);
           
         
         
	//out = response.getWriter();   //only use out for simple text, not an array
       
	//dont use out see below gson to json pass arraylist to response object
	     
	     
   loadDriver();
      
   
   try{
   //PreparedStatement ps = cn.prepareStatement("SELECT name FROM sqlite_master WHERE type='table'");
	   //PreparedStatement ps = cn.prepareStatement("SELECT * from book5");
	   //PreparedStatement ps = cn.prepareStatement("SELECT * from tracker");
	   
	   
	             //need to use an alias for aggregates, use the as condition which is an alias
	   PreparedStatement ps = cn.prepareStatement("select country,count(distinct ip) as ip, sum(hit) as hit from tracker where country is not null and ip != 0 group by country order by ip desc");
	  // PreparedStatement ps = cn.prepareStatement("select distinct country, ip from tracker where ip is not null");
	   
	  
    myList = new ArrayList<String>();  //instead of appending mylist for each pass put the actual objects in
   
    ResultSet rt =ps.executeQuery();
   while(rt.next()) {
	         //need a real json object vs a gson array
	   JSONObject jo = new JSONObject();
 	  
 	  /*
	   table_name = rt.getString("name");   //get meta data table names from sqlite master
 	
 	 myList.add(table_name); //add each to arraylist
 	 
 	  System.out.println(table_name);
 	  */
 	  
	//   aa= rt.getString("region");
	  // bb= rt.getString("plant_name");
	
	   aa= rt.getString("country");
	   bb= rt.getString("ip");
	   cc= rt.getString("hit");
	
	   
	   
	   //myList.add(aa);
	   //myList.add(bb);
	   
	   //jo.put("name", aa);
	   //jo.put("name", bb);
	       //looks like json array puts vals into defined col here, call these defined cols in ajax
	       //they are key pairs, a key and a value, a column name and its corresponding val
	   //jo.put("region", aa);
	   //jo.put("plant_name", bb);
	   
	   jo.put("country", aa);
	   jo.put("ip", bb);
	   jo.put("hit", cc);
	   
	   
	   ja.add(jo);
	   
	  // hitCount++;  //increment by 1
	   
 	  
 	  
 	  //pass table names to array for use elsewhere, populate html table in sql program
 	 //out.print(table_name); //can also pass to dynamic table object referred to after table is created and we hav FA icon
	  
	//   out.print(hitCount);
	   
   }
   
   //out.print(hitCount);
   //out.flush();   //only using this with basic text response not an array

      //use or do not use gson, gson good for plain arraylist with one column
 //Gson gson = new Gson();  //added jar file to web inf lib for runtime to find gson class
	//	 gson.toJson(myList, response.getWriter());  //pass arraylist to Writer object
       
		 response.getWriter().write(ja.toJSONString()); 
		 ja = new JSONArray(); //reset array so each load only has 2 elements
		 
		 
		 //format needs to be json object, an array of objects / key pairs
		 // [{"col1":"dddd","col2":"eeee"},{"col1":"dd","col2":"123"}]
   
   }
   catch(SQLException e) {   }  
   
   
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
		   cn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rickd\\desktop\\SQLite\\test2.db");
			System.out.println("Connection to Server was Established");
	        } catch (Exception E2) {
			System.out.println(" Exceptions : " + E2.toString());
	        }
		
		//get_next_number();
		
	} //end method
	

}



