package ru.akhramenko.objectstorageapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.akhramenko.objectstorageapp.entity.ObjectMetadata;
import ru.akhramenko.objectstorageapp.service.ObjectMetadataService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/objects")
public class ObjectMetadataController {

    @Autowired
    private final ObjectMetadataService objectMetadataService;

    @PostMapping("")
    public ResponseEntity<ObjectMetadata> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {
        try {
            return ResponseEntity.ok(objectMetadataService.saveFile(file, description));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("")
    public List<ObjectMetadata> getAllFiles() {
        return objectMetadataService.getAllFiles();
    }
}
