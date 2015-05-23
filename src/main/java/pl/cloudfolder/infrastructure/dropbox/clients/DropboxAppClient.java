package pl.cloudfolder.infrastructure.dropbox.clients;

import com.dropbox.core.*;
import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.ClientIds;
import pl.cloudfolder.domain.storage.Directory;
import pl.cloudfolder.domain.storage.File;
import pl.cloudfolder.domain.storage.StorageItem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DropboxAppClient implements AppClient {

    private final String id;
    private final DbxClient dbxClient;
    private final DbxAccountInfo accountInfo;

    public DropboxAppClient(DbxClient dbxClient) {
        id = ClientIds.newId(ServiceType.dropbox);
        this.dbxClient = dbxClient;
        try {
            accountInfo = dbxClient.getAccountInfo();
        } catch (DbxException e) {
            throw new DropboxException(e);
        }
    }

    @Override
    public String name() {
        return accountInfo.displayName;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public ServiceType serviceType() {
        return ServiceType.dropbox;
    }

    @Override
    public Collection<StorageItem> listingForDirectoryId(String directoryId) {
        try {
            List<StorageItem> storageItems = new ArrayList<>();
            DbxEntry.WithChildren listing = dbxClient.getMetadataWithChildren(directoryId);
            for (DbxEntry child : listing.children) {
                StorageItem storageItem;
                if (child.isFile()) {
                    storageItem = new File(child.name, child.path);
                } else {
                    storageItem = new Directory(child.name, child.path);
                }
                storageItems.add(storageItem);
            }
            return storageItems;
        } catch (Exception e) {
            throw new DropboxException(e);
        }
    }

    @Override
    public String rootDirectoryId() {
        return "/";
    }

    @Override
    public void createDirectoryWithNameInDirectoryWithId(String name, String parentDirectoryId) {
        try {
            dbxClient.createFolder(parentDirectoryId + "/" + name);
        } catch (DbxException e) {
            throw new DropboxException(e);
        }
    }

    @Override
    public void downloadFileToLocation(String fileId, String fileLocation) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
            dbxClient.getFile(fileId, null, outputStream);
        } catch (IOException | DbxException e) {
            throw new DropboxException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void uploadFileFromPathToDirectory(String filePath, String directoryId) {
        java.io.File inputFile = new java.io.File(filePath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(inputFile);
            dbxClient.uploadFile(directoryId + "/" + inputFile.getName(),
                    DbxWriteMode.add(), inputFile.length(), inputStream);
        } catch (IOException | DbxException e) {
            throw new DropboxException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
