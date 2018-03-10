package it.macke.javaeetests.viewmodel;

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

public class CredentialsShould
{
	private Credentials sut;

	@BeforeEach
	public void setup()
	{
		sut = new Credentials();
		sut.setUserName("validusername");
		sut.setPassword("ValidPassword");
	}

	@Test
	@DisplayName("be valid if all attributes are set")
	public void beValid()
	{
		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		final Set<ConstraintViolation<Credentials>> constraintViolations = validator.validate(sut);

		assertThat(constraintViolations.size(), is(0));
	}

	@Test
	@DisplayName("be a valid Bean")
	public void beABean()
	{
		assertThat(Credentials.class, allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanEquals(),
				hasValidBeanToString()));
	}
}
