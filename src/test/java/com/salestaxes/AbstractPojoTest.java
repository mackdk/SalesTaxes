package com.salestaxes;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

public abstract class AbstractPojoTest<T> {

	private final Class<T> pojoClass;

	protected AbstractPojoTest(Class<T> pojoClass) {
		this.pojoClass = pojoClass;
	}

	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(pojoClass)
					  .usingGetClass()
					  .withNonnullFields(getEqualsNonnullFields())
					  .suppress(getEqualsSuppressedWarnings())
					  .verify();
	}

	@Test
	public void verifyToString() {
		ToStringVerifier.forClass(pojoClass)
						.verify();
	}

	protected String[] getEqualsNonnullFields() {
		return new String[0];
	}

	protected Warning[] getEqualsSuppressedWarnings() {
		return new Warning[0];
	}
}
