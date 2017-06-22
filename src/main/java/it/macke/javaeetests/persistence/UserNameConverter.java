package it.macke.javaeetests.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import it.macke.javaeetests.domain.UserName;

@Converter(autoApply = true)
public class UserNameConverter implements AttributeConverter<UserName, String>
{
	@Override
	public String convertToDatabaseColumn(final UserName userName)
	{
		return userName.getValue();
	}

	@Override
	public UserName convertToEntityAttribute(final String value)
	{
		try
		{
			return new UserName(value);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error while converting String to UserName.", e);
		}
	}
}
