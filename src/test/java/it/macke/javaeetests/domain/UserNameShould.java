package it.macke.javaeetests.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("A UserName should")
public class UserNameShould
{
	private UserName sut;

	@Test
	@DisplayName("be created from a valid user name")
	public void beCreatedFromValidUserName() throws Exception
	{
		sut = new UserName("validusername");
		assertThat(sut.getValue(), is("validusername"));
	}

	@Test
	@DisplayName("not be created from an invalid user name")
	public void notBeCreatedFromInvalidUserName()
	{
		final InvalidUserNameException exception = assertThrows(InvalidUserNameException.class, () ->
		{
			sut = new UserName("InVaLiD");
		});
		assertAll("error message should contain correct cause",
				() -> assertEquals("Invalid user name: only lower case letters allowed", exception.getMessage()),
				() -> assertEquals("InVaLiD", exception.getInvalidUserName()));
	}

	@DisplayName("identify valid user names")
	@ParameterizedTest(name = "valid user name {index}: <{0}>")
	@ValueSource(strings =
	{ "avalidusername", "anotherone", "and_another_one" })
	public void identifyValidUserName(final String validUserName) throws Exception
	{
		assertTrue(UserName.isValidUserName(validUserName));
	}

	@DisplayName("identify invalid user names")
	@ParameterizedTest(name = "invalid user name {index}: <{0}>")
	@CsvSource(
	{ "asd, too short", "ASDFG, only lower case letters allowed" })
	public void identifyInvalidUserName(final String invalidUserName, final String message) throws Exception
	{
		assertFalse(UserName.isValidUserName(invalidUserName),
				() -> "Username <" + invalidUserName + "> should be invalid (" + message + ")");
		final InvalidUserNameException exception = assertThrows(InvalidUserNameException.class, () ->
		{
			sut = new UserName(invalidUserName);
		});
		assertAll("error message should contain correct cause",
				() -> assertEquals("Invalid user name: " + message, exception.getMessage()),
				() -> assertEquals(invalidUserName, exception.getInvalidUserName()));

	}
}
