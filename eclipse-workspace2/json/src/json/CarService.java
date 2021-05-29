package json;

//import com.zetcode.util.Utils;   //not using Utils to create connection, created my own class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;    //to get col names
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
//import org.apache.derby.optional.api.SimpleJsonUtils;     //already have this class in project
import org.json.simple.JSONArray;

public class CarService {

    private static final Logger LOG = Logger.getLogger(CarService.class.getName());
    private static JSONArray jarray;

    
 /*   
    public static void updateCar(String name, int price) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            DataSource ds = Utils.getDataSource();
            con = ds.getConnection();
            pst = con.prepareStatement("UPDATE CARS SET NAME=?, PRICE=? WHERE NAME=?");
            pst.setString(1, name);
            pst.setInt(2, price);
            pst.setString(3, name);
            pst.executeUpdate();

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CarService.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static void deleteCar(String name) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            DataSource ds = Utils.getDataSource();
            con = ds.getConnection();
            pst = con.prepareStatement("DELETE FROM CARS WHERE Name=?");
            pst.setString(1, name);
            pst.executeUpdate();

        } catch (SQLException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static void insertCar(String name, int price) {

        Connection con = null;
        PreparedStatement pst = null;

        try {

            DataSource ds = Utils.getDataSource();
            con = ds.getConnection();
            pst = con.prepareStatement("INSERT INTO CARS(NAME, PRICE) "
                    + "VALUES(?, ?)");
            pst.setString(1, name);
            pst.setInt(2, price);
            pst.executeUpdate();

        } catch (SQLException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                
                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    */
    
    
    public static JSONArray getCarsJSON() {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
        	
           // DataSource ds = Utils.getDataSource();    //create my own connection class
        	
        	Connection_class connection2 = new Connection_class();        	
            con = connection2.getConnection();  //this is now the sub class..Connection_class is parent
                 //can i select from book5 instead
            //pst = con.prepareStatement("SELECT NAME, PRICE FROM Cars");   //create cars table in sqlite
            //pst = con.prepareStatement("SELECT * from customers");
            pst = con.prepareStatement("SELECT * from recruiters");  //also book5
            rs = pst.executeQuery();

            jarray = SimpleJsonUtils.toJSON(rs);
            
            /*
            
            // trying to get col names as an array, then set attribute and pass to ajax (the col names only)
             //this is so we can show col names for table icon and maybe pass whole array to column model fro grid
              //perhaps we write separate servlet for this
               //see view_html.java in servlet example project
            
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            
         // get the column names; column indexes start from 1
            for (int i = 1; i < numberOfColumns + 1; i++) {
              String columnName = rsMetaData.getColumnName(i);
              // Get the name of the column's table name
              //String tableName = rsMetaData.getTableName(i);
              System.out.println("column name=" + columnName);
            }
            
           */ 
            

        } catch (SQLException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

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
                
                LOG.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return jarray;
    }
}