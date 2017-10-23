package br.com.https;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsClient{

   public static void main(String[] args)
   {
        new HttpsClient().testIt();
   }

   private void testIt(){
	   String https_url = "https://maps.googleapis.com/maps/api/geocode/json?address=16260+N+71st+St+Suite+400+Scottsdale+AZ+85254&key=AIzaSyBVDe9AMNO7PWMwWvR4s_swIv-62QV-emE"; 

      try {  

          // Create a URL for the desired page  
          URL url = new URL(https_url);  

          // Read all the text returned by the server  
          BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
          String str;              

          String json = "";
          int counter = 0;
          while ((str = in.readLine()) != null) 
          {  
              json = json + str;
          }     
          

          in.close();  

          
          String location = json.substring(json.indexOf("\"location\" :"), json.indexOf("\"location_type\"")-13);
          String lat = location.substring((location.indexOf("\"lat\"")+8), location.indexOf(","));
          String lng = location.substring((location.indexOf("\"lng\"")+8), (location.indexOf("}")-12));

          System.out.println(json);
          System.out.println(location);
          System.out.println(lat);
          System.out.println(lng);

      } catch (MalformedURLException e) {  
          e.printStackTrace();

      } catch (IOException e) {  
          e.printStackTrace();
      }

   }

   private void print_https_cert(HttpsURLConnection con){

    if(con!=null){

      try {

	System.out.println("Response Code : " + con.getResponseCode());
	System.out.println("Cipher Suite : " + con.getCipherSuite());
	System.out.println("\n");

	Certificate[] certs = con.getServerCertificates();
	for(Certificate cert : certs){
	  System.out.println("Cert Type : " + cert.getType());
	  System.out.println("Cert Hash Code : " + cert.hashCode());
	  System.out.println("Cert Public Key Algorithm : "
                                    + cert.getPublicKey().getAlgorithm());
	  System.out.println("Cert Public Key Format : "
                                    + cert.getPublicKey().getFormat());
	  System.out.println("\n");
	}

	} catch (SSLPeerUnverifiedException e) {
		e.printStackTrace();
	} catch (IOException e){
		e.printStackTrace();
	}

     }

   }

   private void print_content(HttpsURLConnection con){
	if(con!=null){

	try {

	  System.out.println("****** Content of the URL ********");
	  BufferedReader br =
		new BufferedReader(
			new InputStreamReader(con.getInputStream()));

	  String input;

	  while ((input = br.readLine()) != null){
	     System.out.println(input);
	  }
	  br.close();

	} catch (IOException e) {
	  e.printStackTrace();
	}

       }

   }

}
