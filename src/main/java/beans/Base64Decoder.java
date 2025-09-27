package beans;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Arrays;
public class Base64Decoder {
public byte[] decode(String str)
{
	 byte[] byteArray = Base64.decodeBase64(str );
	  
	 
	  // Print the decoded array
	 
	  System.out.println(Arrays.toString(byteArray));
	  
	 
	  // Print the decoded string 
	 
	  String decodedString = new String(byteArray);
	 
	  System.out.println(str + " = " + decodedString);
	  return byteArray;
}
}
