package com.salestaxes.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.annotation.Nonnull;

/**
 * Represent a receipt as computed from a list of products.
 */
public class Receipt {

	@Nonnull
	private final List<CategorizedProduct> productsList;

	private final double salesTaxes;

	private final double total;

	public Receipt(@Nonnull List<CategorizedProduct> productsList, double salesTaxes, double total) {
		this.productsList = productsList;
		this.salesTaxes = salesTaxes;
		this.total = total;
	}

	@Nonnull
	public List<CategorizedProduct> getProductsList() {
		return Collections.unmodifiableList(productsList);
	}

	public double getSalesTaxes() {
		return salesTaxes;
	}

	public double getTotal() {
		return total;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Receipt receipt = (Receipt) o;
		return Double.compare(receipt.salesTaxes, salesTaxes) == 0 && Double.compare(receipt.total, total) == 0 && productsList.equals(receipt.productsList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productsList, salesTaxes, total);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Receipt.class.getSimpleName() + "[", "]")
				.add("productsList=" + productsList)
				.add("salesTaxes=" + salesTaxes)
				.add("total=" + total)
				.toString();
	}
}
