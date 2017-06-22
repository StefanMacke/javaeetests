package it.macke.javaeetests.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import it.macke.javaeetests.viewmodel.Article;

public class ArticleServiceIntegrationTest
{
	private static final String WIREMOCK_HOST = "localhost";
	private static final int WIREMOCK_PORT = 54321;

	private ArticleService sut;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration
			.options()
			.port(WIREMOCK_PORT)
			.bindAddress(WIREMOCK_HOST));;

	@Before
	public void setup()
	{
		sut = new ArticleService();
		ArticleService.TARGET_SERVER = "http://" + WIREMOCK_HOST + ":" + WIREMOCK_PORT;
	}

	@Test
	public void requestArticlesViaRest() throws Exception
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

		stubFor(get(urlPathEqualTo(ArticleService.TARGET_PATH)).withQueryParam("per_page", equalTo("2"))
				.willReturn(
						aResponse()
								.withStatus(200)
								.withHeader("Content-Type", "application/json")
								.withBody(json)));

		final List<Article> articles = sut.findLatestArticles(2);

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
