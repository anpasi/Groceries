package com.groceries.services.impl;

import java.io.StringWriter;
import java.util.List;

import com.groceries.dto.Grocery;
import com.groceries.dto.GroceryList;
import com.groceries.services.JsonObjectBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonObjectBuilderImpl implements JsonObjectBuilder {

	public String buildJsonObjectFromGroceryList(List<Grocery> groceries) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringEmp = new StringWriter();
		GroceryList groceryList = new GroceryList();
		groceryList.setResults(groceries);
		objectMapper.writeValue(stringEmp, groceryList);
		return stringEmp.toString();
	}

}
