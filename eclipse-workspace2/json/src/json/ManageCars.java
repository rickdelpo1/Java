package json;

//note that json.simple.jar needs to be in both build path and web-inf lib otherwise will
  //get runtime exception as not able to find org.json.simple.JSONArray class
  // output is json file called ManageCars.json and is only in memory but will be used from mem for jsgrid

//import com.zetcode.service.CarService;     //already have car service class in project
//import com.zetcode.util.Utils;             //connection not used in this file
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;  //commented out because i now have web.xml
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;


//see this link so we dont use json.simple and thus CarService.java not needed
//https://stackoverflow.com/questions/6514876/most-efficient-conversion-of-resultset-to-json
   //it looks like if we use org.json library instead it is a newer library and does json array easier

      //or this one which seems to ignore data types.. perhaps types are in the jar file
//http://biercoff.com/nice-and-simple-converter-of-java-resultset-into-jsonarray-or-xml/

      //or this from 2018
//https://geekylearner.com/how-to-convert-resultset-to-json-array-and-iterate-through-the-json-array-on-jsp/




//took away annotation and added web.xml because i needed to implement cors but this is cors just for this servlet
//need cors for all of eclipse at localhost:8080

//@WebServlet(name = "ManageCars", urlPatterns = {"/ManageCars"})
public class ManageCars extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	         //got rid of web xml cors and put this in place..see desktop file on cors policy
    	         //also by hosting html file in tomcat app we avoid cors at other levels too
    	             //drag html and js file to webcontent folder and acess via localhost:8080/json/html file
    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
    	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
    	
    	
    	
    	

        response.setContentType("application/json");  //gets response object in json format similar to opening excel
        response.setCharacterEncoding("UTF-8");

        JSONArray ar = CarService.getCarsJSON();

        response.getWriter().write(ar.toJSONString());  //gets resp object, writes in temp space and offers to open
    }

    
  /*  
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String name = request.getParameter("NAME");
        int price = Integer.valueOf(request.getParameter("PRICE"));

        CarService.insertCar(name, price);

        getServletContext().log("Car " + name + " inserted");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        Map<String, String> dataMap = Utils.getParameterMap(request);

        String carName = dataMap.get("NAME");
        int carPrice = Integer.valueOf(dataMap.get("PRICE"));

        CarService.updateCar(carName, carPrice);

        getServletContext().log("Car " + carName + " updated" + carPrice);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        Map<String, String> dataMap = Utils.getParameterMap(request);

        String carName = dataMap.get("NAME");

        CarService.deleteCar(carName);

        getServletContext().log("Car:" + carName + " deleted");
    }

    */

}