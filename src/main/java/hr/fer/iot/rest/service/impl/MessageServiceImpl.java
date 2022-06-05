package hr.fer.iot.rest.service.impl;

import com.amazonaws.services.iot.client.AWSIotMessage;
import hr.fer.iot.rest.entities.MqttMessage;
import hr.fer.iot.rest.repository.MqttRepository;
import hr.fer.iot.rest.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MqttRepository mqttRepository;

    @Autowired
    public MessageServiceImpl(MqttRepository mqttRepository) {
        this.mqttRepository = mqttRepository;
    }

    @Override
    public void save(AWSIotMessage message) {
        final MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setTopic(message.getTopic());
        mqttMessage.setPayload(message.getStringPayload());

        mqttRepository.save(mqttMessage);
    }

    @Override
    public List<MqttMessage> findLastNMinutes(Integer minutes) {
        return mqttRepository.findLastNMinutes(minutes);
    }

}
