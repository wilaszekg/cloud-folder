package pl.cloudfolder.domain.clients;

import com.dropbox.core.DbxException;
import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.storage.StorageException;
import pl.cloudfolder.domain.storage.StorageItem;

import java.io.IOException;
import java.util.Collection;

public interface AppClient {
    String name();

    String id();

    ServiceType serviceType();

    Collection<StorageItem> listingForDirectoryId(String directoryId);

    String rootDirectoryId();

    void createDirectoryWithNameInDirectoryWithId(String name, String parentDirectoryId);

    void downloadFileToLocation(String fileId, String fileLocation, String filename) throws StorageException;

    void uploadFileFromPathToDirectory(String filePath, String directoryId) throws StorageException;

    void deleteFileOrDirectory(String id) throws StorageException;

    String filenameForFileId(String fileId);
}
