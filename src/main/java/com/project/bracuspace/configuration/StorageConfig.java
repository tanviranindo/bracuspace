package com.project.bracuspace.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${storage.key}")
    private String accessKey;

    @Value("${storage.secret}")
    private String secretKey;

    @Value("${storage.endpoint}")
    private String endPoint;

    @Value("${storage.region}")
    private String region;


    //AWS S3 WITH CREDENTIALS
//        @Bean
//    public AmazonS3 s3Client() {
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(region).build();
//    }
    //DIGITAL OCEAN WITH END POINTS
    @Bean
    public AmazonS3 s3client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region)).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }
}
