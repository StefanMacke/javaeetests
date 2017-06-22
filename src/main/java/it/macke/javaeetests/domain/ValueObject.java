package it.macke.javaeetests.domain;

import java.io.Serializable;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class ValueObject<T> implements Serializable
{
	private final T value;

	public ValueObject(final T value)
	{
		this.value = value;
	}

	public T getValue()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getValue());
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		@SuppressWarnings("unchecked")
		final ValueObject<T> other = (ValueObject<T>) obj;
		return Objects.equal(getValue(), other.getValue());
	}

	@Override
	public String toString()
	{
		return getValue().toString();
	}
}
