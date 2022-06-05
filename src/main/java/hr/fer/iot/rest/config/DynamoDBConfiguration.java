package hr.fer.iot.rest.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import hr.fer.iot.rest.properties.AmazonProperties;
import hr.fer.iot.rest.properties.MqttProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfiguration {

    @Bean
    public DynamoDBMapper mapper(AmazonProperties amazonProperties) {
        return new DynamoDBMapper(amazonDynamoDBConfig(amazonProperties));
    }

    public AmazonDynamoDB amazonDynamoDBConfig(AmazonProperties amazonProperties) {
        return AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(amazonProperties.getDynamoDBProperties().getEndpoint(),
                    amazonProperties.getRegion()))
            .withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(amazonProperties.getAccessKey(), amazonProperties.getSecret())))
            .build();
    }

}
