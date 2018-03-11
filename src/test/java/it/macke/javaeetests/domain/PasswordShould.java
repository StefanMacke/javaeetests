package it.macke.javaeetests.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Password should")
public class PasswordShould
{
	@Test
	@DisplayName("not accept short passwords")
	public void notAcceptShortPasswords() throws Exception
	{
		assertThat(Password.isValidPassword("asdf")).isFalse();
	}

	@Test
	@DisplayName("not accept passwords in lowercase")
	public void notAcceptPasswordsInLowercase() throws Exception
	{
		assertThat(Password.isValidPassword("asdfasdf")).isFalse();
	}

	@Test
	@DisplayName("not accept passwords in uppercase")
	public void notAcceptPasswordsInUppercase() throws Exception
	{
		assertThat(Password.isValidPassword("ASDFASDF")).isFalse();
	}

	@Test
	@DisplayName("accept complex passwords")
	public void acceptComplexPasswords() throws Exception
	{
		assertThat(Password.isValidPassword("AsdfAsdf")).isTrue();
	}

	@Test
	@DisplayName("remember the password")
	public void rememberThePassword() throws Exception
	{
		assertThat(new Password("AsdfFdsa").getValue())
				.startsWith("Asdf")
				.endsWith("Fdsa")
				.isEqualTo("AsdfFdsa");
	}
}
