package it.macke.javaeetests.system;

import java.io.File;

import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.google.common.base.MoreObjects;

import it.macke.javaeetests.api.SessionResource;
import it.macke.javaeetests.controller.LoginController;
import it.macke.javaeetests.domain.User;
import it.macke.javaeetests.persistence.JpaUserRepository;
import it.macke.javaeetests.service.UserService;
import it.macke.javaeetests.setup.Slf4jProducer;
import it.macke.javaeetests.setup.TestData;
import it.macke.javaeetests.viewmodel.Credentials;

public class Deployments
{
	private static final String WEBAPP_SRC = "src/main/webapp";

	public static WebArchive createBasicDeployment()
	{
		return ShrinkWrap
				.create(WebArchive.class)
				.addPackages(true, Filters.exclude(".*(Should|Properties|Spec|IntegrationTest).*"),
						MoreObjects.class.getPackage(),
						User.class.getPackage(), // domain
						Credentials.class.getPackage(), // viewmodel
						JpaUserRepository.class.getPackage(), // persistence
						UserService.class.getPackage(), // service
						TestData.class.getPackage()) // setup
				.addClass(Slf4jProducer.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsResource("arquillian/persistence.xml", "META-INF/persistence.xml");
	}

	public static WebArchive createWebDeployment()
	{
		return createBasicDeployment()
				.addPackage(LoginController.class.getPackage()) // controller
				.addPackage(SessionResource.class.getPackage()) // api
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class)
						.importDirectory(WEBAPP_SRC)
						.as(GenericArchive.class),
						"/", Filters.include(".*\\.(xhtml|css|jpg)$"));
	}
}
