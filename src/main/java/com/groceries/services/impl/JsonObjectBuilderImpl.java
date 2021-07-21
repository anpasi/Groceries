package com.groceries.services.impl;

import java.io.StringWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groceries.dto.Grocery;
import com.groceries.dto.GroceryList;
import com.groceries.services.JsonObjectBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service("jsonService")
public class JsonObjectBuilderImpl implements JsonObjectBuilder {

	public String buildJsonObjectFromGroceryList(List<Grocery> groceries) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringEmp = new StringWriter();
		GroceryList groceryList = new GroceryList();
		groceryList.setResults(groceries);
		groceryList.setTotal(groceries.stream().mapToDouble(f -> f.getPrice()).sum());
		objectMapper.writeValue(stringEmp, groceryList);
		return stringEmp.toString();
	}

}
