package pl.tomasino.jeeapp.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import pl.tomasino.jeeapp.mq.MessageSender;

@Path("/message")
public class AsyncRESTService {

	@Inject
	private MessageSender msgSender;

	@GET
	public void produceMessage() {
		msgSender.sendMessage("test msg");

	}

}
