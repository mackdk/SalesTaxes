package com.salestaxes.model;

import java.util.Objects;
import java.util.StringJoiner;

import javax.annotation.Nonnull;

/**
 * A product which has been already classified and assigned to a specific category.
 */
public class CategorizedProduct extends Product {

	@Nonnull
	private final ProductCategory category;

	public CategorizedProduct(@Nonnull Product product, double priceAfterTaxes, @Nonnull ProductCategory category) {
		this(product.getDescription(), priceAfterTaxes, product.getQuantity(), product.isImported(), category);
	}

	public CategorizedProduct(@Nonnull String description, double price, int quantity, boolean imported, @Nonnull ProductCategory category) {
		super(description, price, quantity, imported);
		this.category = category;
	}

	@Nonnull
	public ProductCategory getCategory() {
		return category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		CategorizedProduct that = (CategorizedProduct) o;
		return category == that.category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), category);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", CategorizedProduct.class.getSimpleName() + "[", "]")
				.add("description='" + getDescription() + "'")
				.add("price=" + getPrice())
				.add("quantity=" + getQuantity())
				.add("imported=" + isImported())
				.add("category=" + category)
				.toString();
	}
}
