package it.macke.javaeetests.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.base.Objects;

public class Article
{
	@NotNull(message = "Title may not be empty")
	@Size(min = 1, message = "Title may not be empty")
	private String title;

	@NotNull(message = "Excerpt may not be empty")
	@Size(min = 1, message = "Excerpt may not be empty")
	private String excerpt;

	@NotNull(message = "URL may not be empty")
	@Size(min = 1, message = "URL may not be empty")
	private String url;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getExcerpt()
	{
		return excerpt;
	}

	public void setExcerpt(final String excerpt)
	{
		this.excerpt = excerpt;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(final String url)
	{
		this.url = url;
	}

	@Override
	public String toString()
	{
		return "Article [title=" + getTitle() + ", excerpt=" + getExcerpt() + ", author=" + ", url=" + getUrl() + "]";
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getTitle(), getExcerpt(), getUrl());
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
		final Article other = (Article) obj;
		return Objects.equal(getTitle(), other.getTitle())
				&& Objects.equal(getExcerpt(), other.getExcerpt())
				&& Objects.equal(getUrl(), other.getUrl());
	}

}
