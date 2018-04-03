package pl.tomasino.jeeapp.mq;

import javax.enterprise.context.Dependent;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

@Dependent
public class MessageSender {

	final static Logger logger = Logger.getLogger(MessageSender.class);

	// URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server
	// is on localhost
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// default broker URL is : tcp://localhost:61616"
	private static String subject = "tomq";

	public void sendMessage(String msg) {

		try {
			logger.debug("Message to be sent: " + msg);

			// Getting JMS connection from the server and starting it
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Creating a non transactional session to send/receive JMS message.
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Destination represents here our queue 'JCG_QUEUE' on the JMS
			// server.
			// The queue will be created automatically on the server.
			Destination destination = session.createQueue(subject);

			// MessageProducer is used for sending messages to the queue.
			MessageProducer producer = session.createProducer(destination);

			// We will send a small text message saying 'Hello World!!!'
			TextMessage message = session.createTextMessage(msg);

			// Here we are sending our message!
			producer.send(message);

			logger.debug("Message sent: " + message.getText());
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}
