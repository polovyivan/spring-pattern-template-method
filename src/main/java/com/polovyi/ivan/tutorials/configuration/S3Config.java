package com.polovyi.ivan.tutorials.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class S3Config {

    private final AWSCredentials credentials;

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Value("${cloud.aws.s3.endpoint}")
    private String s3BucketEndpoint;

    @Value("${cloud.aws.s3.with-path-style-access-enabled}")
    private Boolean isWithS3PathStyleAccessEnabled;


    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(s3BucketEndpoint, awsRegion))
                .withPathStyleAccessEnabled(isWithS3PathStyleAccessEnabled)
                .build();
    }
}
