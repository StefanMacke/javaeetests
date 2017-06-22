package it.macke.javaeetests.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import it.macke.javaeetests.service.UserService;
import it.macke.javaeetests.viewmodel.Credentials;

@RunWith(JUnitPlatform.class)
@DisplayName("A LoginController should")
public class LoginControllerShould
{
	private LoginController sut;
	private UserService userService;
	private Credentials credentials;
	private FacesContext facesContext;

	@BeforeEach
	public void setup() throws Exception
	{
		credentials = new Credentials();
		userService = mock(UserService.class);
		facesContext = mock(FacesContext.class);
		sut = new LoginController();
		sut.userService = userService;
		sut.credentials = credentials;
		sut.facesContextSupplier = () -> facesContext;
	}

	private void givenTheCredentialsAreCorrect()
	{
		when(userService.isValidUser(any(Credentials.class))).thenReturn(true);
	}

	private void givenTheCredentialsAreNotCorrect()
	{
		when(userService.isValidUser(any(Credentials.class))).thenReturn(false);
	}

	@Test
	@DisplayName("navigate to welcome page after a successful login")
	public void navigateToWelcomePageAfterSuccessfulLogin()
	{
		givenTheCredentialsAreCorrect();

		assertThat(sut.login(), is(LoginController.WELCOME_PAGE));
	}

	@Test
	@DisplayName("navigate to index page after an unsuccessful login")
	public void navigateToIndexPageAfterUnsuccessfulLogin()
	{
		givenTheCredentialsAreNotCorrect();

		assertThat(sut.login(), is(LoginController.INDEX_PAGE));
	}

	@Test
	@DisplayName("show an error message after an unsuccessful login")
	public void showErrorMessageAfterUnsuccessfulLogin()
	{
		givenTheCredentialsAreNotCorrect();

		sut.login();

		final ArgumentCaptor<FacesMessage> message = ArgumentCaptor.forClass(FacesMessage.class);
		verify(facesContext).addMessage(isNull(), message.capture());
		assertThat(message.getValue().getSeverity(), is(FacesMessage.SEVERITY_ERROR));
		assertThat(message.getValue().getDetail(), is("Invalid user name or password"));
	}

	@Test
	@DisplayName("show a nice goodbye message after logou")
	public void showGoodbyeAfterLogout()
	{
		final String targetPage = sut.logout();
		assertThat(targetPage, is(LoginController.INDEX_PAGE));

		final ArgumentCaptor<FacesMessage> message = ArgumentCaptor.forClass(FacesMessage.class);
		verify(facesContext).addMessage(isNull(), message.capture());
		assertThat(message.getValue().getSeverity(), is(FacesMessage.SEVERITY_INFO));
		assertThat(message.getValue().getDetail(), is("Goodbye!"));
	}
}
