package com.salestaxes.model;

import com.salestaxes.AbstractPojoTest;

public class CategorizedProductTest extends AbstractPojoTest<CategorizedProduct> {

	protected CategorizedProductTest() {
		super(CategorizedProduct.class);
	}

	@Override
	protected String[] getEqualsNonnullFields() {
		return new String[]{"description", "category"};
	}
}
