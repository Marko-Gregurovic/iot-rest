package hr.fer.iot.rest.mqtt;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class SimpleAwsMqttTopic extends AWSIotTopic {

    private static final Logger LOGGER = Logger.getLogger(SimpleAwsMqttTopic.class.getName());

    private final Consumer<AWSIotMessage> messageConsumer;

    public SimpleAwsMqttTopic(String topic, AWSIotQos qos, Consumer<AWSIotMessage> messageConsumer) {
        super(topic, qos);
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        LOGGER.info("Message received: " + message.getStringPayload() + " on topic: " + getTopic());

        messageConsumer.accept(message);
    }

}

