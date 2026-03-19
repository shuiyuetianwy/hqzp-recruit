package com.hqzp.recruit.file.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties props;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials credentials =
                new BasicAWSCredentials(props.getAccessKey(), props.getSecretKey());

        // Support both AWS and S3-compatible endpoints (MinIO, Cloudflare R2, etc.)
        boolean isAwsEndpoint = props.getEndpoint().contains("amazonaws.com");

        if (isAwsEndpoint) {
            return AmazonS3ClientBuilder.standard()
                    .withRegion(props.getRegion())
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
        } else {
            return AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    props.getEndpoint(), props.getRegion()))
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withPathStyleAccessEnabled(true)
                    .build();
        }
    }
}
