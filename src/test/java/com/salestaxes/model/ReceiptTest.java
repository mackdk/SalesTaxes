package com.salestaxes.model;

import com.salestaxes.AbstractPojoTest;

class ReceiptTest extends AbstractPojoTest<Receipt> {

	protected ReceiptTest() {
		super(Receipt.class);
	}

	@Override
	protected String[] getEqualsNonnullFields() {
		return new String[]{"productsList"};
	}
}
