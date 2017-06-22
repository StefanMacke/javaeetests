package it.macke.javaeetests.domain;

public class UserName extends ValueObject<String>
{
	private static final long serialVersionUID = -6571418031344260077L;

	public UserName(final String potentialUserName) throws InvalidUserNameException
	{
		super(potentialUserName);
		checkPotentialUserName(potentialUserName);
	}

	public static boolean isValidUserName(final String potentialUserName)
	{
		try
		{
			checkPotentialUserName(potentialUserName);
			return true;
		}
		catch (final InvalidUserNameException e)
		{
			return false;
		}
	}

	private static void checkPotentialUserName(final String potentialUserName) throws InvalidUserNameException
	{
		if (potentialUserName.length() < 4)
		{
			throw new InvalidUserNameException("too short", potentialUserName);
		}
		if (!potentialUserName.toLowerCase().equals(potentialUserName))
		{
			throw new InvalidUserNameException("only lower case letters allowed", potentialUserName);
		}
	}
}
