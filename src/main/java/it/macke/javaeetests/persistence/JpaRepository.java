package it.macke.javaeetests.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public abstract class JpaRepository<T>
{
	@PersistenceContext(name = "Demo")
	private EntityManager em;

	public JpaRepository()
	{}

	public JpaRepository(final EntityManager em)
	{
		this.em = em;
	}

	public JpaRepository(final String persistenceUnitName)
	{
		em = Persistence.createEntityManagerFactory(persistenceUnitName)
				.createEntityManager();
	}

	protected EntityManager getEntityManager()
	{
		return em;
	}
}
