package hr.fer.iot.rest.service.impl;

import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTimeoutException;
import hr.fer.iot.rest.entities.TopicInfo;
import hr.fer.iot.rest.mqtt.SimpleAwsMqttTopic;
import hr.fer.iot.rest.mqtt.SimpleWSAwsMqttClient;
import hr.fer.iot.rest.properties.AmazonProperties;
import hr.fer.iot.rest.service.MessageService;
import hr.fer.iot.rest.service.MqttMessageService;
import hr.fer.iot.rest.service.TopicInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Logger;

@Service
public class AwsMqttService implements MqttMessageService {

    private static final Logger LOGGER = Logger.getLogger(AwsMqttService.class.getName());

    private final SimpleWSAwsMqttClient simpleWSAwsMqttClient;

    private final AmazonProperties amazonProperties;

    private final MessageService messageService;

    private final TopicInfoService topicInfoService;

    private AWSIotMqttClient awsIotMqttClient;

    public AwsMqttService(AmazonProperties amazonProperties, MessageService messageService,
        TopicInfoService topicInfoService) {
        this.amazonProperties = amazonProperties;
        this.messageService = messageService;
        this.topicInfoService = topicInfoService;
        this.simpleWSAwsMqttClient = SimpleWSAwsMqttClient.getBuilder()
            .withClientId(amazonProperties.getMqttProperties().getClientId())
            .withEndpoint(amazonProperties.getMqttProperties().getEndpoint())
            .withAwsAccessKeyId(amazonProperties.getAccessKey())
            .withAwsSecretAccessKey(amazonProperties.getSecret())
            .build();
    }

    @PostConstruct
    public void init() throws AWSIotException, AWSIotTimeoutException {
        awsIotMqttClient = simpleWSAwsMqttClient.createConnection();

        for (String topic : amazonProperties.getMqttProperties().getSubscribeTopics()) {
            final SimpleAwsMqttTopic awsIotTopic = new SimpleAwsMqttTopic(topic, AWSIotQos.QOS0, this::messageReceived);
            awsIotMqttClient.subscribe(awsIotTopic);
        }
    }

    @PreDestroy
    public void destroy() throws AWSIotException {
        awsIotMqttClient.disconnect();
    }

    @Override
    public void publish(String topic, String message) {
        final AWSIotMessage awsMessage = new AWSIotMessage(topic, AWSIotQos.QOS0, message);

        try {
            if (awsIotMqttClient.getConnectionStatus() == AWSIotConnectionStatus.DISCONNECTED) {
                awsIotMqttClient.connect(amazonProperties.getMqttProperties().getTimeout());

                LOGGER.info("Not connected to AWS. Connection established.");
            }
            awsIotMqttClient.publish(awsMessage);
        } catch (AWSIotException | AWSIotTimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void messageReceived(AWSIotMessage message) {
        messageService.save(message);

        try {
            final String roomNumber = message.getTopic().split("/")[1];
            final String actuatorTopic = String.format("room/%s/act", roomNumber);
            final TopicInfo topicInfo = topicInfoService.findTopicInfo(actuatorTopic);

            if (topicInfo != null) {
                if (Double.valueOf(message.getStringPayload()).compareTo(topicInfo.getThreshold()) <= 0
                    && Boolean.FALSE.equals(topicInfo.isTurnedOn())) {
                    publish(actuatorTopic, "true");

                    topicInfoService.changeTurnedOnStatus(topicInfo);
                    LOGGER.info(
                        "Message published: false on topic: on topic: " + message.getTopic());
                } else if (Double.valueOf(message.getStringPayload()).compareTo(topicInfo.getThreshold()) > 0
                    && Boolean.TRUE.equals(topicInfo.isTurnedOn())) {
                    publish(actuatorTopic, "false");

                    topicInfoService.changeTurnedOnStatus(topicInfo);
                    LOGGER.info(
                        "Message published: false on topic: " + message.getTopic());
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.info("invalid message format received: " + message.getStringPayload());
        }
    }

}
