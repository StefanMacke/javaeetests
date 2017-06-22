package it.macke.javaeetests.domain;

public class InvalidUserNameException extends Exception
{
	private static final long serialVersionUID = -5033257223154660826L;

	private final String invalidUserName;

	public InvalidUserNameException(final String message, final String invalidUserName)
	{
		super("Invalid user name: " + message);
		this.invalidUserName = invalidUserName;
	}

	public String getInvalidUserName()
	{
		return invalidUserName;
	}
}
