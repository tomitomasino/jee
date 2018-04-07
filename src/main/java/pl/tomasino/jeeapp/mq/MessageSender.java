package pl.tomasino.jeeapp.mq;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import lombok.extern.slf4j.Slf4j;
import pl.tomasino.jeeapp.utils.PropertiesHandler;

@Dependent
@Slf4j
public class MessageSender {

	@Inject
	PropertiesHandler props;

	public void sendMessage(String msg) {

		try {
			log.info("Message to be sent: " + msg);
			// Getting JMS connection from the server and starting it
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(props.getProperty("mqurl"));
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Creating a non transactional session to send/receive JMS message.
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Destination represents here our queue 'JCG_QUEUE' on the JMS
			// server.
			// The queue will be created automatically on the server.
			Destination destination = session.createQueue(props.getProperty("mqqueuqe"));

			// MessageProducer is used for sending messages to the queue.
			MessageProducer producer = session.createProducer(destination);

			// We will send a small text message saying 'Hello World!!!'
			TextMessage message = session.createTextMessage(msg);

			// Here we are sending our message!
			producer.send(message);

			log.info("Message sent: " + message.getText());
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
			log.error(e.getErrorCode());
		}
	}
}
