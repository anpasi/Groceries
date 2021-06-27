package com.groceries.services;

import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.groceries.dto.Grocery;

public class GroceryServiceTest {
	
	private final String EMPTY_URL = "";
	private final String WRONG_URL = "https://jsainsburyplc.github.WRONG/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	private final String CORRECT_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	
	private GroceryService groceryService;

	@Before
	public void beforeEachTest() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
		groceryService = (GroceryService)appContext.getBean("groceryService");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testEmptyUrl() throws Exception {
		groceryService.getAllGroceries(EMPTY_URL);
	}
	
	@Test(expected = UnknownHostException.class)
	public void testMalFormedUrl() throws Exception {
		groceryService.getAllGroceries(WRONG_URL);
	}
	
	
	@Test
	public void testCorrectUrl() throws Exception {
		initTrustConnections();
		List<Grocery> list = groceryService.getAllGroceries(CORRECT_URL);
		assertNotNull(list); 
	}


	private void initTrustConnections() {

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
