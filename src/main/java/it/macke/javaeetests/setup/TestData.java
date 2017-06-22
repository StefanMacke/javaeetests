package it.macke.javaeetests.setup;

import it.macke.javaeetests.domain.Password;
import it.macke.javaeetests.domain.Salt;
import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.domain.UserName;

public class TestData
{
	private static final String VALID_USERNAME = "validusername";
	private static final String VALID_PASSWORD = "ValidPassword";
	private static final String VALID_SALT = "asdfasdf";

	public static User validUser()
	{
		try
		{
			final User user = new User();
			user.setPassword(new Password(VALID_PASSWORD));
			user.setSalt(new Salt(VALID_SALT));
			user.setUserName(new UserName(VALID_USERNAME));
			return user;
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Error while creating test user.", e);
		}
	}
}
