package it.macke.javaeetests.viewmodel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Credentials>> constraintViolations = validator.validate(sut);

		assertThat(constraintViolations.size(), is(0));
	}

	@Test
	@DisplayName("implement its basic Getters and Setters correctly")
	public void implementBasicGettersAndSettersCorrectly() throws Exception
	{
		PropertyAsserter.assertBasicGetterSetterBehavior(sut);
	}
}
