package it.macke.javaeetests.setup;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jProducer
{
	@Produces
	public Logger produceLogger(final InjectionPoint ip)
	{
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass().getName());
	}
}