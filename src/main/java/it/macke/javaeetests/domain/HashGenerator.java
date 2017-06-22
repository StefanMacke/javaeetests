package it.macke.javaeetests.domain;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashGenerator
{
	public static String calculateSha256Hash(final String salt, final String password)
	{
		return Hashing.sha256()
				.hashString(salt + ":" + password, StandardCharsets.UTF_8)
				.toString()
				.toLowerCase();
	}
}
