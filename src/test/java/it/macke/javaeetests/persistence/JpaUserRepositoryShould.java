package it.macke.javaeetests.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.domain.UserName;

@DisplayName("JpaUserRepository should")
public class JpaUserRepositoryShould
{
	private JpaUserRepository sut;
	private EntityManager em;
	private TypedQuery<User> query;
	private Logger logger;

	@BeforeEach
	@SuppressWarnings("unchecked")
	public void setup() throws Exception
	{
		em = mock(EntityManager.class);
		query = mock(TypedQuery.class);
		logger = mock(Logger.class);
		sut = new JpaUserRepository(em);
		sut.logger = logger;
	}

	private void givenTheUserExists(final User existingUser)
	{
		when(query.getSingleResult()).thenReturn(existingUser);
		when(em.createQuery(any(String.class), eq(User.class))).thenReturn(query);
	}

	private void givenTheUserDoesNotExist()
	{
		when(query.getSingleResult()).thenThrow(NoResultException.class);
		when(em.createQuery(any(String.class), eq(User.class))).thenReturn(query);
	}

	@Test
	@DisplayName("find an existing user by its user name")
	public void findExistingUserByItsUserName() throws Exception
	{
		final User existingUser = new User();
		existingUser.setUserName(new UserName("existinguser"));
		givenTheUserExists(existingUser);

		final Optional<User> result = sut.findByUserName("existinguser");

		assertThat(result.isPresent(), is(true));
		assertThat(result.get(), is(existingUser));
	}

	@Test
	@DisplayName("not find a non-existing user by its user name")
	public void notFindNonExistingUserByItsUserName()
	{
		givenTheUserDoesNotExist();

		final Optional<User> result = sut.findByUserName("nonexistinguser");

		assertThat(result.isPresent(), is(false));
	}

	@Test
	@DisplayName("save a user to the database")
	public void saveUserToDatabase()
	{
		final User user = new User();
		sut.save(user);
		verify(em).persist(user);
	}
}
