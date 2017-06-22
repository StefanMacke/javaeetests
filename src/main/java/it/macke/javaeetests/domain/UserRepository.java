package it.macke.javaeetests.domain;

import java.util.Optional;

public interface UserRepository
{
	Optional<User> findByUserName(String userName);

	void save(User user);
}