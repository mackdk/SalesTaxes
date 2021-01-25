package com.salestaxes;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.salestaxes.calculator.KeywordProductClassifier;
import com.salestaxes.calculator.ReceiptCalculator;
import com.salestaxes.io.ProductParser;
import com.salestaxes.io.ReceiptWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Application entry point. Receives the files to process from the command line. Tax rates can be specified as optional parameters.
 * <pre>
 *     Usage: java -jar SalesTaxes-1.0-SNAPSHOT.jar [options] Input files
 *   Options:
 *     --help
 *
 *     --importTax, -i
 *       The % of the import tax
 *       Default: 5
 *     --salesTax, -s
 *       The % of the sales tax
 *       Default: 10
 * </pre>
 */
public class SalesTaxesApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesTaxesApp.class);

	private final ProductParser productParser;

	private final ReceiptWriter receiptWriter;

	private final ReceiptCalculator receiptCalculator;

	public SalesTaxesApp(double basicSalesTax, double importTax) {
		this.productParser = new ProductParser();
		this.receiptWriter = new ReceiptWriter();
		this.receiptCalculator = new ReceiptCalculator(new KeywordProductClassifier(), basicSalesTax, importTax);
	}

	/**
	 * Process the list of values and return the result.
	 *
	 * @param valuesList The list of products in to the expected format.
	 *
	 * @return a string representing the receipt computed based on the product and the tax rates.
	 */
	public String process(List<String> valuesList) {

		final var productsList = valuesList.stream()
										   .map(productParser::parseProduct)
										   .filter(Objects::nonNull)
										   .collect(Collectors.toUnmodifiableList());

		final var receipt = receiptCalculator.calculateReceipt(productsList);

		return receiptWriter.writeReceipt(receipt);
	}

	/**
	 * Main application entry point.
	 *
	 * @param args command line arguments.
	 */
	public static void main(String... args) {
		final Args arguments = parseArguments(args);
		if (arguments == null) {
			return;
		}

		final var salesTaxesApp = new SalesTaxesApp(arguments.getSalesTax() / 100d, arguments.getImportTax() / 100d);

		for (String inputFile : arguments.getFiles()) {
			try {
				LOGGER.info("Processing file {}", inputFile);
				final var products = Files.readAllLines(Path.of(inputFile));
				final var result = salesTaxesApp.process(products);
				System.out.println(result);
			} catch (Exception ex) {
				LOGGER.error("Unable to process the file", ex);
			}
		}
	}

	private static Args parseArguments(String[] args) {
		final var arguments = new Args();
		final var cliParser = JCommander.newBuilder().programName("java -jar SalesTaxes-1.0-SNAPSHOT.jar").addObject(arguments).build();

		try {
			cliParser.parse(args);
		} catch (ParameterException ex) {
			System.out.println("Syntax error: " + ex.getMessage());
			cliParser.usage();
			return null;
		}

		if (arguments.isHelp()) {
			cliParser.usage();
			return null;
		}

		return arguments;
	}

}
