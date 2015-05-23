package pl.cloudfolder.domain.clients;

import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.storage.StorageItem;

import java.util.Collection;

public interface AppClient {
    String name();

    String id();

    ServiceType serviceType();

    Collection<StorageItem> listingForDirectoryId(String directoryId);

    String rootDirectoryId();

    void createDirectoryWithNameInDirectoryWithId(String name, String parentDirectoryId);

    void downloadFileToLocation(String fileId, String fileLocation);

    void uploadFileFromPathToDirectory(String filePath, String directoryId);

    void deleteFileOrDirectory(String id);
}
