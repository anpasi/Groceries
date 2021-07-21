package com.groceries.services;

import java.util.List;

import com.groceries.dto.Grocery;

public interface GroceryService {
	
	/***
	 * 
	 * Returns a list of groceries from the default web site
	 * @return
	 * 		List of groceries. It can be empty
	 * @throws Exception
	 */
	public List<Grocery> getAllGroceriesFromDefaultUrl() throws Exception;
	
	
	/**
	 * 
	 * Returns a list of groceries from the default web site
	 * @param url of the groceries repository
	 * @return List of groceries
	 * @throws Exception
	 */
	public List<Grocery> getAllGroceries(String url) throws Exception;


}
