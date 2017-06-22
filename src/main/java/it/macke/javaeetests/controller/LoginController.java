package it.macke.javaeetests.controller;

import java.io.Serializable;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.macke.javaeetests.service.UserService;
import it.macke.javaeetests.viewmodel.Credentials;

@Named
@SessionScoped
public class LoginController implements Serializable
{
	private static final long serialVersionUID = 3493246071028367843L;
	public static final String INDEX_PAGE = "index.xhtml";
	public static final String WELCOME_PAGE = "welcome.xhtml";

	@Inject
	Credentials credentials;

	@Inject
	UserService userService;

	transient Supplier<FacesContext> facesContextSupplier;

	@PostConstruct
	void initialize()
	{
		if (facesContextSupplier == null)
		{
			facesContextSupplier = () -> FacesContext.getCurrentInstance();
		}
	}

	public String logout()
	{
		facesContextSupplier.get().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Goodbye!", null));
		return INDEX_PAGE;
	}

	public String login()
	{
		if (userService.isValidUser(credentials))
		{
			return WELCOME_PAGE;
		}
		facesContextSupplier.get().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid user name or password", null));
		return INDEX_PAGE;
	}

	public void setCredentials(Credentials credentials)
	{
		this.credentials = credentials;
	}
}
