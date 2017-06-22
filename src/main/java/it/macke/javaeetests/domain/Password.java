package it.macke.javaeetests.domain;

public class Password extends ValueObject<String>
{
	private static final long serialVersionUID = -425236696369503066L;

	public Password(final String potentialPassword) throws InvalidPasswordException
	{
		super(potentialPassword);
		checkPotentialPassword(potentialPassword);
	}

	public static boolean isValidPassword(final String potentialPassword)
	{
		try
		{
			checkPotentialPassword(potentialPassword);
			return true;
		}
		catch (final InvalidPasswordException e)
		{
			return false;
		}
	}

	private static void checkPotentialPassword(final String potentialPassword) throws InvalidPasswordException
	{
		if (potentialPassword.length() < 8)
		{
			throw new InvalidPasswordException("too short", potentialPassword);
		}
		if (potentialPassword.toLowerCase().equals(potentialPassword)
				|| potentialPassword.toUpperCase().equals(potentialPassword))
		{
			throw new InvalidPasswordException("upper and lower case letters are needed", potentialPassword);
		}
	}
}
