package com.groceries.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.groceries.config.BeanConfiguration;
import com.groceries.dto.Grocery;
import com.groceries.dto.GroceryBuilder;
import com.groceries.services.impl.JsonObjectBuilderImpl;
	

public class JsonObjectBuilderTest {
	
	private JsonObjectBuilder jsonService;

	@Before
	public void beforeEachTest() throws Exception {
//		ApplicationContext appContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
		jsonService = new JsonObjectBuilderImpl();
	}
	
	
	@Test
	public void testFromListToJSON() throws Exception {
		// GIVEN a list of groceries
		List<Grocery> groceries = new ArrayList<Grocery>();
		groceries.add(buildFullGrocery("Cherry", "The best of the world", 2.1, 300));
		groceries.add(buildFullGrocery("Berry", "From Midlands",1.81, 200));
		
		// When is parsed to JSON
		String jsonObject = jsonService.buildJsonObjectFromGroceryList(groceries);
			
		// Then 
		JSONAssert.assertEquals("{\"results\":[{\"title\":\"Cherry\",\"description\":\"The best of the world\",\"kcal_per_100g\":300,\"unit_price\":2.1},{\"title\":\"Berry\",\"description\":\"From Midlands\",\"kcal_per_100g\":200,\"unit_price\":1.81}],\"total\":3.91}", jsonObject, false);
	}
	
	@Test
	public void testFromListToJSONWithNoNewLinesInDescription() throws Exception {
		// GIVEN a list of groceries
		List<Grocery> groceries = new ArrayList<Grocery>();
		groceries.add(buildFullGrocery("Cherry", "The best of the world\nAnd of Universe", 2.1, 300));
		
		// When is parsed to JSON
		String jsonObject = jsonService.buildJsonObjectFromGroceryList(groceries);
			
		// Then 
		JSONAssert.assertEquals("{\"results\":[{\"title\":\"Cherry\",\"description\":\"The best of the world\",\"kcal_per_100g\":300,\"unit_price\":2.1}],\"total\":2.1}", jsonObject, false);
	}
	
	
	@Test
	public void testFromListToJSONWithNoCalories() throws Exception {
		// GIVEN a list of groceries
		List<Grocery> groceries = new ArrayList<Grocery>();
		groceries.add(buildGroceryWithNoCalories("Cherry", "The best of the world", 2.1));
		
		// When is parsed to JSON
		String jsonObject = jsonService.buildJsonObjectFromGroceryList(groceries);
			
		// Then 
		JSONAssert.assertEquals("{\"results\":[{\"title\":\"Cherry\",\"description\":\"The best of the world\",\"unit_price\":2.1}],\"total\":2.1}", jsonObject, false);
	}
	
	private Grocery buildFullGrocery(String title, String description, Double price, Integer calories){
		return new GroceryBuilder().withTitle(title).
				withDescription(description).withCalories(calories).withPrice(price).buildGrocery();
	}

	private Grocery buildGroceryWithNoCalories(String title, String description, Double price){
		return new GroceryBuilder().withTitle(title).
				withDescription(description).withPrice(price).buildGrocery();
	}

}
