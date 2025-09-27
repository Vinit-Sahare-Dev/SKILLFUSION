package beans;

 

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Encoder {
	public String encode(byte[] bytes){
	    String encodedfile = null;
	    try {
	      //  FileInputStream fileInputStreamReader = new FileInputStream(file);
	       // byte[] bytes = new byte[(dint)file.length()];
	       // fileInputStreamReader.read(bytes);
	      //  encodedfile = Base64.encodeBase64(bytes).toString();
	        encodedfile =  Base64.encodeBase64String(bytes) ;
	    }  
	      catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	     
	    return encodedfile;
	}
}
