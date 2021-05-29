/**
 * read header and content of tab delimited file provided by upload.jsp and upload servlet
 * create 2 string buffers, append as needed and then loop through to construct a create table query
 * and an insert query to load data into mysql db
 * 
 * 
 */


import javax.servlet.*;
import javax.servlet.http.*;
//import java.io.*;
import java.util.*;
//import java.sql.*;
import java.lang.*;  // use this for Comparable object
import java.net.URISyntaxException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Date;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class CreateTable3 extends HttpServlet {

    Connection cn = null;
	String table_name ="table";     
    String concat;  //this is the final concatanated table name  incremented by 1 each time
	PreparedStatement dd2;
	String next;
	String filename;    //string value for passed file name from previous upload servlet	
    String buf1,buf2=null; //buf1 create table, buf2 is insert code
    boolean aa;  //default val is false
  
    int total=0;   //total items in elements array  
    PrintWriter out;
  
  //Service the request
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	  
	  out = new PrintWriter (response.getOutputStream());	  
	  response.setContentType("text/html");
	  
//get filename attribute from upload servlet..ie get file name passed...and below we also set table name..using session object twice	  
	  
	  HttpSession session = request.getSession();
	   filename = (String) session.getAttribute("DataKey");
	     
    
    try{

    	 loadDriver();       //connect to mysql
    	 get_next_number();  //auto increment table name each time table is created
         connect();          //create and insert data
         
    
 } //end of try block
      
      catch (Exception e) {
         throw new ServletException("Exception in XLS  Servlet", e);
     } 
    
   
    
		System.out.println(concat);  //this is the concatenated table name created below
		
		String filename2 = concat;
		        //pass concatenated table name to form 2 or we can bypass form 2 and go directly to view table
		session = request.getSession();
        session.setAttribute("DataKey",filename2 );  //using same session pass concat table name now called filename2
        
        
        out.println("<html><head><title>Create Table Example</title>");
        out.println("</head>"); 

        out.println("<body>");
                  out.println("<div name='name1' id='search' style='float: left'>"); //need to use float on both divs for side by side
                  out.println("<td width='400'>");                  
                  out.println("<br>");
        out.println("<form action='http://ec2-52-15-160-113.us-east-2.compute.amazonaws.com:8080/UploadServletApp/FormTwo' method='Post' name='formname' target='receiver2'>");
        out.println("<tr><td align='right'><input type='submit' value='submit' >");
        out.println("<br><p>press submit button and then see Form2 tab to view data</p>");
        out.println("</form>");
        
        
        out.println("</body></html>");
		   out.close();
		
    
   } //end of service method


public void connect(){
	
	
	          //pull vals from concat string created in get next number method
	StringBuffer output = new StringBuffer("CREATE TABLE "+concat+"("); 
	StringBuffer output1 = new StringBuffer (" values (");
	
	  System.out.println("this is the file name "+filename);
	         //create the whole path plus file name
	  String dataFileName = "c:/users/administrator/workspace/UploadServletApp/WebContent/"+filename+""; 
	  
	  
     
                   //first read line 1 only from file
 try{
   String line;
   BufferedReader bReader = new BufferedReader(new FileReader(dataFileName)); 
   
	line = bReader.readLine();  // looks like reader knows to stop at last item... be sure sheet range is specifically set
       //create string array called elements
String[] elements = line.split("\t");  // a string array of elements parsed/separated/delimited by tabs

System.out.println("number of elements= "+elements.length); //count array elements up front


             //setting up create table statement based only on first row of file
   int count=0;
   total=elements.length; //number of columns or elements in first row

   for(count=0; count < 100; count++){    // max cols is 100
	                                    //this executes until cols in first row are finished
  
String value = elements[count]; // get value of each element in array, it counts 0,1,2 & 3.but knows that there is a last val
//gets val at position 0, then position 1.....et until it reaches end of first line
String col_name = (value);  //grab whatever vals user has in first row for cols..with max = 100
   

                      //append each val to buffers as they get picked up in for loop

output.append(col_name);  //add each item to string buffer as it is created
output1.append("?");
output.append (" text NULL");

           //also start buf2 here for insert statement which uses same col names from header
if (count==total-1) {
	output.append (" )"); //total elements -1 because we start at 0, dont put comma on last element
	output1.append (" )");
	
buf1 = output.toString();
buf2 = output1.toString();
System.out.println(buf1);
System.out.println("buf2= "+buf2);

}   else {
	output.append (","); //insert comma for each col until we reach last element
	output1.append (",");
         }

System.out.println(col_name);
  
                          }  //end for loop

	}
	catch(Exception e){	}

 
   System.out.println(buf1);  //when finished reading we completed buffer with create syntax for the table of n columns
   
                        //then create table
   try{
	   Statement stmt5 = cn.createStatement();
	      //use whatever final buf 1 is in sql syntax...pass buf1 into statement, then execute to create table in sqlite
	   stmt5.executeUpdate(buf1);  
	   stmt5.close();
	   
	   
	   
	           //now after creating table create insert dialog...read rest of table and insert data into table
	  try{ 
	   	  BufferedReader in = new BufferedReader( new FileReader(dataFileName));
       String line2 = in.readLine();
       
       while ((line2 = in.readLine()) != null) {  //....this is the outer loop, reads each line
    	   
    		  String[] columns = line2.split("\t");	  //set up string array to first read vals and then below to insert vals
    		  int i=0;
    		  String col;
    		
    		          //for col count found above get parsed/split vals...this is the inner loop
    		       for (i=0; i <total; i++){   //grab this val from above col count total
    		            
    	               col = columns[i];  //gets col vals for each row, based on val of i    	      
    	               System.out.println(col);
    	       	     
    	               } //end inner for loop, all vals stored in session, then below grab all to insert
    		
    		                        //now insert col vals into table for each line read
    		  System.out.println(buf2);
                             //turn this into an expandable buffer..for cols accept whatever names are assigned	  
    		  dd2 = cn.prepareStatement("insert into "+concat+"" +buf2);
		    	      for(int i2=0; i2<total; i2++) {
		                dd2.setString(i2+1, columns[i2]); //for how many ever cols insert string val of columns array
		                                            }
		    	dd2.executeUpdate();
		    	    
    	 } //end while loop for insert dialog - outer loop
   
       
		      
          //clean out buffers and close vars for next use..instead use new table each time...had trouble closing out vars
       output.setLength(0); output1.setLength(0); dd2.close();
  // 	buf1=null; buf2=null;  initialize all strings and vars at beg to null so we clear out previous buffers..start fresh
       
       
       
       //also insert current user data into downloads db to keep track of user history       
       
       PreparedStatement ps2 = cn.prepareStatement("insert into downloads3 (file_name, table_name, stamp) values (?,?,?) ");
       
       java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis()); //instantiate new instance of date
       java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime()); //use sql date as import and not util.date
  	    ps2.setString(1,filename);
    	ps2.setString(2,concat);     
    	ps2.setDate(3,(java.sql.Date) date); //stores datetime in milliseconds, using calendar instance above    	
    	
  	 ps2.executeUpdate();
       ps2.close();
       
             
	  }
		catch(Exception e2){  //find language to print out error e2
			
		} 
   }
	catch(SQLException e){
		
	                     } 
      //close connection to db
