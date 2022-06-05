package hr.fer.iot.rest.service;

import com.amazonaws.services.iot.client.AWSIotMessage;
import org.springframework.stereotype.Service;

@Service
public interface MqttMessageService {

    void publish(String topic, String message);

    void messageReceived(AWSIotMessage message);

}
