package ru.akhramenko.objectstorageapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhramenko.objectstorageapp.entity.ObjectMetadata;

import java.util.UUID;

@Repository
public interface ObjectMetadataRepo extends JpaRepository<ObjectMetadata, UUID> {
}
