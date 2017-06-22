package it.macke.javaeetests.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import it.macke.javaeetests.domain.Password;
import it.macke.javaeetests.domain.Salt;
import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.domain.UserName;
import it.macke.javaeetests.domain.UserRepository;
import it.macke.javaeetests.viewmodel.Credentials;

@RequestScoped
public class UserService
{
	@Inject
	UserRepository userRepository;

	@Inject
	MailService mailService;

	public boolean isValidUser(final String userName, final String password)
	{
		return userRepository.findByUserName(userName)
				.map(user -> user.isCorrectPassword(password))
				.orElse(false);
	}

	public boolean isValidUser(final Credentials credentials)
	{
		return isValidUser(credentials.getUserName(), credentials.getPassword());
	}

	public User createUser(final String userName, final String password) throws Exception
	{
		final User user = new User();
		user.setUserName(new UserName(userName));
		user.setSalt(Salt.generate());
		user.setPassword(new Password(password));
		userRepository.save(user);
		mailService.notifyAdmin("New user created: " + user.getUserName().getValue());
		return user;
	}
}
