//all methods consolidated to this one file

package json;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.annotation.WebServlet;  //commented out because i now have web.xml
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;  //to get cookie from session
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.PrintWriter;  

import javax.servlet.http.HttpSession;


@WebServlet("/StartScript")
public class StartScript extends HttpServlet {
	
	//private static final Logger LOG = Logger.getLogger(CarService.class.getName());
//    public static JSONArray jarray;
    public static String name2;  
    static String data;
    public static String name23;
    public static String session2;
    
   
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	         //set cors policy..or can be done in web xml..doing here means we can use annotation instead of web xml
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
    	
    	HttpSession session = request.getSession(); //will always get new session id
    	System.out.println("this is my session id: "+request.getSession().getId());
    	      //to use session id we need front end at ec2 apache hitting ec2 tomcat..with allow origin as 8071
    	       //front end at desktop/files is showing with connection refused..how did this work on 6-13?
    	     //this is not working
    	if (request.getSession() != null) {
    		 //looks like it will always be not null
    	//if (request.getParameter("JSESSIONID") != null) {
    		System.out.println("is not null: "+request.getSession().getId());
    		session2 = request.getSession().getId();
    	    Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
    	   // response.addCookie(userCookie);
    	} else {
    	    String sessionId = session.getId();
    	    Cookie userCookie = new Cookie("JSESSIONID", sessionId);
    	   // response.addCookie(userCookie);
    	    System.out.println("is null: "+request.getSession().getId());
    	}
    	
    	    //looks like if we store old id in dbase then try to do response.add cookie
    	if (request.getSession().getId().equals(session2)) {
    		System.out.println("session same "+request.getSession().getId());
    	}
    	else {
    		System.out.println("session changed "+request.getSession().getId());
    	}
    	
    	name23 = (String) session.getAttribute("yourDataKey");
    	
    	
    	
    	
    	
    	
    	
		   //get param from jquery, service method knows it is a post, return this resp as success message
        name2 = request.getParameter("name1");
    	//name2 = request.getParameter("responseText");
		//name = "select * from recruiters";
        System.out.println("name2 is "+name2);
		System.out.println("orig session object "+name23);
					
        //response.setContentType("application/json");  //gets response object in json format similar to opening excel
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

       // JSONArray ar = CarService.getCarsJSON();
        JSONArray ar = getCarsJSON();

        response.getWriter().write(ar.toJSONString());  //gets resp object, writes in temp space and offers to open
    }  //end of doGet

  
       
    
    public static JSONArray getCarsJSON() {

    	
    	JSONArray jarray=new JSONArray();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
        	
        	System.out.println("other "+name2);
        	System.out.println("persist "+name23);
        	System.out.println("session id persisted "+session2);
        	
        	Connection_class connection2 = new Connection_class();        	
            con = connection2.getConnection();  //this is now the sub class..Connection_class is parent
                 //can i select from book5 instead
            //pst = con.prepareStatement("SELECT NAME, PRICE FROM Cars");   //create cars table in sqlite
            //pst = con.prepareStatement("SELECT * from customers");
           
           // String sql = "select * from recruiters";
            String sql = name23;    //pass request param from session to query        
           // pst = con.prepareStatement(sql);
           // pst = con.prepareStatement("SELECT * from recruiters");
            //pst = con.prepareStatement("SELECT * book5");
            pst = con.prepareStatement("insert into book5 values 'aa', 'bb'");
            
            // pst = con.prepareStatement("SELECT * from recruiters");  //also book5
            rs = pst.executeQuery();

            
            
   //needs to be in separate servlet returning stringbuffer object as response over to jquery, update col model
                 //because we pass only 1 response object per servlet                  
            
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            
         // get the column names; column indexes start from 1
            for (int i = 1; i < numberOfColumns + 1; i++) {
              String columnName = rsMetaData.getColumnName(i);
              // Get the name of the column's table name
              //String tableName = rsMetaData.getTableName(i);
              System.out.println("column name=" + columnName);
            }
            
            
            
            //jarray = SimpleJsonUtils.toJSON(rs);
            jarray = toJSON(rs);
            

        } catch (SQLException ex) {

         //   LOG.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                
           //     LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return jarray;
    } //end of get cars method
   
    
    public  static  JSONArray   toJSON( ResultSet rs )
    	     throws SQLException
    	 {
    	     ResultSetMetaData   rsmd = rs.getMetaData();
    	     int                 columnCount = rsmd.getColumnCount();
    	     JSONArray           result = new JSONArray();

    	     try {
    	         while( rs.next() )
    	         {
    	             JSONObject  row = new JSONObject();

    	             for ( int i = 1; i <= columnCount; i++ )
    	             {
    	                 String  keyName = rsmd.getColumnName( i );
    	                 Object  value = getLegalJsonValue( rs.getObject( i ) );

    	                 row.put( keyName, value );
    	             }

    	             result.add( row );
    	         }
    	     }
    	     finally
    	     {
    	         if ( rs != null )
    	         {
    	             rs.close();
    	         }
    	     }

    	     return result;
    	 }     //end of to json method

 
    private static  Object  getLegalJsonValue( Object obj )
    	     throws SQLException
    	 {
    	     if (
    	         (obj == null) ||
    	         (obj instanceof Long) ||
    	         (obj instanceof Double) ||
    	         (obj instanceof Boolean) ||
    	         (obj instanceof String) ||
    	         (obj instanceof JSONObject) ||
    	         (obj instanceof JSONArray)
    	         )
    	     {
    	         return obj;
    	     }
    	     // other exact integers
    	     else if (
    	              (obj instanceof Byte) ||
    	              (obj instanceof Short) ||
    	              (obj instanceof Integer)
    	              )
    	     {
    	         return ((Number) obj).longValue();
    	     }
    	     // all other numbers, including BigDecimal
    	     else if (obj instanceof Number) { return ((Number) obj).doubleValue(); }
    	     else if (obj instanceof Clob)
    	     {
    	         Clob    clob = (Clob) obj;
    	         return clob.getSubString( 1, (int) clob.length() );
    	     }
    	     else if (obj instanceof Blob)
    	     {
    	         Blob    blob = (Blob) obj;
    	         //return formatBytes( blob.getBytes( 1, (int) blob.length() ) );
    	     }
    	     if (obj instanceof byte[])
    	     {
    	         //return formatBytes( (byte[]) obj );
    	     }
    	     // catch-all
    	     else { return obj.toString(); 
    	     }
    	     
    	     return obj;  //needed to add this in
    	 }      //end of legal method


    
    
}