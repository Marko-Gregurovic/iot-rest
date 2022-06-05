package hr.fer.iot.rest.service;

import hr.fer.iot.rest.entities.TopicInfo;

import java.util.List;

public interface TopicInfoService {

    TopicInfo save(String topic, Double threshold);

    TopicInfo changeTurnedOnStatus(TopicInfo topicInfo);

    List<TopicInfo> findAll();

    TopicInfo findTopicInfo(String topic);

}
