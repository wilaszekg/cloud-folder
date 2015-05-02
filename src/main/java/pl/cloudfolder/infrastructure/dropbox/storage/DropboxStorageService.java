package pl.cloudfolder.infrastructure.dropbox.storage;

import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import org.springframework.stereotype.Component;
import pl.cloudfolder.domain.storage.Directory;
import pl.cloudfolder.domain.storage.File;
import pl.cloudfolder.domain.storage.StorageItem;
import pl.cloudfolder.domain.storage.StorageService;
import pl.cloudfolder.infrastructure.dropbox.clients.DropboxAppClient;
import pl.cloudfolder.infrastructure.dropbox.clients.DropboxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adiki on 2015-05-02.
 */
@Component
public class DropboxStorageService extends StorageService<DropboxAppClient> {

    @Override
    public List<StorageItem> listingAtPath(String path, DropboxAppClient dropboxClient) {
        try {
            List<StorageItem> storageItems = new ArrayList<>();
            DbxEntry.WithChildren listing = dropboxClient.getDbxClient().getMetadataWithChildren(path);
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
}
