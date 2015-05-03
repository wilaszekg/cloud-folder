package pl.cloudfolder.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.cloudfolder.application.dto.StorageItemDto;
import pl.cloudfolder.application.translate.StorageItemTransformer;
import pl.cloudfolder.domain.ServiceCoordinator;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.storage.StorageItem;

import java.util.Collection;
import java.util.stream.Collectors;

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
}
