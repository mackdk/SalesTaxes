package com.salestaxes.io;

import com.salestaxes.model.CategorizedProduct;
import com.salestaxes.model.ProductCategory;
import com.salestaxes.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptWriterTest {

	private ReceiptWriter receiptWriter;

	@BeforeEach
	public void setup() {
		receiptWriter = new ReceiptWriter();
	}

	@Test
	public void canWriteReceipt() {
		final var receipt = new Receipt(List.of(new CategorizedProduct("book", 12.49, 1, false, ProductCategory.Books),
												new CategorizedProduct("music CD", 16.49, 1, false, ProductCategory.General),
												new CategorizedProduct("chocolate bar", 0.85, 1, false, ProductCategory.Food)), 1.50d, 29.83d);

		final var expectedReceipt = "1 book: 12.49" + System.lineSeparator() +
									"1 music CD: 16.49" + System.lineSeparator() +
									"1 chocolate bar: 0.85" + System.lineSeparator() +
									"Sales Taxes: 1.50" + System.lineSeparator() +
									"Total: 29.83" + System.lineSeparator();

		assertEquals(expectedReceipt, receiptWriter.writeReceipt(receipt));
	}
}
