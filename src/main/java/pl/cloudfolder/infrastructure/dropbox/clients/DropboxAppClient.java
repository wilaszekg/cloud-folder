package pl.cloudfolder.infrastructure.dropbox.clients;

import com.dropbox.core.DbxAccountInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.ClientIds;
import pl.cloudfolder.domain.storage.Directory;
import pl.cloudfolder.domain.storage.File;
import pl.cloudfolder.domain.storage.StorageItem;

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
    public void createDirectoryWithNameInDirectoryWithId(String name, String directoryId) {
        try {
            dbxClient.createFolder(directoryId + "/" + name);
        } catch (DbxException e) {
            throw new DropboxException(e);
        }
    }

}
