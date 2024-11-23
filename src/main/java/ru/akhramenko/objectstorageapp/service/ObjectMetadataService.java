package ru.akhramenko.objectstorageapp.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

import ru.akhramenko.objectstorageapp.entity.ObjectMetadata;
import ru.akhramenko.objectstorageapp.repo.ObjectMetadataRepo;
import ru.akhramenko.objectstorageapp.utils.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ObjectMetadataService {

    @Autowired
    private final MinioClient minioClient;

    @Autowired
    private final ObjectMetadataRepo objectMetadataRepo;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public ObjectMetadata saveFile(MultipartFile file, String description) throws Exception {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setFilename(filename);
        metadata.setUrl("http://localhost:9000/" + bucketName + "/" + filename);
        metadata.setDescription(description);

        return objectMetadataRepo.save(metadata);
    }

    public List<ObjectMetadata> getAllFiles() {
        return objectMetadataRepo.findAll();
    }

    public void deleteFile(UUID id) throws Exception {
        ObjectMetadata metadata = objectMetadataRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(metadata.getFilename())
                .build());

        objectMetadataRepo.deleteById(metadata.getId());
    }

}
