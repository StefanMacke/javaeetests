package it.macke.javaeetests.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import it.macke.javaeetests.domain.Password;

@Converter(autoApply = true)
public class PasswordConverter implements AttributeConverter<Password, String>
{
	@Override
	public String convertToDatabaseColumn(final Password password)
	{
		return password.getValue();
	}

	@Override
	public Password convertToEntityAttribute(final String value)
	{
		try
		{
			return new Password(value);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error while converting String to Password.", e);
		}
	}
}
