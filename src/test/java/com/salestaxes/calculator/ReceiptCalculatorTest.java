package com.salestaxes.calculator;

import com.salestaxes.model.Product;
import com.salestaxes.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptCalculatorTest {

	private ReceiptCalculator receiptCalculator;

	@Mock
	private ProductClassifier classifier;

	@BeforeEach
	public void setup() {
		receiptCalculator = new ReceiptCalculator(classifier, 0.5, 0.1);
	}

	@Test
	public void canRecognizeExemptedProducts() {
		final var product = new Product("chocolate bar", 1.50, 1, false);

		when(classifier.classify(product)).thenReturn(ProductCategory.Food);

		final var receipt = receiptCalculator.calculateReceipt(Collections.singletonList(product));

		assertAll(
				() -> assertEquals(1.50d, receipt.getTotal()),
				() -> assertEquals(0, receipt.getSalesTaxes()),
				() -> assertEquals(1, receipt.getProductsList().size()),
				() -> assertEquals(ProductCategory.Food, receipt.getProductsList().get(0).getCategory()),
				() -> assertEquals(1.50, receipt.getProductsList().get(0).getPrice())
				 );
	}

	@Test
	public void canCalculateReceiptForStandardProduct() {
		final var product = new Product("battery", 1.23, 1, false);

		when(classifier.classify(product)).thenReturn(ProductCategory.General);

		final var receipt = receiptCalculator.calculateReceipt(Collections.singletonList(product));

		assertAll(
				() -> assertEquals(1.88, receipt.getTotal()),
				() -> assertEquals(0.65, receipt.getSalesTaxes()),
				() -> assertEquals(1, receipt.getProductsList().size()),
				() -> assertEquals(ProductCategory.General, receipt.getProductsList().get(0).getCategory()),
				() -> assertEquals(1.88, receipt.getProductsList().get(0).getPrice())
				 );
	}

	@Test
	public void canCalculateReceiptForStandardImportedProduct() {
		final var product = new Product("battery", 1.93, 1, true);

		when(classifier.classify(product)).thenReturn(ProductCategory.General);

		final var receipt = receiptCalculator.calculateReceipt(Collections.singletonList(product));

		assertAll(
				() -> assertEquals(3.13, receipt.getTotal()),
				() -> assertEquals(1.20, receipt.getSalesTaxes()),
				() -> assertEquals(1, receipt.getProductsList().size()),
				() -> assertEquals(ProductCategory.General, receipt.getProductsList().get(0).getCategory()),
				() -> assertEquals(3.13, receipt.getProductsList().get(0).getPrice())
				 );
	}

	@Test
	public void canCalculateReceiptForExemptedImportedProduct() {
		final var product = new Product("pills", 1.93, 1, true);

		when(classifier.classify(product)).thenReturn(ProductCategory.Medical);

		final var receipt = receiptCalculator.calculateReceipt(Collections.singletonList(product));

		assertAll(
				() -> assertEquals(2.13, receipt.getTotal()),
				() -> assertEquals(0.20, receipt.getSalesTaxes()),
				() -> assertEquals(1, receipt.getProductsList().size()),
				() -> assertEquals(ProductCategory.Medical, receipt.getProductsList().get(0).getCategory()),
				() -> assertEquals(2.13, receipt.getProductsList().get(0).getPrice())
				 );
	}

	@Test
	public void canCalculateReceiptForMultipleOfSameProduct() {
		final var product = new Product("battery", 1.93, 5, true);

		when(classifier.classify(product)).thenReturn(ProductCategory.General);

		final var receipt = receiptCalculator.calculateReceipt(Collections.singletonList(product));

		assertAll(
				() -> assertEquals(15.65, receipt.getTotal()),
				() -> assertEquals(6.0, receipt.getSalesTaxes()),
				() -> assertEquals(1, receipt.getProductsList().size()),
				() -> assertEquals(ProductCategory.General, receipt.getProductsList().get(0).getCategory()),
				() -> assertEquals(3.13, receipt.getProductsList().get(0).getPrice()));
	}

	@Test
	public void canCalculateReceiptForMultipleProducts() {
		final var foodProduct = new Product("chocolate bar", 1.50, 1, false);
		final var standardProduct = new Product("battery", 1.93, 1, true);
		final var medicalProduct = new Product("pills", 1.93, 1, true);

		when(classifier.classify(foodProduct)).thenReturn(ProductCategory.Food);
		when(classifier.classify(standardProduct)).thenReturn(ProductCategory.General);
		when(classifier.classify(medicalProduct)).thenReturn(ProductCategory.Medical);

		final var receipt = receiptCalculator.calculateReceipt(List.of(foodProduct, standardProduct, medicalProduct));

		assertAll(
				() -> assertEquals(6.76, receipt.getTotal()),
				() -> assertEquals(1.4, receipt.getSalesTaxes()),
				() -> assertEquals(3, receipt.getProductsList().size()),
				() -> assertEquals(ProductCategory.Food, receipt.getProductsList().get(0).getCategory()),
				() -> assertEquals(1.50, receipt.getProductsList().get(0).getPrice()),
				() -> assertEquals(ProductCategory.General, receipt.getProductsList().get(1).getCategory()),
				() -> assertEquals(3.13, receipt.getProductsList().get(1).getPrice()),
				() -> assertEquals(ProductCategory.Medical, receipt.getProductsList().get(2).getCategory()),
				() -> assertEquals(2.13, receipt.getProductsList().get(2).getPrice())
				 );
	}
}
