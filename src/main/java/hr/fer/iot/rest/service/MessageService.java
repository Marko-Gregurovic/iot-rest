package hr.fer.iot.rest.service;

import com.amazonaws.services.iot.client.AWSIotMessage;
import hr.fer.iot.rest.entities.MqttMessage;

import java.util.List;

public interface MessageService {

    void save(AWSIotMessage message);

    List<MqttMessage> findLastNMinutes(Integer minutes);

}
