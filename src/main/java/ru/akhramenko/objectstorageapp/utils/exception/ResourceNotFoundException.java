package ru.akhramenko.objectstorageapp.utils.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    private static final String ID_PREFIX = "Resource wasn't found by id: %s";

    public ResourceNotFoundException(UUID id) {
        super(String.format(ID_PREFIX, id));
    }
}
