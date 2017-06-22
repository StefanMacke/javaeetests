package it.macke.javaeetests.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class HashGeneratorProperties
{
	@Property
	public void createOnlyValidSHA256Hashes(final String salt, final String password)
	{
		final String hash = HashGenerator.calculateSha256Hash(salt, password);

		assertEquals(64, hash.length(),
				"Hash has to be 64 characters long");
		assertTrue(Pattern.matches("[0-9a-f]+", hash),
				"Hash has to be a valid hex number in lower case");
		assertNotEquals(hash,
				HashGenerator.calculateSha256Hash(salt + "a", password),
				"Hash for different salt has to be different");
		assertNotEquals(hash,
				HashGenerator.calculateSha256Hash(salt, password + "a"),
				"Hash for different password has to be different");
	}
}
