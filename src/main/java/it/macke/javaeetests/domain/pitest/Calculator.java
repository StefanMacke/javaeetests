package it.macke.javaeetests.domain.pitest;

public class Calculator
{
	public int abs(int a)
	{
		if (a < 0)
		{
			return -1 * a;
		}
		return a;
	}
}
