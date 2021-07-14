import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.groceries.config.BeanConfiguration;
import com.groceries.dto.Grocery;
import com.groceries.services.GroceryService;
import com.groceries.services.JsonObjectBuilder;

public class Application {
	
	public static final String DEFAULT_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	

	public static void main(String[] args) {
	
		
		ApplicationContext app =  new AnnotationConfigApplicationContext(BeanConfiguration.class);
		
		initTrustConnections();
		
		GroceryService groceryService = (GroceryService)app.getBean("groceryService");
		JsonObjectBuilder jsonService = (JsonObjectBuilder)app.getBean("jsonService");
		try {
			//Get groceries    
			List<Grocery> groceries = groceryService.getAllGroceries(DEFAULT_URL);
			//Parse groceries 
			System.out.println("JSON is:\n" + jsonService.buildJsonObjectFromGroceryList(groceries));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static void initTrustConnections() {

		System.setProperty("https.protocols", "TLSv1.2");

		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){

			public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
			public void checkClientTrusted(X509Certificate[] certs, String authType){}
			public void checkServerTrusted(X509Certificate[] certs, String authType){}
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] arg0, String arg1)
							throws CertificateException {

			}
			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] chain,
					String authType) throws CertificateException {
			}

		}};

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}
	}

}
