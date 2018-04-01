package pl.tomasino.jeeapp.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.tomasino.jeeapp.model.Company;
import pl.tomasino.jeeapp.service.DBManager;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyRESTService {

	@Inject
	private DBManager dbManager;

	@GET
	public List<Company> getCompanies() {
		return dbManager.getAllFromDB();
	}

}
