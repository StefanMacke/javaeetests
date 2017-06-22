package it.macke.javaeetests.api;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;

@ApplicationPath(DemoApplication.REST_PATH)
public class DemoApplication extends Application
{
	private static String PROTOCOL = "http";
	private static String HOSTNAME = "localhost";
	private static int PORT = 18080;
	static String CONTEXT_PATH = "Demo";
	static final String REST_PATH = "rest";

	@Inject
	private Logger logger;

	void initialize(
			@Observes @Initialized(ApplicationScoped.class) final Object event)
			throws NamingException
	{
		setCurrentHostname();
		setCurrentContextPath(event);
	}

	private void setCurrentContextPath(final Object event)
	{
		final ServletContext servletContext = (ServletContext) event;
		final String contextPath = servletContext.getContextPath();
		setContextPath(contextPath);
		logger.info("Current context path set to: " + contextPath);
	}

	private void setCurrentHostname()
	{
		try
		{
			final String hostname = InetAddress.getLocalHost().getHostName();
			setHostname(hostname);
			logger.info("Current hostname set to: " + hostname);
		}
		catch (final UnknownHostException e)
		{
			logger.warn("Hostname could not be determined. Using localhost.");
		}
	}

	public static final String getRestUrl()
	{
		return String.format("%s://%s:%s/%s/%s",
				PROTOCOL, HOSTNAME, PORT, CONTEXT_PATH, REST_PATH);
	}

	public static void setContextPath(final String contextPfad)
	{
		CONTEXT_PATH = removeSlashes(contextPfad);
	}

	public static void setHostname(final String hostname)
	{
		HOSTNAME = hostname;
	}

	public static void setPort(final int port)
	{
		PORT = port;
	}

	private static String removeSlashes(final String pfad)
	{
		return pfad.replaceAll("/", "");
	}
}
