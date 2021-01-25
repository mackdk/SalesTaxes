package com.salestaxes.calculator;

import com.salestaxes.model.CategorizedProduct;
import com.salestaxes.model.Product;
import com.salestaxes.model.Receipt;
import com.salestaxes.utils.DoubleUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Calculate the receipt of a list of products.
 */
public class ReceiptCalculator {

	private final ProductClassifier productClassifier;

	private final double basicSaleTax;

	private final double importTax;

	public ReceiptCalculator(ProductClassifier productClassifier, double basicSaleTax, double importTax) {
		this.productClassifier = productClassifier;
		this.basicSaleTax = basicSaleTax;
		this.importTax = importTax;
	}

	/**
	 * Generates the receipt by calculating the taxes and the total of the give list of products.
	 *
	 * @param productList the list of products to compute.
	 *
	 * @return the object representing the receipt.
	 */
	@Nonnull
	public Receipt calculateReceipt(@Nonnull List<Product> productList) {
		final var categorizedProducts = new ArrayList<CategorizedProduct>(productList.size());

		double total = 0;
		double salesTaxes = 0;

		for (Product product : productList) {
			final var category = productClassifier.classify(product);
			final var priceAfterTaxes = computeFinalPrice(product.getPrice(), category.isTaxExempted(), product.isImported());

			// Update the taxes and the total
			salesTaxes += (priceAfterTaxes - product.getPrice()) * product.getQuantity();
			total += priceAfterTaxes * product.getQuantity();

			categorizedProducts.add(new CategorizedProduct(product, priceAfterTaxes, category));
		}

		return new Receipt(categorizedProducts, DoubleUtils.round(salesTaxes, 2), DoubleUtils.round(total, 2));
	}

	/**
	 * Compute the final price after sales and import taxes.
	 *
	 * @param price       the initial price of the product.
	 * @param taxExempted true if the product is exempted from the taxes.
	 * @param imported    true if the product is imported from abroad.
	 *
	 * @return the initial price plus the taxes.
	 */
	private double computeFinalPrice(double price, boolean taxExempted, boolean imported) {
		double taxes = 0;

		if (imported) {
			taxes += price * importTax;
		}

		if (!taxExempted) {
			taxes += price * basicSaleTax;
		}

		return DoubleUtils.round(price + DoubleUtils.roundToNearest5Percent(taxes), 2);
	}

}
