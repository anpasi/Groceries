package com.groceries.services.impl;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groceries.dto.Grocery;
import com.groceries.services.GroceryService;
import com.groceries.services.UrlContentReaderService;

@Service("groceryService")
public class GroceryServiceImpl implements GroceryService {
	
	private UrlContentReaderService urlContentReaderService;
	
	@Autowired
	public GroceryServiceImpl(UrlContentReaderService urlContentReaderService) {
		this.urlContentReaderService = urlContentReaderService;
	}
	

	public List<Grocery> getAllGroceriesFromDefaultUrl() throws Exception{

		List<Grocery> groceryList = new ArrayList<>();
		Document groceryPage =  urlContentReaderService.getContentFromDefaultUrl();
		
		Elements elements = groceryPage.select("div.product");
		groceryList = elements.stream().map(element->parseGrocery(element)).collect(Collectors.toList());

		return groceryList;
	}

	public List<Grocery> getAllGroceries(String url) throws Exception {
		List<Grocery> groceryList = new ArrayList<>();
		Document groceryPage =  urlContentReaderService.getContentFromUrl(url); 
		Elements elements = groceryPage.select("div.product");
		groceryList = elements.stream().map(element->parseGrocery(element)).collect(Collectors.toList());		

		return groceryList;
	}


	private Grocery parseGrocery(Element element){
		Grocery grocery = new Grocery();		
		grocery.setTitle(getTitleFromTag(element));
		grocery.setPrice(getUnitPriceFromTag(element));
		getDescriptionAndCaloriesFromTag(element, grocery);
		return grocery;
	}

	private String getTitleFromTag(Element element) {
		Element link = element.select("div.productInfo").select("div.productNameAndPromotions").select("a").first();		
		String title =  link.html().replaceAll("<img.*", "").trim();		
		return Parser.unescapeEntities(title, true);
	}

	private Double getUnitPriceFromTag(Element element) {
		Element link = element.select("p.pricePerUnit").first();	
		String price = link.html().replaceAll("<abbr.*", "").substring(1);

		if (!StringUtils.isBlank(price) ){
			return Double.parseDouble(price);
		}
		return DOUBLE_ZERO;		
	}	


	private void getDescriptionAndCaloriesFromTag(Element element, Grocery grocery) {	
		//Extract link
		Element linkToDetailsPage = element.select("div.productInfo").select("div.productNameAndPromotions").select("a").first();

		try {
			//Connect to product details page
			Document detailsProductPage = urlContentReaderService.getContentFromUrl(linkToDetailsPage.attr("abs:href"));
			
			//Grocery description
			String description = detailsProductPage.select("div.productText p").first().html();		
			grocery.setDescription(Parser.unescapeEntities(description, true));

			//Grocery unit price
			Element caloriesElement = detailsProductPage.select("table.nutritionTable tbody tr td.nutritionLevel1").first();
			if (caloriesElement != null) {
				grocery.setCalories(Integer.parseInt(caloriesElement.html().replaceAll("kcal", "")));
			}		

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}
