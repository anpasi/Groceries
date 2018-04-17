package com.groceries.dto;

public class GroceryBuilder {
	
	private String title=null;
	private Integer calories=null;	
	private Double price=null;
	private String description=null;
	
	
	
	public Grocery buildGrocery(){
		Grocery grocery = new Grocery();
		grocery.setCalories(calories);
		grocery.setPrice(price);
		grocery.setTitle(title);
		grocery.setDescription(description);
		return grocery;
	}
	
	public GroceryBuilder withDescription(String description){
		this.description = description;
		return this;
	}
	
	public GroceryBuilder withTitle(String title){
		this.title=title;
		return this;
	}
	
	public GroceryBuilder withCalories(Integer calories){
		this.calories = calories;
		return this;
	}
	
	public GroceryBuilder withPrice(Double price){
		this.price = price;
		return this;
	}
	
}
