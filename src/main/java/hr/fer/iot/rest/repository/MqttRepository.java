package hr.fer.iot.rest.repository;

import hr.fer.iot.rest.entities.MqttMessage;
import hr.fer.iot.rest.entities.TopicInfo;

import java.util.List;

public interface MqttRepository {

    MqttMessage save(MqttMessage message);

    TopicInfo save(TopicInfo topicInfo);

    List<MqttMessage> findLastNMinutes(Integer minutes);

    List<TopicInfo> findAll();

    TopicInfo findTopicInfo(String topic);

}
