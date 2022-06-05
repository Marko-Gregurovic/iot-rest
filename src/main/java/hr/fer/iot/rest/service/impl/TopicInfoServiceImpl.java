package hr.fer.iot.rest.service.impl;

import hr.fer.iot.rest.entities.TopicInfo;
import hr.fer.iot.rest.repository.MqttRepository;
import hr.fer.iot.rest.service.TopicInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicInfoServiceImpl implements TopicInfoService {

    private final MqttRepository mqttRepository;

    public TopicInfoServiceImpl(MqttRepository mqttRepository) {
        this.mqttRepository = mqttRepository;
    }

    @Override
    public TopicInfo save(String topic, Double threshold) {
        TopicInfo topicInfo = mqttRepository.findTopicInfo(topic);
        if (topicInfo == null) {
            topicInfo = new TopicInfo();
            topicInfo.setTopic(topic);
            topicInfo.setThreshold(threshold);
        } else {
            topicInfo.setThreshold(threshold);
        }

        return mqttRepository.save(topicInfo);
    }

    @Override
    public TopicInfo changeTurnedOnStatus(TopicInfo topicInfo) {
        topicInfo.setTurnedOn(!Boolean.TRUE.equals(topicInfo.isTurnedOn()));

        return mqttRepository.save(topicInfo);
    }

    @Override
    public List<TopicInfo> findAll() {
        return mqttRepository.findAll();
    }

    @Override
    public TopicInfo findTopicInfo(String topic) {
        return mqttRepository.findTopicInfo(topic);
    }

}
