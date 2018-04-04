package pl.tomasino.jeeapp.mq;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import pl.tomasino.jeeapp.utils.PropertiesHandler;

@Singleton
@Startup
public class QueueReader {

	final static Logger logger = Logger.getLogger(QueueReader.class);

	@Inject
	PropertiesHandler props;

	@Schedule(hour = "*", minute = "*", second = "*/10", info = "Every 10 seconds timer")
	public void readQueue() {

		readMessages();

	}

	public void readMessages() {

		try {

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
			MessageConsumer consumer = session.createConsumer(destination);

			// read a message from the queue destination
			Message message = consumer.receive(10);

			// check if a message was received
			if (message != null) {
				// cast the message to the correct type
				TextMessage textMessage = (TextMessage) message;

				// retrieve the message content
				String text = textMessage.getText();
				logger.info("consumer received message with text=" + text);
			}

			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}
