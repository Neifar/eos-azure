package com.org.proto.catalog.client;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AzureBlobClient {

    @Value("${cloud.azure.connection.string}")
    private String connectionString;

    @Value("${application.bucket.name}")
    private String containerName;

    private BlobServiceClient blobServiceClient;
    private BlobContainerClient blobContainerClient;

    public AzureBlobClient() {
        blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    public String uploadFile(String fileName, MultipartFile file) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        try (InputStream data = file.getInputStream()) {
            blobClient.upload(data, file.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Azure", e);
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
            blobClient.delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAllFiles() {
        try {
            blobContainerClient.listBlobs().forEach(blobItem -> {
                BlobClient blobClient = blobContainerClient.getBlobClient(blobItem.getName());
                blobClient.delete();
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
