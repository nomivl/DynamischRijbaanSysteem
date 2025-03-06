package org.dynamischrijbaansysteem;
import javax.jms.*;

import com.dynamischrijbaansysteem.services.LaneService;
import com.dynamischrijbaansysteem.services.LaneStatusService;
import com.dynamischrijbaansysteem.services.LaneTrafficService;
import com.dynamischrijbaansysteem.utils.ConfigLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.dynamischrijbaansysteem.models.LaneTraffic;


public class TrafficDataConsumer {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "traffic.data";
    private static final ConfigLoader config = new ConfigLoader();

    // TO DO: services injecten.
    private static final LaneTrafficService  laneTrafficService = new LaneTrafficService();
    private static final LaneStatusService laneStatusService = new LaneStatusService();

    private static final LaneService laneService = new LaneService();

    public static void start() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory(config.getProperty("activemq.url", BROKER_URL));
        ActiveMQConnectionFactory amqConnectionFactory  = (ActiveMQConnectionFactory) factory;

        amqConnectionFactory.setUserName(config.getProperty("karaf.username", "karaf"));
        amqConnectionFactory.setPassword(config.getProperty("karaf.password","karaf"));
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(config.getProperty("activemq.queue",QUEUE_NAME));
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new TrafficMessageListener());

        System.out.println("Verbonden met ActiveMQ en luistert op " + QUEUE_NAME);
    }

    static class TrafficMessageListener implements MessageListener {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onMessage(Message message) {
            try {
                if (message instanceof TextMessage) {
                    String json = ((TextMessage) message).getText();
                    LaneTraffic laneTraffic = objectMapper.readValue(json, LaneTraffic.class);
                    laneTraffic.setLaneStatus(laneStatusService.determineExtraLaneStatus(laneTraffic.getDensity()));
                    boolean dynamicLaneControl = laneService.getLaneById(laneTraffic.getLaneId()).getDynamicLaneControl();
                    laneTraffic.setComment("System");
                    if (dynamicLaneControl) {
                        laneTrafficService.insertLaneTraffic(laneTraffic);
                    }
                    System.out.println(json);
                } else {
                    System.err.println("Onbekend bericht type ontvangen: "+ message.getClass().getSimpleName());
                }
            } catch (Exception e) {
                System.err.println("Fout bij verwerken van bericht: " + e.getMessage());
            }
        }
    }
}
