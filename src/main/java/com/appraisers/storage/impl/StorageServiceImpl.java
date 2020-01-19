package com.appraisers.storage.impl;

import com.appraisers.storage.*;
import com.appraisers.storage.dto.PlatformStoringItemDto;
import com.appraisers.storage.local.StorageType;
import com.appraisers.storage.local.StorageUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Autowired
    private List<PlatformStorageStrategy> platformStorageStrategies;

    //We determine the path here.  the other service only stores.
    @Autowired
    private List<PathConstructionStrategy> pathConstructionStrategies;

    @Override
    public Optional<StoredItemDto> create(StoringItemDto storingItemDto) throws NotSupportedException, IOException {

        String sanitizedFileName = StorageUtils.sanitizePathComponent(storingItemDto.getOriginalFileName());
        String domainIdentifier = StorageUtils.sanitizePathComponent(storingItemDto.getDomainIdentifier());
        String mime = storingItemDto.getMimeType();
        PlatformStorageStrategy service = getService(storingItemDto.getOriginalFileName());

        String[] paths = Arrays.asList(domainIdentifier).stream()
                .filter(Strings::isNotEmpty)
                .toArray(String[]::new);
        InputStream inputStream = storingItemDto.getInputStream();
        PlatformStoringItemDto platformStoringItemDto = new PlatformStoringItemDto(inputStream, paths, sanitizedFileName, mime);
        StoredItemDto storedItemDto = service.create(platformStoringItemDto);
        return Optional.of(storedItemDto);
    }

    private String getFullStringPath(String domainIdentifier, String sanitizedFileName, StorageType type) {
        Optional<PathConstructionStrategy> pathStrategy = pathConstructionStrategies.stream().filter(s -> s.isSupported()).findFirst();
        if (pathStrategy.isPresent()) {
            return pathStrategy.get().getFullPath(domainIdentifier, type, sanitizedFileName);
        } else {
            String error = "No PathConstructionStrategy found";
            LOGGER.error(error);
            throw new UnsupportedOperationException(error);
        }
    }

    @Override
    public Optional<StoredItemDto> get(String id) throws NotSupportedException {
        return Optional.of(getService("Id requested: " + id).get(id));
    }

    private PlatformStorageStrategy getService(String originalFileName) throws NotSupportedException {
        Optional<PlatformStorageStrategy> service = platformStorageStrategies.stream().filter(s -> s.isSupported()).findFirst();
        if (service.isPresent()) {
            return service.get();
        } else {
            String errorMessage = String.format("No suitable platform was found for storing '%s'. Throwing NotSupportedException.", originalFileName);
            LOGGER.error(errorMessage);
            throw new NotSupportedException(errorMessage);
        }
    }

    private PathConstructionStrategy getPathStrategy() throws NotSupportedException {
        Optional<PathConstructionStrategy> pathConstructionStrategy = pathConstructionStrategies.stream().filter(s -> s.isSupported()).findFirst();
        if (pathConstructionStrategy.isPresent()) {
            return pathConstructionStrategy.get();
        } else {
            throw new NotSupportedException("Could not find an appropriate PathConstructionStrategy");
        }
    }
}
