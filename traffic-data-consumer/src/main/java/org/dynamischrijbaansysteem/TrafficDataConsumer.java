package org.dynamischrijbaansysteem;
import javax.jms.*;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.dynamischrijbaansysteem.models.TrafficData;


public class TrafficDataConsumer {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "traffic.data";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        ActiveMQConnectionFactory amqConnectionFactory  = (ActiveMQConnectionFactory) factory;
        amqConnectionFactory.setUserName("karaf");
        amqConnectionFactory.setPassword("karaf");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(destination);


        System.out.println("Consumer verbonden met ActiveMQ en luistert op " + QUEUE_NAME);
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                String json = ((TextMessage) message).getText();
                TrafficData trafficData = objectMapper.readValue(json, TrafficData.class);
                System.out.println("Ontvangen bericht: " + json);
            } else {
                System.err.println("Onbekend berichttype ontvangen: " + message.getClass().getSimpleName());
            }
        }

        // connection.close();
    }
}
