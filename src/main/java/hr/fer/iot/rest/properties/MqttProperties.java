package hr.fer.iot.rest.properties;

import java.util.List;

public class MqttProperties {

    private String endpoint;

    private String clientId;

    private Integer timeout;

    private List<String> subscribeTopics;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public List<String> getSubscribeTopics() {
        return subscribeTopics;
    }

    public void setSubscribeTopics(List<String> subscribeTopics) {
        this.subscribeTopics = subscribeTopics;
    }

}
