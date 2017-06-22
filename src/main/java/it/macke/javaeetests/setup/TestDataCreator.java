package it.macke.javaeetests.setup;

import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import it.macke.javaeetests.domain.User;

public class TestDataCreator
{
	@PersistenceContext
	private EntityManager em;

	@Inject
	private Logger logger;

	@Transactional
	public void initializeDatabase(
			@Observes @Initialized(ApplicationScoped.class) final Object event)
			throws NamingException
	{
		logger.info("Initializing database");
		createTestData(em, logger::info);
	}

	public void createTestData(final EntityManager em, final Consumer<String> logger)
	{
		logger.accept("Deleting all users.");
		em.createQuery("DELETE FROM Users").executeUpdate();
		final User user = TestData.validUser();
		logger.accept("Creating valid user: " + user);
		em.persist(user);
	}
}
