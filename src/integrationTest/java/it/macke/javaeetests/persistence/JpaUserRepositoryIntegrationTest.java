package it.macke.javaeetests.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.setup.TestData;
import it.macke.javaeetests.setup.TestDataCreator;

@DisplayName("Integration test for JpaUserRepository")
public class JpaUserRepositoryIntegrationTest
{
	private JpaUserRepository sut;
	private static Logger logger = LoggerFactory.getLogger(JpaUserRepositoryIntegrationTest.class);

	@BeforeAll
	public static void setupClass()
	{
		final EntityManager em = RepositoryIntegrationTest.getEntityManager();
		final EntityTransaction t = em.getTransaction();
		t.begin();
		new TestDataCreator().createTestData(em, logger::info);
		t.commit();
	}

	@BeforeEach
	public void setup()
	{
		sut = new JpaUserRepository(RepositoryIntegrationTest.getEntityManager());
	}

	@Test
	@DisplayName("find an existing user")
	public void findExistingUser()
	{
		final User expectedUser = TestData.validUser();
		final User actualUser = sut.findByUserName(expectedUser.getUserName().getValue()).get();
		assertAll("user's attributes should match expected values",
				() -> assertThat(actualUser.getUserName(), is(expectedUser.getUserName())),
				() -> assertThat(actualUser.getPassword(), is(expectedUser.getPassword())),
				() -> assertThat(actualUser.getSalt(), is(expectedUser.getSalt())));
	}
}
