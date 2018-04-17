package com.groceries.services;

import java.util.List;

import com.groceries.dto.Grocery;

public interface GroceryService {
	
	/***
	 * 
	 * Returns a list of groceries from the web site
	 * @param url to connect
	 * @return
	 * 		List of groceries. It can be empty
	 * @throws Exception
	 */
	public List<Grocery> getAllGroceries(String url) throws Exception;

}
