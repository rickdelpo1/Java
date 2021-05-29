package json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    static Connection conn1 = null;

    public static Connection getDbConnection(String driver, String url,
            String username, String password) {
        // TODO Auto-generated constructor stub
        try {

            Class.forName(driver);

            conn1 = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn1;
    }

}


/*

while(rs.next()) {
  int numColumns = rsmd.getColumnCount();
  JSONObject obj = new JSONObject();

  for( int i=1; i<numColumns+1; i++) {
    String column_name = rsmd.getColumnName(i);

    switch( rsmd.getColumnType( i ) ) {
      case java.sql.Types.ARRAY:
        obj.put(column_name, rs.getArray(column_name));     break;
      case java.sql.Types.BIGINT:
        obj.put(column_name, rs.getInt(column_name));       break;
      case java.sql.Types.BOOLEAN:
        obj.put(column_name, rs.getBoolean(column_name));   break;
      case java.sql.Types.BLOB:
        obj.put(column_name, rs.getBlob(column_name));      break;
      case java.sql.Types.DOUBLE:
        obj.put(column_name, rs.getDouble(column_name));    break;
      case java.sql.Types.FLOAT:
        obj.put(column_name, rs.getFloat(column_name));     break;
      case java.sql.Types.INTEGER:
        obj.put(column_name, rs.getInt(column_name));       break;
      case java.sql.Types.NVARCHAR:
        obj.put(column_name, rs.getNString(column_name));   break;
      case java.sql.Types.VARCHAR:
        obj.put(column_name, rs.getString(column_name));    break;
      case java.sql.Types.TINYINT:
        obj.put(column_name, rs.getInt(column_name));       break;
      case java.sql.Types.SMALLINT:
        obj.put(column_name, rs.getInt(column_name));       break;
      case java.sql.Types.DATE:
        obj.put(column_name, rs.getDate(column_name));      break;
      case java.sql.Types.TIMESTAMP:
        obj.put(column_name, rs.getTimestamp(column_name)); break;
      default:
        obj.put(column_name, rs.getObject(column_name));    break;
    }
  }

  json.put(obj);
}

*/


//below not using simple jar

/*

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ResultSetConverter {
  public static JSONArray convert( ResultSet rs )
    throws SQLException, JSONException
  {
    JSONArray json = new JSONArray();
    ResultSetMetaData rsmd = rs.getMetaData();

    while(rs.next()) {
      int numColumns = rsmd.getColumnCount();
      JSONObject obj = new JSONObject();

      for (int i=1; i<numColumns+1; i++) {
        String column_name = rsmd.getColumnName(i);

        if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
         obj.put(column_name, rs.getArray(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
         obj.put(column_name, rs.getInt(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
         obj.put(column_name, rs.getBoolean(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
         obj.put(column_name, rs.getBlob(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
         obj.put(column_name, rs.getDouble(column_name)); 
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
         obj.put(column_name, rs.getFloat(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
         obj.put(column_name, rs.getInt(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
         obj.put(column_name, rs.getNString(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
         obj.put(column_name, rs.getString(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
         obj.put(column_name, rs.getInt(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
         obj.put(column_name, rs.getInt(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
         obj.put(column_name, rs.getDate(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
        obj.put(column_name, rs.getTimestamp(column_name));   
        }
        else{
         obj.put(column_name, rs.getObject(column_name));
        }
      }

      json.put(obj);
    }

    return json;
  }
}

*/