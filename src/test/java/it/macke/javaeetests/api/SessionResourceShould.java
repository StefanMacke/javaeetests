package it.macke.javaeetests.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import it.macke.javaeetests.service.UserService;
import it.macke.javaeetests.viewmodel.Credentials;

@DisplayName("SessionResource should")
public class SessionResourceShould
{
	private SessionResource sut;
	private UserService userService;
	private Logger logger;
	private Credentials credentials;

	@BeforeEach
	public void setup()
	{
		sut = new SessionResource();
		userService = mock(UserService.class);
		logger = mock(Logger.class);
		sut.userService = userService;
		sut.logger = logger;
		credentials = new Credentials();
	}

	private void givenCredentialsAreInvalid()
	{
		when(userService.isValidUser(any(Credentials.class))).thenReturn(false);
	}

	private void givenCredentialsAreValid()
	{
		when(userService.isValidUser(any(Credentials.class))).thenReturn(true);
	}

	@Test
	@DisplayName("return status code 400 for missing credentials")
	public void return400ForMissingCredentials()
	{
		assertThat(sut.authenticateUser(null).getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
	}

	@Test
	@DisplayName("return status code 401 for invalid credentials")
	public void return401ForInvalidCredentials()
	{
		givenCredentialsAreInvalid();
		assertThat(sut.authenticateUser(credentials).getStatus(), is(Response.Status.UNAUTHORIZED.getStatusCode()));
	}

	@Test
	@DisplayName("return status code 200 for valid credentials")
	public void return200ForValidCredentials()
	{
		givenCredentialsAreValid();
		assertThat(sut.authenticateUser(credentials).getStatus(), is(Response.Status.OK.getStatusCode()));
	}

	@Test
	@DisplayName("return true for valid credentials")
	public void returnTrueForValidCredentials()
	{
		givenCredentialsAreValid();
		assertThat(sut.authenticateUser(credentials).getEntity(), is(true));
	}

	@Test
	@DisplayName("return false for invalid credentials")
	public void returnFalseForInvalidCredentials()
	{
		givenCredentialsAreInvalid();
		assertThat(sut.authenticateUser(credentials).getEntity(), is(false));
	}
}
