package com.groceries.services.impl;

import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.apache.commons.lang3.StringUtils;

import com.groceries.dto.Grocery;
import com.groceries.services.GroceryService;

public class GroceryServiceImpl implements GroceryService {

	public List<Grocery> getAllGroceries(String url) throws Exception{

		List<Grocery> groceryList = new ArrayList<Grocery>();

		Document groceryPage = Jsoup.connect(url).get();
		Elements elements = groceryPage.select("div.product");
		for (Element element: elements) {	
			groceryList.add(parseGrocery(element));
		}

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

		if (! StringUtils.isBlank(price) ){
			return Double.parseDouble(price);
		}
		return DOUBLE_ZERO;		
	}	


	private void getDescriptionAndCaloriesFromTag(Element element, Grocery grocery) {	
		//Extract link
		Element linkToDetailsPage = element.select("div.productInfo").select("div.productNameAndPromotions").select("a").first();

		try {
			//Connect to product details page
			Document detailsProductPage = Jsoup.connect(linkToDetailsPage.attr("abs:href")).get();

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
