package it.macke.javaeetests.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import it.macke.javaeetests.service.UserService;
import it.macke.javaeetests.viewmodel.Credentials;

@Path(SessionResource.PATH)
public class SessionResource
{
	public static final String PATH = "/sessions";

	@Inject
	UserService userService;

	@Inject
	Logger logger;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(
			final Credentials credentials)
	{
		logger.info("Trying to log in: " + credentials);
		if (credentials == null)
		{
			return Response
					.status(Response.Status.BAD_REQUEST)
					.build();
		}

		if (userService.isValidUser(credentials))
		{
			return Response
					.status(Response.Status.OK)
					.entity(true)
					.build();
		}
		else
		{
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.entity(false)
					.build();
		}
	}
}
