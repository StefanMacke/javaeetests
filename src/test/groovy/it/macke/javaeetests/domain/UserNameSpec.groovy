import spock.lang.Specification
import it.macke.javaeetests.domain.UserName
import it.macke.javaeetests.domain.InvalidUserNameException

class UserNameSpec extends Specification
{
	def "valid user names are accepted"(String userName)
	{
		expect:
		UserName.isValidUserName(userName)

		where:
		userName << [ "asdf", "user1", "anotherreallylongusername" ]
 	}

	def "invalid user names are not accepted"(String userName, String message)
	{
		when:
		new UserName(userName)

		then:
		def e = thrown(InvalidUserNameException)
		e.message == "Invalid user name: " + message

		where:
		userName | message
		"asd"    | "too short"
		"ASDF"   | "only lower case letters allowed"
	}
}