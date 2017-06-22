package it.macke.javaeetests.viewmodel;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.base.Objects;

@Named
@SessionScoped
public class Credentials implements Serializable
{
	private static final long serialVersionUID = 2397106667865172732L;

	@NotNull(message = "User name may not be empty")
	@Size(min = 1, message = "User name may not be empty")
	private String userName;

	@NotNull(message = "Password may not be empty")
	@Size(min = 1, message = "Password may not be empty")
	private String password;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(final String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "Credentials [userName=" + getUserName() + ", password=" + getPassword() + "]";
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getUserName(), getPassword());
	}

	@Override
	public boolean equals(Object obj)
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
		Credentials other = (Credentials) obj;
		return Objects.equal(getUserName(), other.getUserName())
				&& Objects.equal(getPassword(), other.getPassword());
	}

}
