package com.groceries.services;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface UrlContentReaderService {
	
	Document getContentFromDefaultUrl() throws IOException;
	
	Document getContentFromUrl(String url) throws IOException;

}
