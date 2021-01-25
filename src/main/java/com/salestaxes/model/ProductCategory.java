package com.salestaxes.model;

/**
 * Category of the product. The tax calculation depends on the category.
 */
public enum ProductCategory {
	Books(true),
	Food(true),
	Medical(true),
	General(false);

	private final boolean taxExempted;

	ProductCategory(boolean taxExempted) {
		this.taxExempted = taxExempted;
	}

	public boolean isTaxExempted() {
		return taxExempted;
	}
}
