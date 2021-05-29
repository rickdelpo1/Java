package json;

import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.json.JSONArray;
//import org.json.JSONObject;
import javax.servlet.http.HttpSession;


//@WebServlet("/AjaxHandler")   //need web.xml for cors policy..register this servlet under cors
public class AjaxHandler extends HttpServlet {
//private static final long serialVersionUID = 1L;

     //will run with blank constructor or no constructor unless we want args inside to pass on
//public AjaxHandler() {
 // super();
//}
	
        //looks like we can pass 2 response objects from same servlet using 2 separate methods
        //can we have 2 doGet methods or in addition a service method?
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 // doPost(request, response);
	
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
	
        //use doGet to pass string var to jquery
                   //for some reason sessions need setting in both doGet and doPost in ordeer to persist
	 HttpSession session = request.getSession();

	 String text= "['first','email']";   //col model looking for fully qualified json
 // String text = "yet some other text2";
  session.setAttribute("yourDataKey",text);

  response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
  response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
  response.getWriter().write(text);       // Write response body.
  
  
}
                     //adding a 2 on end is doing nothing for jq grid
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                    //use doPost to get param and return it to html . refer to pass_variable.html in files folder
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
	//((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "http://ec2-52-15-160-113.us-east-2.compute.amazonaws.com:8071");
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
	((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");
	
  String name = request.getParameter("name1");
  //response2.getWriter().print("Hello "+ name + "!!");
  System.out.println(name);
  HttpSession session = request.getSession();
  session.setAttribute("yourDataKey",name); //pass val of get_string to next servlet
}
}