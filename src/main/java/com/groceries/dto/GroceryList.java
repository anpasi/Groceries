package com.groceries.dto;


import java.util.List;

public class GroceryList {

	
	private List<Grocery> results;
	private Double total;
	
	
	public GroceryList() {
	}



	public void setTotal(Double total) {
		this.total = total;
	}

	public List<Grocery> getResults() {
		return results;
	}

	public void setResults(List<Grocery> result) {
		this.results = result;
	}
	public Double getTotal() {
		total = new Double(0);
		
		for (Grocery grocery: results) {
			total += grocery.getPrice();
		}
		
		return total;
	}

}
