package com.salestaxes;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Command line arguments for the main app. Due to the requirements of JCommander the fields cannot be final.
 */
public class Args {

	public static final int DEFAULT_SALES_TAX = 10;

	public static final int DEFAULT_IMPORT_TAX = 5;

	@Parameter(required = true, description = "Input files")
	private List<String> files;

	@Parameter(names = {"--importTax", "-i"}, description = "The % of the import tax", validateWith = {PositiveInteger.class})
	private int importTax;

	@Parameter(names = {"--salesTax", "-s"}, description = "The % of the sales tax", validateWith = {PositiveInteger.class})
	private int salesTax;

	@Parameter(names = "--help", help = true)
	private boolean help;

	public Args() {
		this.files = new ArrayList<>();
		this.importTax = DEFAULT_IMPORT_TAX;
		this.salesTax = DEFAULT_SALES_TAX;
		this.help = false;
	}

	public List<String> getFiles() {
		return files;
	}

	public int getImportTax() {
		return importTax;
	}

	public int getSalesTax() {
		return salesTax;
	}

	public boolean isHelp() {
		return help;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Args args = (Args) o;
		return importTax == args.importTax && salesTax == args.salesTax && help == args.help && Objects.equals(files, args.files);
	}

	@Override
	public int hashCode() {
		return Objects.hash(files, importTax, salesTax, help);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Args.class.getSimpleName() + "[", "]")
				.add("files=" + files)
				.add("importTax=" + importTax)
				.add("salesTax=" + salesTax)
				.add("help=" + help)
				.toString();
	}
}
