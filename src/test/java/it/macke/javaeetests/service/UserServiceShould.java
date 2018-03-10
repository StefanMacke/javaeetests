package it.macke.javaeetests.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.macke.javaeetests.domain.Password;
import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.domain.UserRepository;

@DisplayName("UserService should")
public class UserServiceShould
{
	private UserService sut;
	private UserRepository userRepository;
	private MailService mailService;

	private static final String USER_NAME = "validusername";
	private static final String PASSWORD = "ValidPassword";

	@BeforeEach
	public void setup()
	{
		sut = new UserService();
		userRepository = mock(UserRepository.class);
		mailService = mock(MailService.class);
		sut.userRepository = userRepository;
		sut.mailService = mailService;
	}

	@Test
	@DisplayName("not authenticate an unknown user")
	public void notAuthenticateUnknownUser()
	{
		when(userRepository.findByUserName(USER_NAME)).thenReturn(Optional.empty());
		assertThat(sut.isValidUser(USER_NAME, PASSWORD), is(false));
	}

	@Test
	@DisplayName("not authenticate a user with wrong password")
	public void notAuthenticateUserWithWrongPassword() throws Exception
	{
		final User user = new User(1);
		user.setPassword(new Password(PASSWORD + "ABC"));
		when(userRepository.findByUserName(USER_NAME)).thenReturn(Optional.of(user));
		assertThat(sut.isValidUser(USER_NAME, PASSWORD), is(false));
	}

	@Test
	@DisplayName("authenticate a user with correct password")
	public void authenticateUserWithCorrectPassword() throws Exception
	{
		final User user = new User(1);
		user.setPassword(new Password(PASSWORD));
		when(userRepository.findByUserName(USER_NAME)).thenReturn(Optional.of(user));
		assertThat(sut.isValidUser(USER_NAME, PASSWORD), is(true));
	}

	@Test
	@DisplayName("save a new user to the repository")
	public void saveNewUserToRepository() throws Exception
	{
		final User user = sut.createUser(USER_NAME, PASSWORD);
		verify(userRepository).save(user);
	}

	@Test
	@DisplayName("notify the administrator when a new user is created")
	public void notifyAdminWhenUserIsCreated() throws Exception
	{
		sut.createUser(USER_NAME, PASSWORD);
		verify(mailService).notifyAdmin("New user created: " + USER_NAME);
	}
}
