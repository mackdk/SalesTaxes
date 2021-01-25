package com.salestaxes.model;

import com.salestaxes.AbstractPojoTest;

class ProductTest extends AbstractPojoTest<Product> {

	protected ProductTest() {
		super(Product.class);
	}

	@Override
	protected String[] getEqualsNonnullFields() {
		return new String[]{"description"};
	}
}
