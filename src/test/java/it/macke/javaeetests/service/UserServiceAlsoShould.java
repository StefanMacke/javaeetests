package it.macke.javaeetests.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import it.macke.javaeetests.domain.Salt;
import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.domain.UserRepository;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Salt.class)
public class UserServiceAlsoShould
{
	private UserService sut;
	private UserRepository userRepository;
	private MailService mailService;

	@Before
	public void setup()
	{
		mockStatic(Salt.class);
		when(Salt.generate()).thenReturn(new Salt("asdfasdf"));
		sut = new UserService();
		userRepository = mock(UserRepository.class);
		mailService = mock(MailService.class);
		sut.userRepository = userRepository;
		sut.mailService = mailService;
	}

	@Test
	public void generateSaltForNewUser() throws Exception
	{
		final User user = sut.createUser("validusername", "ValidPassword");
		assertThat(user.getSalt().getValue(), is("asdfasdf"));
	}
}
