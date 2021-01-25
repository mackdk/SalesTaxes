package com.salestaxes.io;

import com.salestaxes.model.CategorizedProduct;
import com.salestaxes.model.Receipt;

import javax.annotation.Nonnull;

/**
 * Write a receipt to a string.
 */
public class ReceiptWriter {

	private static final String EOL = System.lineSeparator();

	public static final String SALES_TAXES_ENTRY = "Sales Taxes";

	private static final String TOTAL_ENTRY = "Total";

	/**
	 * Converts the given receipt to a string. The format is:
	 * <pre>
	 *     &lt;qty&gt; &lt;product 1 description&gt;: &lt;priceAfterTaxes&gt;
	 *     &lt;qty&gt; &lt;product 2 description&gt;: &lt;priceAfterTaxes&gt;
	 *     ...
	 *     &lt;qty&gt; &lt;product n description&gt;: &lt;priceAfterTaxes&gt;
	 *     Sales taxes: &lt;total taxes&gt;
	 *     Total: &lt;total amount&gt;
	 * </pre>
	 *
	 * @param receipt the receipt to be printed.
	 *
	 * @return the string representing the given receipt.
	 */
	@Nonnull
	public String writeReceipt(@Nonnull Receipt receipt) {
		final var builder = new StringBuilder();

		for (CategorizedProduct product : receipt.getProductsList()) {
			builder.append(product.getQuantity()).append(' ')
				   .append(product.getDescription()).append(": ")
				   .append(String.format("%.2f", product.getPrice())).append(EOL);
		}

		builder.append(SALES_TAXES_ENTRY).append(": ")
			   .append(String.format("%.2f", receipt.getSalesTaxes())).append(EOL);

		builder.append(TOTAL_ENTRY).append(": ")
			   .append(String.format("%.2f", receipt.getTotal())).append(EOL);

		return builder.toString();
	}
}
