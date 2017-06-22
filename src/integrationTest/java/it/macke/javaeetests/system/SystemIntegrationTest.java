package it.macke.javaeetests.system;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.http.ContentType;
import it.macke.javaeetests.controller.LoginController;
import it.macke.javaeetests.service.UserService;
import it.macke.javaeetests.viewmodel.Credentials;

@RunWith(Arquillian.class)
public class SystemIntegrationTest
{
	@Inject
	private UserService userService;

	@Inject
	private LoginController loginController;

	@Deployment
	public static WebArchive createDeployment()
	{
		return Deployments.createWebDeployment();
	}

	@Test
	public void authenticateValidUserViaService()
	{
		Credentials credentials = new Credentials();
		credentials.setUserName("validusername");
		credentials.setPassword("ValidPassword");
		assertThat(userService.isValidUser(credentials), is(true));
	}

	@Test
	public void authenticateValidUserViaController()
	{
		Credentials credentials = new Credentials();
		credentials.setUserName("validusername");
		credentials.setPassword("ValidPassword");
		loginController.setCredentials(credentials);
		assertThat(loginController.login(), is(LoginController.WELCOME_PAGE));
	}

	@Test
	@RunAsClient
	public void authenticateValidUserViaRest(
			@ArquillianResteasyResource final WebTarget webTarget)
	{
		given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body("{ \"userName\": \"validusername\", \"password\": \"ValidPassword\" }")
				.when()
				.post(webTarget.path("/sessions").getUri())
				.then()
				.statusCode(200)
				.body(is("true"));
	}
}