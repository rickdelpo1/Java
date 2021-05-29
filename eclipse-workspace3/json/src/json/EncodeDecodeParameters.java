package json;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
 

public class EncodeDecodeParameters {
	
	public static void main(String...s){
		String parameter = "Samsung S7, iPhone 7";
		String encodedParameter, decodedParameter;
		
		//encoding and decoding url parameters
		try {
			encodedParameter = URLEncoder.encode(parameter, "UTF-8");
			decodedParameter = URLDecoder.decode(encodedParameter, "UTF-8");
			
			System.out.println("Encoded Parameter:" + encodedParameter);
			System.out.println("Decoded Parameter:" + decodedParameter);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
