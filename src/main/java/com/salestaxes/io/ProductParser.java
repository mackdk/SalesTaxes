package com.salestaxes.io;

import com.salestaxes.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

import javax.annotation.CheckForNull;

/**
 * Parses a product from a string.
 */
public class ProductParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductParser.class);

	private static final Pattern DESCRIPTION_REGEX = Pattern.compile("(\\d+)\\s+([\\w\\s]+)\\s+at\\s+(\\d+.\\d+)");

	private static final int EXPECTED_PRODUCT_GROUPS = 3;

	private static final String IMPORTED_TAG = "imported";

	/**
	 * Parses the given string and extract the product. The string must be in the format:
	 * <pre>&lt;qty&gt; &lt;description&gt; at &lt;price&gt;</pre>
	 * If the product is imported the description must contain the word 'imported' (sensitive case).
	 *
	 * @param text the product to be parsed
	 *
	 * @return the product represented by the string, or null in case of format mismatch.
	 */
	@CheckForNull
	public Product parseProduct(@CheckForNull String text) {
		if (text == null || text.isBlank()) {
			return null;
		}

		final var matcher = DESCRIPTION_REGEX.matcher(text);

		if (!matcher.matches() || matcher.groupCount() != EXPECTED_PRODUCT_GROUPS) {
			LOGGER.warn("Unable to match regex on the give text '{}'", text);
			return null;
		}

		try {
			final int quantity = Integer.parseInt(matcher.group(1));
			final String description = matcher.group(2);
			final double price = Double.parseDouble(matcher.group(3));

			return new Product(description, price, quantity, description.contains(IMPORTED_TAG));
		} catch (NumberFormatException ex) {
			LOGGER.warn("Unable to parse product from text '{}'", text, ex);
			return null;
		}
	}
}
