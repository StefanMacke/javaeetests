package it.macke.javaeetests.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.octodecillion.test.PropertyAsserter;

@RunWith(JUnitPlatform.class)
@DisplayName("A User should")
public class UserShould
{
	private User sut;

	@BeforeEach
	public void setup()
	{
		sut = new User();
	}

	@Test
	@DisplayName("implement its basic Getters and Setters correctly")
	public void implementBasicGettersAndSettersCorrectly()
	{
		PropertyAsserter.assertBasicGetterSetterBehavior(sut);
	}

	@Test
	@DisplayName("implement its Getters and Setters for individual types correctly")
	public void implementGettersAndSettersForIdividualTypesCorrectly() throws Exception
	{
		final Map<String, Object> properties = new HashMap<>();
		properties.put("userName", new UserName("validusername"));
		PropertyAsserter.assertBasicGetterSetterBehavior(sut, properties);
	}

	@Test
	@DisplayName("not be valid if attributes are missing")
	public void notBeValidIfAttributesAreMissing()
	{
		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		final Set<ConstraintViolation<User>> constraintViolations = validator.validate(sut);

		assertThat(constraintViolations.size(), is(3));

		for (final String message : new String[]
		{
				"User name may not be null",
				"Password may not be null",
				"Salt may not be null" })
		{
			assertThat("User should check contraint: " + message,
					constraintViolations.stream()
							.anyMatch(v -> v.getMessage().equals(message)),
					is(true));
		}
	}

	@Test
	@DisplayName("be valid if all attributes are set")
	public void beValidIfAllAttributesAreSet() throws Exception
	{
		sut.setUserName(new UserName("validusername"));
		sut.setPassword(new Password("ValidPassword"));
		sut.setSalt(new Salt("asdfasdf"));

		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		final Set<ConstraintViolation<User>> constraintViolations = validator.validate(sut);

		assertThat(constraintViolations.size(), is(0));
	}
}
