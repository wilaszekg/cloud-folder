package pl.cloudfolder.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.cloudfolder.application.dto.ClientDto;
import pl.cloudfolder.domain.ServiceCoordinator;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.storage.Directory;
import pl.cloudfolder.domain.storage.StorageItem;
import pl.cloudfolder.domain.storage.StorageService;
import pl.cloudfolder.infrastructure.dropbox.clients.DropboxAppClient;

import java.util.List;

/**
 * Created by Adiki on 2015-05-02.
 */
@Component
public class StorageManagementPort {

    @Autowired
    private ServiceCoordinator serviceCoordinator;

    @Autowired
    private StorageService<DropboxAppClient> dropboxStorageService;

    public List<StorageItem> rootListingForUserClient(ClientDto userClient) {
        AppClient appClient = serviceCoordinator.appClient(userClient.getId());
        switch (appClient.serviceType()) {
            case dropbox:
                return dropboxStorageService.rootListingForAppClient((DropboxAppClient) appClient);
            case google:
                return null;
            default:
                throw new IllegalStateException("Not supported service type");
        }
    }

    public List<StorageItem> listingForDirectoryAndUserClient(Directory directory, ClientDto userClient) {
        AppClient appClient = serviceCoordinator.appClient(userClient.getId());
        switch (appClient.serviceType()) {
            case dropbox:
                return dropboxStorageService.listingForDirectoryAndClient(directory, (DropboxAppClient) appClient);
            case google:
                return null;
            default:
                throw new IllegalStateException("Not supported service type");
        }
    }
}
