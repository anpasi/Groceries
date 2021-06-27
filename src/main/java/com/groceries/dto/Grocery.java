package com.groceries.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * DTO with the grocery details
 * 
 * @author Antonio P
 *
 */
public class Grocery {

	private String title;
	private Integer calories;	
	private Double price;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("kcal_per_100g")
	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	@JsonProperty("unit_price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		int index = description.indexOf("\n");
		if (index>-1){
			System.out.println("description: " + description.indexOf("\n"));
			return description.substring(0, index);
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("title: ").append( getTitle());
		str.append(", price: ").append( getPrice());
		str.append(", description: ").append( getDescription());
		if (getCalories() != null){
			str.append(", calories: ").append(getCalories());
		}else{
			str.append(" no calories info ") ;
		}
		return str.toString(); 
	}

}
