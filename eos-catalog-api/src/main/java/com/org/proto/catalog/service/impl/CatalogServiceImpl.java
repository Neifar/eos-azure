package com.org.proto.catalog.service.impl;

import com.org.proto.catalog.client.AzureBlobClient;
import com.org.proto.catalog.model.CatalogItem;
import com.org.proto.catalog.repository.CatalogItemRepository;
import com.org.proto.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogItemRepository repository;

    @Autowired
    private AzureBlobClient client;

    @Override
    public ResponseEntity addItem(final CatalogItem catalogItem) {
        return ResponseEntity.ok().body(repository.save(catalogItem));
    }

    @Override
    public ResponseEntity addImage(final Long id, final MultipartFile multipartFile) {
        Optional<CatalogItem> response = repository.findById(id);
        if (response.isPresent()) {
            String imageUrl = client.uploadFile(String.valueOf(response.get().getId()), multipartFile);
            CatalogItem item = response.get();
            item.setImageUrl(imageUrl);
            return ResponseEntity.ok().body(repository.save(item));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity getItem() {
        List<CatalogItem> response = repository.findAll();
        return response.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity getItem(final Long id) {
        Optional<CatalogItem> response = repository.findById(id);
        return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity updateItem(final CatalogItem catalogItem) {
        Optional<CatalogItem> item = repository.findById(catalogItem.getId());
        if (!item.isPresent()) {
            return ResponseEntity.badRequest().body("Id is invalid");
        }
        return ResponseEntity.ok().body(repository.save(catalogItem));
    }

    @Override
    public ResponseEntity updateImage(final Long id, final MultipartFile multipartFile) {
        Optional<CatalogItem> catalogItemOpt = repository.findById(id);
        if (catalogItemOpt.isPresent()) {
            CatalogItem catalogItem = catalogItemOpt.get();
            if (!multipartFile.isEmpty()) {
                client.deleteFile(String.valueOf(catalogItem.getId()));
                String imageUrl = client.uploadFile(String.valueOf(catalogItem.getId()), multipartFile);
                catalogItem.setImageUrl(imageUrl);
            }
            return ResponseEntity.ok().body(repository.save(catalogItem));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public void removeItem() {
        if (client.deleteAllFiles()) {
            repository.deleteAll();
        }
    }

    @Override
    public void removeItem(final Long id) {
        if (client.deleteFile(String.valueOf(id))) {
            repository.deleteById(id);
        }
    }
}