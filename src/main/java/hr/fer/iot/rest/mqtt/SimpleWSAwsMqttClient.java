package hr.fer.iot.rest.mqtt;

import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotTimeoutException;
import com.amazonaws.services.iot.client.auth.Credentials;
import com.amazonaws.services.iot.client.auth.StaticCredentialsProvider;

import java.util.logging.Logger;

public class SimpleWSAwsMqttClient implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(SimpleWSAwsMqttClient.class.getName());

    private String endpoint;

    private String clientId;

    private String awsAccessKeyId;

    private String awsSecretAccessKey;

    private AWSIotMqttClient client;

    private Integer timeout = 15000;

    private SimpleWSAwsMqttClient() {
    }

    public static SimpleAwsMqttClientBuilder getBuilder() {
        return new SimpleAwsMqttClientBuilder();
    }

    public AWSIotMqttClient createConnection() throws AWSIotException, AWSIotTimeoutException {
        if (client != null && client.getConnectionStatus() == AWSIotConnectionStatus.CONNECTED) {
            throw new AWSIotException("Client is already connected");
        }

        final Credentials credentials = new Credentials(awsAccessKeyId, awsSecretAccessKey);
        client = new AWSIotMqttClient(endpoint, clientId, new StaticCredentialsProvider(credentials), "us-east-1");

        client.setCleanSession(true);
        client.connect(timeout);

        return client;
    }

    @Override
    public void close() throws Exception {
        if (client.getConnectionStatus() == AWSIotConnectionStatus.DISCONNECTED) {
            LOGGER.warning("Client is not connected, no need to disconnect");
        } else {
            client.disconnect();
        }
    }

    public static class SimpleAwsMqttClientBuilder {

        private final SimpleWSAwsMqttClient simpleWSAwsMqttClient;

        private SimpleAwsMqttClientBuilder() {
            simpleWSAwsMqttClient = new SimpleWSAwsMqttClient();
        }

        public SimpleAwsMqttClientBuilder withClientId(String clientId) {
            simpleWSAwsMqttClient.clientId = clientId;

            return this;
        }

        public SimpleAwsMqttClientBuilder withEndpoint(String endpoint) {
            simpleWSAwsMqttClient.endpoint = endpoint;

            return this;
        }

        public SimpleAwsMqttClientBuilder withAwsAccessKeyId(String awsAccessKeyId) {
            simpleWSAwsMqttClient.awsAccessKeyId = awsAccessKeyId;

            return this;
        }

        public SimpleAwsMqttClientBuilder withAwsSecretAccessKey(String awsSecretAccessKey) {
            simpleWSAwsMqttClient.awsSecretAccessKey = awsSecretAccessKey;

            return this;
        }

        public SimpleAwsMqttClientBuilder withTimeout(Integer timeout) {
            simpleWSAwsMqttClient.timeout = timeout;

            return this;
        }

        public SimpleWSAwsMqttClient build() {
            return simpleWSAwsMqttClient;
        }

    }

}
