package pl.cloudfolder.application;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.cloudfolder.application.dto.StorageItemDto;
import pl.cloudfolder.application.translate.StorageItemTransformer;
import pl.cloudfolder.domain.ServiceCoordinator;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.storage.StorageItem;

@Component
public class StoragePort {

    @Autowired
    private ServiceCoordinator serviceCoordinator;

    @Autowired
    private StorageItemTransformer storageItemTransformer;

    public Collection<StorageItemDto> rootListingForUserId(String userId) {
        AppClient appClient = serviceCoordinator.appClient(userId);
        return listingForUserIdAndDirectoryId(userId, appClient.rootDirectoryId());
    }

    public Collection<StorageItemDto> listingForUserIdAndDirectoryId(String userId, String directoryId) {
        AppClient appClient = serviceCoordinator.appClient(userId);
        Collection<StorageItem> storageItems = appClient.listingForDirectoryId(directoryId);
        return storageItems.stream().map(storageItem -> storageItemTransformer.apply(storageItem, appClient)).collect(Collectors.toList());
    }

    public void createDirectoryWithNameForUserIdInDirectoryWithId(String name, String userId, String directoryId) {
        AppClient appClient = serviceCoordinator.appClient(userId);
        appClient.createDirectoryWithNameInDirectoryWithId(name, directoryId);
    }

    public void createDirectoryWithNameInRootDirectory(String name, String userId) {
        AppClient appClient = serviceCoordinator.appClient(userId);
        createDirectoryWithNameForUserIdInDirectoryWithId(name, userId, appClient.rootDirectoryId());
    }

    public void copyFile(String sourceClient, String sourceFile, String destClient, Optional<String> destDirectory) {
        serviceCoordinator
                .fileTransferManagerForSourceAndDestinationClientIds(sourceClient, destClient)
                .copyFileToDirectory(sourceFile,
                        destDirectory.orElse(serviceCoordinator.appClient(destClient).rootDirectoryId()));
    }

    public void moveFile(String sourceClient, String sourceFile, String destClient, Optional<String> destDirectory) {
        serviceCoordinator
                .fileTransferManagerForSourceAndDestinationClientIds(sourceClient, destClient)
                .moveFileToDirectory(sourceFile,
                        destDirectory.orElse(serviceCoordinator.appClient(destClient).rootDirectoryId()));
    }

    public void removeFile(String clientId, String fileId) {
        serviceCoordinator.appClient(clientId).deleteFileOrDirectory(fileId);
    }
}
