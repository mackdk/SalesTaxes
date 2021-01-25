package com.salestaxes;

import com.beust.jcommander.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.annotation.Nonnull;

class SalesTaxesAppTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesTaxesAppTest.class);

	private static final String INPUT_URI = "/test-scenarios/{scenario}/input.txt";

	private static final String OUTPUT_URI = "/test-scenarios/{scenario}/output.txt";

	private SalesTaxesApp app;

	@BeforeEach
	public void setup() {
		app = new SalesTaxesApp(Args.DEFAULT_SALES_TAX / 100d, Args.DEFAULT_IMPORT_TAX / 100d);
	}

	@ParameterizedTest(name = "Testing scenario {0}")
	@ValueSource(strings = {"no-imports", "only-imports", "mixed"})
	public void testScenario(String scenarioName) {
		LOGGER.info("Executing scenario {}", scenarioName);
		final var valuesList = readInput(scenarioName);

		final var response = app.process(valuesList);
		LOGGER.info("Output generated\n{}", response);

		Assertions.assertEquals(readOutput(scenarioName), response.trim());
	}

	@Nonnull
	private static List<String> readInput(String scenarioName) {
		final var resourceName = INPUT_URI.replace("{scenario}", scenarioName);

		try {
			final var resource = SalesTaxesAppTest.class.getResource(resourceName);
			return Files.readAllLines(Path.of(resource.toURI()));
		} catch (URISyntaxException | IOException ex) {
			throw new IllegalStateException("Unable to read input file" + resourceName, ex);
		}
	}

	@Nonnull
	private static String readOutput(String scenarioName) {
		final var resourceName = OUTPUT_URI.replace("{scenario}", scenarioName);

		try {
			final var resource = SalesTaxesAppTest.class.getResource(resourceName);
			final var lines = Files.readAllLines(Path.of(resource.toURI()));
			return Strings.join(System.lineSeparator(), lines);
		} catch (URISyntaxException | IOException ex) {
			throw new IllegalStateException("Unable to read input file" + resourceName, ex);
		}
	}

}
