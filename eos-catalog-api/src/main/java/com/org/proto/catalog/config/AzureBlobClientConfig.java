package com.org.proto.catalog.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
public class AzureBlobClientConfig {

    @Value("${cloud.azure.connection.string}")
    private String connectionString;

    @Value("${application.bucket.name}")
    private String containerName;

    @Bean
    public BlobServiceClient blobServiceClient() {
        if (connectionString == null || connectionString.isEmpty()) {
            throw new IllegalArgumentException("Azure connection string is not set.");
        }
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    @Bean
    public BlobContainerClient blobContainerClient() {
        // Initialize the container client with the BlobServiceClient and container name
        return blobServiceClient().getBlobContainerClient(containerName);
    }
}