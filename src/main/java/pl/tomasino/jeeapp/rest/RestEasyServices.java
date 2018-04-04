package pl.tomasino.jeeapp.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class RestEasyServices extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		resources.add(AsyncRESTService.class);
		resources.add(CompanyRESTService.class);
		return resources;
	}
}