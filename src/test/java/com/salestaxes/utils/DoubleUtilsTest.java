package com.salestaxes.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleUtilsTest {

	@Test
	public void canRoundToNearest5Percent() {
		assertAll(
				() -> assertEquals("1.25", Double.toString(DoubleUtils.roundToNearest5Percent(1.234d))),
				() -> assertEquals("2.05", Double.toString(DoubleUtils.roundToNearest5Percent(2.01d))),
				() -> assertEquals("3.5", Double.toString(DoubleUtils.roundToNearest5Percent(3.50d))),
				() -> assertEquals("1.25", Double.toString(DoubleUtils.roundToNearest5Percent(1.223d))),
				() -> assertEquals("7.0", Double.toString(DoubleUtils.roundToNearest5Percent(7d))));
	}

	@Test
	public void canRoundToTheGivenAmountOfDecimals() {
		assertAll(
				() -> assertEquals("3.7", Double.toString(DoubleUtils.round(3.684324d, 1))),
				() -> assertEquals("4.23", Double.toString(DoubleUtils.round(4.22812d, 2))),
				() -> assertEquals("3.7", Double.toString(DoubleUtils.round(3.7, 8))),
				() -> assertEquals("4.23", Double.toString(DoubleUtils.round(4.22812d, 2))),
				() -> assertEquals("8.3486", Double.toString(DoubleUtils.round(8.34861837d, 4)))
				 );
	}
}
