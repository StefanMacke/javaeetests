package it.macke.javaeetests.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

// "User" is not allowed as a table name
@Entity(name = "Users")
public class User
{
	@Id
	@GeneratedValue
	private long id;

	@NotNull(message = "User name may not be null")
	private UserName userName;
	@NotNull(message = "Password may not be null")
	private Password password;
	@NotNull(message = "Salt may not be null")
	private Salt salt;

	public User(final long id)
	{
		this.id = id;
	}

	public User()
	{}

	public long getId()
	{
		return id;
	}

	public void setId(final long newId)
	{
		this.id = newId;
	}

	public UserName getUserName()
	{
		return userName;
	}

	public void setUserName(final UserName userName)
	{
		this.userName = userName;
	}

	public Password getPassword()
	{
		return password;
	}

	public void setPassword(final Password password)
	{
		this.password = password;
	}

	public Salt getSalt()
	{
		return salt;
	}

	public void setSalt(final Salt salt)
	{
		this.salt = salt;
	}

	public boolean isCorrectPassword(final String password)
	{
		return this.password.getValue().equals(password);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("userName", getUserName())
				.add("password", getPassword())
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId(), getPassword(), getSalt(), getUserName());
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
		final User other = (User) obj;
		return Objects.equal(getId(), other.getId())
				&& Objects.equal(getPassword(), other.getPassword())
				&& Objects.equal(getSalt(), other.getSalt())
				&& Objects.equal(getUserName(), other.getUserName());
	}
}
