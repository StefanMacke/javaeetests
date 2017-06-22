package it.macke.javaeetests.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import it.macke.javaeetests.domain.Salt;

@Converter(autoApply = true)
public class SaltConverter implements AttributeConverter<Salt, String>
{
	@Override
	public String convertToDatabaseColumn(final Salt salt)
	{
		return salt.getValue();
	}

	@Override
	public Salt convertToEntityAttribute(final String value)
	{
		try
		{
			return new Salt(value);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error while converting String to Salt.", e);
		}
	}
}
