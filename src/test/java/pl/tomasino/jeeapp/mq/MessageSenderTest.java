package pl.tomasino.jeeapp.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.tomasino.jeeapp.MyTestWatcher;
import pl.tomasino.jeeapp.utils.PropertiesHandler;

public class MessageSenderTest {

	private String msg = "test message";

	@Mock
	PropertiesHandler props;

	@InjectMocks
	MessageSender msgSender;

	@Before
	public void setUp() {
		msgSender = new MessageSender();
		MockitoAnnotations.initMocks(this);
		Mockito.when(props.getProperty("mqurl")).thenReturn("tcp://192.168.233.175:61616");
		Mockito.when(props.getProperty("mqqueuqe")).thenReturn("tomq");
	}
	
	@Rule
    public TestWatcher watchman = new MyTestWatcher();

	@Test
	public void sentMessageShouldBeInQueue() throws Exception {
		msgSender.sendMessage(msg);
		TextMessage m = getMessage();
		Assert.assertEquals(msg, m.getText());
	}

	private TextMessage getMessage() {

		TextMessage message = null;
		try {

			PropertiesHandler props = new PropertiesHandler();

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
			message = (TextMessage) consumer.receive();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return message;
	}
}
