package it.macke.javaeetests.domain;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User should")
public class UserShould
{
	private User sut;

	@BeforeEach
	public void setup()
	{
		sut = new User();
	}

	@Test
	@DisplayName("be a valid Bean")
	public void beABean()
	{
		assertThat(User.class, allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanEquals(),
				hasValidBeanToString()));
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
