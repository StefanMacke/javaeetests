package it.macke.javaeetests.service;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import it.macke.javaeetests.viewmodel.Article;

@RequestScoped
public class ArticleService
{
	static String TARGET_SERVER = "http://serviceorientedarchitect.com";
	static String TARGET_PATH = "/wp-json/wp/v2/posts";

	public List<Article> findLatestArticles(final int numberOfArticles) throws Exception
	{
		final String jsonArticles = ClientBuilder.newClient()
				.target(TARGET_SERVER)
				.path(TARGET_PATH)
				.queryParam("per_page", numberOfArticles)
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
		return createArticles(jsonArticles);
	}

	List<Article> createArticles(final String json)
	{
		final ByteArrayInputStream stream = new ByteArrayInputStream(json.getBytes(Charset.forName("UTF-8")));
		final JsonReader reader = Json.createReader(stream);
		final JsonArray jsonArticles = reader.readArray();
		final List<Article> articles = new ArrayList<>();
		for (final JsonObject jsonObject : jsonArticles.getValuesAs(JsonObject.class))
		{
			final Article a = new Article();
			a.setTitle(jsonObject.getJsonObject("title").getString("rendered"));
			a.setExcerpt(jsonObject.getJsonObject("excerpt").getString("rendered"));
			a.setUrl(jsonObject.getString("link"));
			articles.add(a);
		}
		return articles;
	}
}
