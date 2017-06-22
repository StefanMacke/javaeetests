package it.macke.javaeetests.persistence;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.domain.UserName;
import it.macke.javaeetests.domain.UserRepository;

public class JpaUserRepository extends JpaRepository<User> implements UserRepository
{
	@Inject
	Logger logger;

	public JpaUserRepository()
	{
		super();
	}

	protected JpaUserRepository(final EntityManager em)
	{
		super(em);
	}

	@Override
	public Optional<User> findByUserName(final String userName)
	{
		try
		{
			final TypedQuery<User> query =
					getEntityManager().createQuery("SELECT u FROM Users u WHERE u.userName = :userName", User.class);
			query.setParameter("userName", new UserName(userName));
			return Optional.of(query.getSingleResult());
		}
		catch (final Exception e)
		{
			logger.error("Could not find user by user name.", e);
			return Optional.empty();
		}
	}

	@Override
	public void save(final User user)
	{
		getEntityManager().persist(user);
	}
}
