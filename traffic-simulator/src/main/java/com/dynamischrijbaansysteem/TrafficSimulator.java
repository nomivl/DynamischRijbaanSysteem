package com.dynamischrijbaansysteem;
import com.dynamischrijbaansysteem.models.TrafficData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

@Component(immediate = true)
public class TrafficSimulator {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "traffic.data";
    @Activate
    public void start() {
        System.out.println("🚦 Traffic Simulator gestart! Verbind met ActiveMQ...");

        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
            ActiveMQConnectionFactory amqConnectionFactory  = (ActiveMQConnectionFactory) factory;
            amqConnectionFactory.setUserName("karaf");
            amqConnectionFactory.setPassword("karaf");
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(QUEUE_NAME);

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            ObjectMapper objectMapper = new ObjectMapper();
            // Simuleer verkeersdata
            for(int i = 1; i<=10; i++) {
                TrafficData traffic = new TrafficData(1, i, System.currentTimeMillis());
                String jsonMessage = objectMapper.writeValueAsString(traffic);
                String message = "Traffic update " + jsonMessage;
                TextMessage objectMessage = session.createTextMessage(jsonMessage);
                producer.send(objectMessage);
                System.out.println("Verzonden: " + message);
                Thread.sleep(1000);
            }

            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
