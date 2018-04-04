package pl.tomasino.jeeapp.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import pl.tomasino.jeeapp.mq.MessageSender;

@Path("/message/{msg}")
@Produces(MediaType.TEXT_PLAIN)
public class AsyncRESTService {

	final static Logger logger = Logger.getLogger(AsyncRESTService.class);

	@Inject
	private MessageSender msgSender;

	@GET
	public String produceMessage(@PathParam("msg") String msg) {
		msgSender.sendMessage(msg);
		return "Sent to mq: " + msg;
	}

}
