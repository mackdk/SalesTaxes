package com.salestaxes;

import nl.jqno.equalsverifier.Warning;

class ArgsTest extends AbstractPojoTest<Args> {

	protected ArgsTest() {
		super(Args.class);
	}

	@Override
	protected Warning[] getEqualsSuppressedWarnings() {
		return new Warning[]{Warning.NONFINAL_FIELDS};
	}
}
