package com.salestaxes.calculator;

import com.salestaxes.model.Product;
import com.salestaxes.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeywordProductClassifierTest {

	private ProductClassifier classifier;


	@BeforeEach
	public void setup() {
		classifier = new KeywordProductClassifier();
	}

	@Test
	public void canClassifyProducts() {

		assertEquals(ProductCategory.Food, classifier.classify(createProductMock("chocolate bar")));
		assertEquals(ProductCategory.Food, classifier.classify(createProductMock("imported box of chocolates")));

		assertEquals(ProductCategory.Books, classifier.classify(createProductMock("book of science")));
		assertEquals(ProductCategory.Books, classifier.classify(createProductMock("large books")));

		assertEquals(ProductCategory.Medical, classifier.classify(createProductMock("pills")));
		assertEquals(ProductCategory.Medical, classifier.classify(createProductMock("imported headache pills")));

		assertEquals(ProductCategory.General, classifier.classify(createProductMock("videogame")));
		assertEquals(ProductCategory.General, classifier.classify(createProductMock("music player")));
	}

	private static Product createProductMock(String description) {
		final var mock = mock(Product.class);

		when(mock.getDescription()).thenReturn(description);

		return mock;
	}


}
