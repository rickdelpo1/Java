package json;


import java.sql.Connection;
import java.sql.DriverManager;

public class Connection_class {
	
	//public Connection_class(Connection con2) {
	//public Connection_class() {
		
	    //constructor with one arg which is a connection object called con2
		      //passes by reference and not name so connection is passed to other class not con2
		
		//do i need connection method inside this constructor since it is being called in sub class
		
		//also might be ok with empty constructor because i am not passing anything
		    //class assumes empty constructor if empty..so constructor not needed
	//}
	
	
	
	Connection cn = null;
                //get connection method returns a connection object
	               //might be able to get away with void because we r only using the method of this class
	                   //sub class will not take a void but will take an empty class constructor
	public Connection getConnection() {
    	//public void getConnection() {	
	
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
			   //cn = DriverManager.getConnection("jdbc:sqlite:test.db");    //default is now desktop..needed to create to find
				cn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rickd\\desktop\\SQLite\\test.db");
				        //need fully qualified path
				
			    System.out.println("Connection to Server was Established");
		        } catch (Exception E2) {
				System.out.println(" Exceptions : " + E2.toString());
		        }
			

	  	return cn;    //must have return here.....method returns connection object called cn
		
		
	}
	
	
	
}
