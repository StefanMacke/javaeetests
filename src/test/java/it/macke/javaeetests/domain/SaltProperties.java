package it.macke.javaeetests.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class SaltProperties
{
	@Property
	public void createOnlyValidSalts()
	{
		final String salt = Salt.generate().getValue();

		assertEquals(8, salt.length(),
				"Salt has to be 8 characters long");
		assertTrue(Pattern.matches("[a-zA-Z]+", salt),
				"Salt may only contain letters");
	}
}
