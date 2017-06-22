package it.macke.javaeetests.domain;

import java.util.Random;

public class Salt extends ValueObject<String>
{
	private static final long serialVersionUID = -5671923545402570589L;
	private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public Salt(String salt)
	{
		super(salt);
	}

	public static Salt generate()
	{
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 1; i <= 8; i++)
		{
			int index = r.nextInt(VALID_CHARS.length());
			sb.append(VALID_CHARS.charAt(index));
		}
		return new Salt(sb.toString());
	}
}
