package it.macke.javaeetests.frontend;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import it.macke.javaeetests.system.Deployments;

@RunWith(Arquillian.class)
public class FrontendIntegrationTest
{
	static final String USERNAME = "validusername";
	static final String PASSWORD = "ValidPassword";

	@ArquillianResource
	URL contextPath;

	@Drone
	WebDriver driver;

	@Deployment(testable = false)
	public static WebArchive createDeployment()
	{
		return Deployments.createWebDeployment();
	}

	@Test
	@InSequence(1)
	public void loginWithValidCredentials()
	{
		final LoginPage page = new LoginPage(driver, contextPath);
		page.login(USERNAME, PASSWORD);
		assertThat(driver.findElement(LoginPage.LOGGED_IN).isDisplayed(), is(true));
	}

	@Test
	@InSequence(2)
	public void logout()
	{
		final LoginPage page = new LoginPage(driver, contextPath);
		page.logout();
		assertThat(driver.findElement(LoginPage.LOGGED_OUT).isDisplayed(), is(true));
	}
}