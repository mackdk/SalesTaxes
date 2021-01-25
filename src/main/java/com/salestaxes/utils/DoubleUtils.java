package com.salestaxes.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * General utilities method for managing double values.
 */
public final class DoubleUtils {

	private DoubleUtils() {
		// Do not allow instantiation
	}

	/**
	 * Round up the given number to the nearest 0.05.
	 *
	 * @param value the value to round.
	 *
	 * @return the rounded value.
	 */
	public static double roundToNearest5Percent(double value) {
		return round(Math.ceil(value * 20d) / 20d, 2);
	}

	/**
	 * Rounds the given value to the specified scale.
	 *
	 * @param value the value to be rounded.
	 * @param scale the maximum number of decimals.
	 *
	 * @return the rounded value.
	 */
	public static double round(double value, int scale) {
		return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}
}
