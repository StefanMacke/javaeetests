package it.macke.javaeetests.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class RepositoryIntegrationTest
{
	public static final String PERSISTENCE_UNIT_NAME = "IntegrationTest";

	public static final EntityManager getEntityManager()
	{
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
				.createEntityManager();
	}
}
