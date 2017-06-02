package spittr.data;

import javax.websocket.Session;

import org.jboss.logging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;

import spittr.config.Spittle;

public class AlertServiceImpl implements AlertService {
	private JmsOperations jmsOperations;
	@Autowired
	public AlertServiceImpl(JmsOperations jmsOperations) {
		// TODO Auto-generated constructor stub
		this.jmsOperations= jmsOperations;
	}
	@Override
	public void sendSpittle(Spittle spittle) {
		jmsOperations.send("spittle.alert.queue", new MessageCreator(){
			
			public Message createMessage(Session session)
					throws JMSException {
					return session.createObjectMessage(spittle);
					}
			
		});
		jmsOperations.convertAndSend(spittle);
		
	}

}
