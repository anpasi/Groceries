package com.groceries.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.groceries.dto.Grocery;
import com.groceries.services.impl.GroceryServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class GroceryServiceTest {
	
	private static final String DETAILS_PRODUCT_PAGE = "https://detailsProduct.html";
	private final String GROCERIES_URL = "https://url.groceries.com";
	
	
	private final String PRODUCTS_PAGE = "<html><head></head> <body>  " 
	  + "<div class='product'>   <div class='productInfo'>"
			+ " <div class='productNameAndPromotions'>"
			+ "  <h3> <a href='https://detailsProduct.html'> Sainsbury Strawberries 400g <img src='c2.sainsburys.co.uk/wcsstore7.23.1.52/SainsburysStorefrontAssetStore/wcassets/product_images/2017/May/media_7555699_L.jpg' alt=''> </a> </h3>"
			+ " </div></div>"
			+ "<p class='pricePerUnit'> £1.75<abbr title='per'></abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p>"
			+ "</div>"
			+ "<div class='product'><div class='productInfo'>"
			+ " <div class='productNameAndPromotions'>"
			+ "  <h3> <a href='https://detailsProduct.html'> Sainsbury Blueberries 200g <img src='c2.sainsburys.co.uk/wcsstore7.23.1.52/SainsburysStorefrontAssetStore/wcassets/product_images/media_7555404_L.jpg' alt=''> </a> </h3>"
			+ " </div></div>"
			+ "<p class='pricePerUnit'> £1.75<abbr title='per'></abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p>"
			+ "</div>"
			+ "<div class='product'><div class='productInfo'>"
			+ " <div class='productNameAndPromotions'>"
			+ "  <h3> <a href='https://detailsProduct.html'> Sainsbury Raspberries 225g <img src='c2.sainsburys.co.uk/wcsstore7.23.1.52/SainsburysStorefrontAssetStore/wcassets/product_images/media_7555636_L.jpg' alt=''> </a> </h3>"
			+ " </div></div><p class='pricePerUnit'> £1.75<abbr title='per'></abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p>"
			+ "</div></body> </html>";

	
	private final String PRODUCT_DESCRIPTION_PAGE = "<html><head></head> <body><div class='productText'>    <p>by Sainsbury strawberries</p>   <table class='nutritionTable'><tbody><tr class='tableRow0'><td class='nutritionLevel1'>33kcal</td> <td class='nutritionLevel1'>2%</td></tr></tbody></table></div>    </body> </html>";
	
	@Mock
	private UrlContentReaderService urlService;
	
	private GroceryService groceryService;

	@BeforeEach
	public void beforeEachTest() throws IOException {
		
		when (urlService.getContentFromUrl(GROCERIES_URL)).thenReturn(Jsoup.parse(PRODUCTS_PAGE));
		when (urlService.getContentFromUrl(DETAILS_PRODUCT_PAGE)).thenReturn(Jsoup.parse(PRODUCT_DESCRIPTION_PAGE));
		groceryService = new GroceryServiceImpl(urlService);
	}

	
	@Test
	public void testGetGroceriesSuccessful() throws Exception {
		
		//When 
		List<Grocery> groceries = groceryService.getAllGroceries(GROCERIES_URL);
		assertNotNull(groceries);
		assertFalse( groceries.isEmpty());
		assertTrue(groceries.size()==3);
		
	}
		
}
