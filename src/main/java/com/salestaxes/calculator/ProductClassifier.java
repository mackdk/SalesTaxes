package com.salestaxes.calculator;

import com.salestaxes.model.Product;
import com.salestaxes.model.ProductCategory;

import javax.annotation.Nonnull;

/**
 * Classify a product by identifying the correct category.
 */
public interface ProductClassifier {

	/**
	 * Defines the proper category for the specified product.
	 *
	 * @param product the product to be classified. Must be non null.
	 *
	 * @return the proper category.
	 */
	@Nonnull
	ProductCategory classify(@Nonnull Product product);
}
