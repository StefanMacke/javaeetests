package it.macke.javaeetests.frontend;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage
{
	static final By LOGGED_IN = By.xpath("//h1[contains(text(),'Welcome')]");
	static final By LOGGED_OUT = By.xpath("//li[contains(text(),'Goodbye')]");

	static final By USERNAME_FIELD = By.id("loginForm:userName");
	static final By PASSWORD_FIELD = By.id("loginForm:password");

	static final By LOGIN_BUTTON = By.id("loginForm:login");
	static final By LOGOUT_BUTTON = By.id("loginForm:logout");

	private final WebDriver driver;
	private final URL contextPath;

	public LoginPage(final WebDriver driver, final URL contextPath)
	{
		this.driver = driver;
		this.contextPath = contextPath;
	}

	public void login(final String name, final String password)
	{
		driver.get(contextPath + "index.xhtml");
		driver.findElement(USERNAME_FIELD).sendKeys(name);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(LOGIN_BUTTON).click();
	}

	public void logout()
	{
		driver.findElement(LOGOUT_BUTTON).click();
	}
}