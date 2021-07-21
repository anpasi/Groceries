package com.groceries.services.impl;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.groceries.services.UrlContentReaderService;



@Service
public class UrlContentReaderServiceImpl implements UrlContentReaderService {
	
	//Default url
	@Value( "${url.groceries}" )
	private String groceriesUrl;
	
	
	public UrlContentReaderServiceImpl() {
		initTrustConnections();
	}
	
	@Override
	public Document getContentFromDefaultUrl() throws IOException {
		return Jsoup.connect(groceriesUrl).get();
	}

	@Override
	public Document getContentFromUrl(String url) throws IOException {
		return Jsoup.connect(url).get();
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
			e.printStackTrace();
		}
	}



}
