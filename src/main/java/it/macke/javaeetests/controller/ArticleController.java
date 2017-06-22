package it.macke.javaeetests.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.macke.javaeetests.service.ArticleService;
import it.macke.javaeetests.viewmodel.Article;

@Named
@RequestScoped
public class ArticleController
{
	@Inject
	ArticleService articleService;

	Supplier<FacesContext> facesContextSupplier;

	@PostConstruct
	void initialize()
	{
		if (facesContextSupplier == null)
		{
			facesContextSupplier = () -> FacesContext.getCurrentInstance();
		}
	}

	public List<Article> getArticles()
	{
		try
		{
			return articleService.findLatestArticles(3);
		}
		catch (final Exception e)
		{
			facesContextSupplier.get().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Articles could not be read: " + e.getMessage(),
							null));
			return new ArrayList<>();
		}
	}
}
