package hr.fer.iot.rest.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;

@DynamoDBTable(tableName = "IotTable")
public class TopicInfo implements Serializable {

    private String topic;

    private Double threshold;

    private Boolean turnedOn = false;

    private String entityType = EntityType.TOPIC_INFO.toString();

    @DynamoDBHashKey(attributeName = "id")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @DynamoDBAttribute(attributeName = "threshold")
    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @DynamoDBAttribute(attributeName = "turned_on")
    public Boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(Boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    @DynamoDBRangeKey(attributeName = "entity_type")
    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

}
