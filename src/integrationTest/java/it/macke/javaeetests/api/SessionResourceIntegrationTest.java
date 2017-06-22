package it.macke.javaeetests.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.http.ContentType;
import it.macke.javaeetests.service.UserService;
import it.macke.javaeetests.viewmodel.Credentials;

@RunWith(JUnitPlatform.class)
@DisplayName("Integration test for SessionResource")
public class SessionResourceIntegrationTest
{
	protected static final String JETTY_HOST = "localhost";
	protected static final int JETTY_PORT = 18081;
	protected static Logger logger = LoggerFactory.getLogger(SessionResourceIntegrationTest.class);
	protected static Server JETTY_SERVER;
	private static UserService userService = mock(UserService.class);;

	protected WebTarget sut;

	@BeforeAll
	public static void setupClass() throws Exception
	{
		DemoApplication.setHostname(JETTY_HOST);
		DemoApplication.setPort(JETTY_PORT);
		final ServletContextHandler context =
				createServlet(
						rc -> rc.register(SessionResource.class),
						ab -> ab.bind(userService).to(UserService.class));
		startJetty(context);
	}

	@AfterAll
	public static void teardownClass() throws Exception
	{
		logger.info("Stopping Jetty");
		JETTY_SERVER.stop();
	}

	private static void startJetty(final ServletContextHandler context) throws Exception
	{
		logger.info("Starting Jetty on port " + JETTY_PORT + " for context path " + context.getContextPath());
		JETTY_SERVER = new Server(JETTY_PORT);
		JETTY_SERVER.setHandler(context);
		JETTY_SERVER.start();
	}

	private static ServletContextHandler createServlet(
			final Consumer<ResourceConfig> configureResource,
			final Consumer<AbstractBinder> configureBinder)
	{
		final ServletContextHandler context =
				new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(
				"/" + DemoApplication.CONTEXT_PATH
						+ "/" + DemoApplication.REST_PATH);

		final ResourceConfig rc = getResourceConfig(configureResource, configureBinder);

		final ServletHolder jerseyServlet =
				new ServletHolder(new ServletContainer(rc));
		context.addServlet(jerseyServlet, "/*");
		return context;
	}

	private static ResourceConfig getResourceConfig(
			final Consumer<ResourceConfig> configureResource,
			final Consumer<AbstractBinder> configureBinder)
	{
		final ResourceConfig rc = new ResourceConfig();
		configureResource.accept(rc);
		rc.register(new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(logger).to(Logger.class);
				configureBinder.accept(this);
			}
		});
		return rc;
	}

	@Test
	@DisplayName("authenticate a valid user")
	public void authenticateValidUser()
	{
		final Credentials validCredentials = new Credentials();
		validCredentials.setUserName("validusername");
		validCredentials.setPassword("ValidPassword");
		when(userService.isValidUser(validCredentials)).thenReturn(true);

		given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body("{ \"userName\": \"validusername\", \"password\": \"ValidPassword\" }")
				.when()
				.post(DemoApplication.getRestUrl() + "/sessions")
				.then()
				.statusCode(200)
				.body(is("true"));
	}

	@Test
	@DisplayName("not authenticate an invalid user")
	public void notAuthenticateInvalidUser()
	{
		final Credentials invalidCredentials = new Credentials();
		invalidCredentials.setUserName("invalidusername");
		invalidCredentials.setPassword("asdfasdf");
		when(userService.isValidUser(invalidCredentials)).thenReturn(false);

		given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body("{ \"userName\": \"invalidusername\", \"password\": \"asdfasdf\" }")
				.when()
				.post(DemoApplication.getRestUrl() + "/sessions")
				.then()
				.statusCode(401)
				.body(is("false"));
	}

	@Test
	@DisplayName("authenticate a valid user with Jersey client")
	public void authenticateValidUserWithJersey()
	{
		final Credentials validCredentials = new Credentials();
		validCredentials.setUserName("validusername");
		validCredentials.setPassword("ValidPassword");
		when(userService.isValidUser(validCredentials)).thenReturn(true);

		final Response response = ClientBuilder.newClient()
				.target(DemoApplication.getRestUrl())
				.path("/sessions")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(validCredentials));

		assertThat(response.getStatus(), is(200));
		assertThat(response.readEntity(Boolean.class), is(true));
	}
}
