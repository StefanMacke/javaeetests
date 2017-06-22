package it.macke.javaeetests.domain;

public class InvalidPasswordException extends Exception
{
	private static final long serialVersionUID = 7453080377095079594L;

	private final String invalidPassword;

	public InvalidPasswordException(final String message, final String invalidPassword)
	{
		super("Invalid password: " + message);
		this.invalidPassword = invalidPassword;
	}

	public String getInvalidPassword()
	{
		return invalidPassword;
	}
}
