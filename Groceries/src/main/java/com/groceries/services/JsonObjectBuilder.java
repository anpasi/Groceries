package com.groceries.services;

import java.util.List;


import com.groceries.dto.Grocery;

public interface JsonObjectBuilder {
	
	
	/***
	 * 
	 * Parses a list of groceries to JSON object. This contains a list of formatted groceries and the total amount of the list
	 * 
	 * @param list of groceries
	 * @return JSON object 
	 * @throws Exception
	 */
	public String buildJsonObjectFromGroceryList(List<Grocery> groceries) throws  Exception;

}
