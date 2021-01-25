package com.salestaxes.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductParserTest {

	private ProductParser productParser;

	@BeforeEach
	public void setup() {
		productParser = new ProductParser();
	}

	@Test
	public void returnsNullWhenTextIsNullOrEmpty() {
		assertNull(productParser.parseProduct(null));
		assertNull(productParser.parseProduct(""));
		assertNull(productParser.parseProduct(" "));
		assertNull(productParser.parseProduct(" \t\n"));
	}

	@Test
	public void canParseNormalProduct() {

		final var product = productParser.parseProduct("1 music CD at 14.99");

		assertNotNull(product);
		assertAll("Product must be parsed correctly",
				  () -> assertEquals(1, product.getQuantity()),
				  () -> assertEquals("music CD", product.getDescription()),
				  () -> assertEquals(14.99d, product.getPrice()),
				  () -> assertFalse(product.isImported()));
	}

	@Test
	public void canParseImportedProduct() {

		final var product = productParser.parseProduct("3 boxes of imported chocolates at 11.25");

		assertNotNull(product);
		assertAll("Product must be parsed correctly",
				  () -> assertEquals(3, product.getQuantity()),
				  () -> assertEquals("boxes of imported chocolates", product.getDescription()),
				  () -> assertEquals(11.25d, product.getPrice()),
				  () -> assertTrue(product.isImported()));
	}

	@Test
	public void returnsNullWhenFormatIsNotCorrect() {

		final var product = productParser.parseProduct("Music CD (3) - Imported - 13.99");

		assertNull(product);
	}

	@Test
	public void returnsNullTheNumberFormatIsNotCorrect() {

		final var product = productParser.parseProduct("12 imported Music CD at 13.9.9");

		assertNull(product);
	}
}
