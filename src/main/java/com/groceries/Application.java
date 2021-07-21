package com.groceries;
 
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.groceries.config.BeanConfiguration;
import com.groceries.dto.Grocery;
import com.groceries.services.GroceryService;
import com.groceries.services.JsonObjectBuilder;

public class Application {
	
	public static final String GROCERIES_URL= "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	

	public static void main(String[] args) {
		
		
		ApplicationContext app =  new AnnotationConfigApplicationContext(BeanConfiguration.class);
		
		GroceryService groceryService = (GroceryService)app.getBean("groceryService");
		JsonObjectBuilder jsonService = (JsonObjectBuilder)app.getBean("jsonService");
		try {
			//Get groceries    
			List<Grocery> groceries = groceryService.getAllGroceriesFromDefaultUrl();
			//Parse groceries 
			System.out.println("JSON from default url is:\n" + jsonService.buildJsonObjectFromGroceryList(groceries));
			
			if (args != null && args.length>0) {
				groceries = groceryService.getAllGroceries(args[0]);
				//Parse groceries 
				System.out.println("JSON from args url is:\n" + jsonService.buildJsonObjectFromGroceryList(groceries));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
