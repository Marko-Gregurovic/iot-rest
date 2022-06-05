package hr.fer.iot.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
public class AmazonProperties {

    private String region;

    private String accessKey;

    private String secret;

    @NestedConfigurationProperty
    private MqttProperties mqttProperties;

    @NestedConfigurationProperty
    private DynamoDBProperties dynamoDBProperties;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public MqttProperties getMqttProperties() {
        return mqttProperties;
    }

    public void setMqttProperties(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    public DynamoDBProperties getDynamoDBProperties() {
        return dynamoDBProperties;
    }

    public void setDynamoDBProperties(DynamoDBProperties dynamoDBProperties) {
        this.dynamoDBProperties = dynamoDBProperties;
    }

}