try{
	//buf1=null; buf2=null;  //close out vars
   cn.close();
   
   }
catch(SQLException e){
	
} 
  
   
             } //end connect and create method


public void loadDriver() {

  String url = "jdbc:mysql://127.0.0.1:3306/ricks_schema";
  String username = "root";           
  String password = "unh12806"; 

	try {
	  //Class.forName("org.gjt.mm.mysql.Driver");
          //Class.forName("com.mysql.jdbc.Driver").newInstance();
           Class.forName("com.mysql.cj.jdbc.Driver");   //8.0 driver jar path
	 // Class.forName("org.sqlite.JDBC");
	  System.out.println("The Driver has been loaded successfully!");
	}   catch (Exception E1) {
          System.out.println("Unable to load the Driver!");
	  System.out.println("Exceptions:" + E1.toString());
	  System.exit(1);
	}

	System.out.println("Establishing connection to Server");
	
	
 //Establishing the connection with the database
		
	try {
	   cn = DriverManager.getConnection(url,username,password);
	   //cn = DriverManager.getConnection("jdbc:sqlite:test.db");
		System.out.println("Connection to Server was Established");
        } catch (Exception E2) {
		System.out.println(" Exceptions : " + E2.toString());
        }
	
	
} //end load method

public void get_next_number(){
	               //this value appends to table...table1, table2, table3 etc
	try {
		
	PreparedStatement ps = cn.prepareStatement("select next_val from GetNextVal where name = 'name1'");
	           // ps.setString(1,"name1");
	ResultSet rt =ps.executeQuery();
    while(rt.next()) {
  	      next =  rt.getString("next_val");
  	
                     } //end result set 

    rt.close();
    ps.close();
    
    //do math..increment next_val from table by 1, for each pass table name is unique and auto incremented
        	Integer d1;		   
        	d1=new Integer(next);
        	Integer increment=new Integer(d1.intValue()+1);
        	String newcalc= increment.toString();
        	
        	System.out.println("incremented val= "+newcalc);
        	        	
            // then increment table name here and use new val for table name in create table method above
        	String append_to_a = increment.toString();
        	concat =table_name+append_to_a;
        	System.out.println("incremented table name= "+concat);

        	//then update table with newcalc val..for next pass to new table number
        	//because am not able to set multiple session attributes        	
        	
        	 PreparedStatement ps1 = cn.prepareStatement("update GetNextVal set next_val=? where name='name1'");
        	 ps1.setString(1,newcalc);
        	 ps1.executeUpdate();
                 ps1.close();
            
	
}
	catch(SQLException e){	//replace this after test
//catch(Exception e){
	System.out.println(" Exception : " + e.toString());
} 
	
} //end next number method


	
}




