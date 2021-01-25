package com.salestaxes.model;

import java.util.Objects;
import java.util.StringJoiner;

import javax.annotation.Nonnull;

/**
 * Immutable class representing a product.
 */
public class Product {

	@Nonnull
	private final String description;

	private final double price;

	private final int quantity;

	private final boolean imported;

	public Product(@Nonnull String description, double price, int quantity, boolean imported) {
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.imported = imported;
	}

	@Nonnull
	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public boolean isImported() {
		return imported;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Product product = (Product) o;
		return Double.compare(product.price, price) == 0 && quantity == product.quantity && imported == product.imported && description.equals(product.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, price, quantity, imported);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
				.add("description='" + description + "'")
				.add("price=" + price)
				.add("quantity=" + quantity)
				.add("imported=" + imported)
				.toString();
	}
}
