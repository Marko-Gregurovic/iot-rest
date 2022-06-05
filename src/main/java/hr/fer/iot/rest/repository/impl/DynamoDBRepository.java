package hr.fer.iot.rest.repository.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import hr.fer.iot.rest.entities.EntityType;
import hr.fer.iot.rest.entities.MqttMessage;
import hr.fer.iot.rest.entities.TopicInfo;
import hr.fer.iot.rest.repository.MqttRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Repository
public class DynamoDBRepository implements MqttRepository {

    private final DynamoDBMapper mapper;

    @Autowired
    public DynamoDBRepository(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public MqttMessage save(MqttMessage message) {
        mapper.save(message);

        return message;
    }

    @Override
    public TopicInfo save(TopicInfo topicInfo) {
        mapper.save(topicInfo);

        return topicInfo;
    }

    @Override
    public List<MqttMessage> findLastNMinutes(Integer minutes) {
        long startingTime = (new Date()).getTime() - (minutes * 60L * 1000L);
        long currentTime = (new Date()).getTime();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String startDate = dateFormatter.format(startingTime);
        String endDate = dateFormatter.format(currentTime);

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":entity_type", new AttributeValue().withS(EntityType.MESSAGE.toString()));
        expressionAttributeValues.put(":startDate", new AttributeValue().withS(startDate));
        expressionAttributeValues.put(":endDate", new AttributeValue().withS(endDate));

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("entity_type = :entity_type and message_timestamp between :startDate and :endDate")
            .withExpressionAttributeValues(expressionAttributeValues);

        return mapper.scan(MqttMessage.class, scanExpression).stream()
            .sorted(Comparator.comparing(MqttMessage::getTimestamp).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<TopicInfo> findAll() {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":entity_type", new AttributeValue().withS(EntityType.TOPIC_INFO.toString()));

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("entity_type = :entity_type")
            .withExpressionAttributeValues(expressionAttributeValues);

        return mapper.scan(TopicInfo.class, scanExpression);
    }

    @Override
    public TopicInfo findTopicInfo(String topic) {
        return mapper.load(TopicInfo.class, topic);
    }

}
