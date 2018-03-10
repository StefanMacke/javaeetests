package it.macke.javaeetests.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.macke.javaeetests.viewmodel.Article;

@DisplayName("An ArticleService should")
public class ArticleServiceShould
{
	private ArticleService sut;

	@BeforeEach
	public void setup()
	{
		sut = new ArticleService();
	}

	@Test
	@DisplayName("convert JSON to list of articles")
	public void convertJsonToArticleList()
	{
		final String json = "["
				+ "    {"
				+ "        \"title\": { "
				+ "            \"rendered\": \"The first title\""
				+ "        },"
				+ "        \"excerpt\": { "
				+ "            \"rendered\": \"This is the content\""
				+ "        },"
				+ "        \"link\": \"http://example.com\""
				+ "    },"
				+ "    {"
				+ "        \"title\": { "
				+ "            \"rendered\": \"The second title\""
				+ "        },"
				+ "        \"excerpt\": { "
				+ "            \"rendered\": \"This is another content\""
				+ "        },"
				+ "        \"link\": \"http://example2.com\""
				+ "    }    "
				+ "]";

		final List<Article> articles = sut.createArticles(json);

		final Article first = articles.get(0);
		assertThat(first.getTitle(), is("The first title"));
		assertThat(first.getExcerpt(), is("This is the content"));
		assertThat(first.getUrl(), is("http://example.com"));

		final Article second = articles.get(1);
		assertThat(second.getTitle(), is("The second title"));
		assertThat(second.getExcerpt(), is("This is another content"));
		assertThat(second.getUrl(), is("http://example2.com"));
	}
}
