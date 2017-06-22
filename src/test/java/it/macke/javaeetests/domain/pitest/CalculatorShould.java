package it.macke.javaeetests.domain.pitest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CalculatorShould
{
	private Calculator sut;

	@Before
	public void setup()
	{
		sut = new Calculator();
	}

	@Test
	public void calculateAbsoluteValueOfPositiveNumber()
	{
		assertThat(sut.abs(1), is(1));
		assertThat(sut.abs(10), is(10));
		assertThat(sut.abs(100), is(100));
		assertThat(sut.abs(1000), is(1000));
	}

	@Test
	public void calculateAbsoluteValueOfNegativeNumber()
	{
		assertThat(sut.abs(-1), is(1));
		assertThat(sut.abs(-10), is(10));
		assertThat(sut.abs(-100), is(100));
		assertThat(sut.abs(-1000), is(1000));
	}

	@Test
	public void calculateAbsoluteValueOfZero()
	{
		assertThat(sut.abs(0), is(0));
	}
}
