package pl.cloudfolder.infrastructure.google.clients;

import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.ClientIds;
import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.storage.StorageItem;
import pl.cloudfolder.infrastructure.google.storage.GoogleStorageManager;

import java.io.IOException;
import java.util.Collection;

public class GoogleAppClient implements AppClient {

    private final String id;
    private final GoogleStorageManager googleStorageManager;

    public GoogleAppClient(GoogleStorageManager googleStorageManager) {
        id = ClientIds.newId(ServiceType.google);
        this.googleStorageManager = googleStorageManager;
    }

    @Override
    public String name() {
        try {
            return googleStorageManager.about().get().execute().getUser().getDisplayName();
        } catch (IOException e) {
            throw new GoogleException(e);
        }
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public ServiceType serviceType() {
        return ServiceType.google;
    }

    @Override
    public Collection<StorageItem> listingForDirectoryId(String directoryId) {
        return googleStorageManager.listingForDirectoryId(directoryId);
    }

    @Override
    public String rootDirectoryId() {
        return "root";
    }

    @Override
    public void createDirectoryWithNameInDirectoryWithId(String name, String parentDirectoryId) {
        googleStorageManager.createDirectoryWithNameInDirectoryWithId(name, parentDirectoryId);
    }

    @Override
    public void downloadFileToLocation(String fileId, String fileLocation) {
        googleStorageManager.downloadFileToLocation(fileId, fileLocation);
    }

    @Override
    public void uploadFileFromPathToDirectory(String filePath, String directoryId) {

    }

    @Override
    public void deleteFileOrDirectory(String id) {

    }
}
