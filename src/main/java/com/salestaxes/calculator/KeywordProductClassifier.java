package com.salestaxes.calculator;

import com.salestaxes.model.Product;
import com.salestaxes.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nonnull;

/**
 * Default product classifier. Uses keyword to identify the product category.
 * The mapping between the keywords and the categories can be specified in a property file.
 */
public class KeywordProductClassifier implements ProductClassifier {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeywordProductClassifier.class);

	public static final String MAPPING_FILE = "keyword-category.properties";

	private final Map<String, ProductCategory> categoryMap;

	public KeywordProductClassifier() {
		final var properties = new Properties();

		try (final var inputStream = KeywordProductClassifier.class.getResourceAsStream(MAPPING_FILE)) {
			properties.load(inputStream);
		} catch (IOException ex) {
			LOGGER.warn("Unable to load mapping property file '{}'", MAPPING_FILE, ex);
			properties.clear();
		}

		this.categoryMap = new HashMap<>();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			ProductCategory category;

			try {
				category = ProductCategory.valueOf(entry.getValue().toString());
			} catch (IllegalArgumentException ex) {
				LOGGER.warn("Unable to map entry {}", entry, ex);
				category = null;
			}

			if (category != null) {
				categoryMap.put(entry.getKey().toString(), category);
			}
		}
	}

	@Nonnull
	@Override
	public ProductCategory classify(@Nonnull Product product) {
		return categoryMap.keySet()
						  .stream()
						  .filter(k -> product.getDescription().contains(k))    // Look for the keyword within the description
						  .findFirst()                                          // Retrieve only the first keyword that matches
						  .map(categoryMap::get)                                // Use the keyword to retrieve the category
						  .orElse(ProductCategory.General);                     // If no category matches use the default General

	}
}
