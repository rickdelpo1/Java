package json;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DBJsonConverter {

    static ArrayList<String> data = new ArrayList<String>();
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;
    static String path = "";
    static String driver="";
    static String url="";
    static String username="";
    static String password="";
    static String query="";

    @SuppressWarnings({ "unchecked" })
    public static void dataLoad(String path) {
        JSONObject obj1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        conn = DatabaseConnector.getDbConnection(driver, url, username,
                password);
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<String> columnNames = new ArrayList<String>();
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    columnNames.add(columns.getColumnName(i));
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < columnNames.size(); i++) {
                        data.add(rs.getString(columnNames.get(i)));
                        {
                            for (int j = 0; j < data.size(); j++) {
                                if (data.get(j) != null) {
                                    obj.put(columnNames.get(i), data.get(j));
                                }else {
                                    obj.put(columnNames.get(i), "");
                                }
                            }
                        }
                    }
                           // build the json object in 2 steps, first the array and then the header
                    jsonArray.add(obj);  //we must first put object into json array then write to json string below
                    obj1.put("header", jsonArray);  //then create new object with desired header included
                    FileWriter file = new FileWriter(path);  //for servlet we use response object .get writer
                    file.write(obj1.toJSONString());  //same as pass object to response, then write to json string
                    file.flush();                               //but here we pass new object after adding header
                    file.close();
                }
                ps.close();
            } else {
                JSONObject obj2 = new JSONObject();
                obj2.put(null, null);
                jsonArray.add(obj2);
                obj1.put("header", jsonArray);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        driver = "oracle.jdbc.driver.OracleDriver";
        url = "jdbc:oracle:thin:@localhost:1521:database";
        username = "user";
        password = "password";
        path = "path of file";
        query = "select * from temp_employee";

        DatabaseConnector dc = new DatabaseConnector();
        dc.getDbConnection(driver,url,username,password);
        DBJsonConverter formatter = new DBJsonConverter();
        formatter.dataLoad(path);

    }

}




