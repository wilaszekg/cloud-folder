package pl.cloudfolder.infrastructure.dropbox.clients;

import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import org.springframework.stereotype.Component;
import pl.cloudfolder.domain.storage.StorageItem;
import pl.cloudfolder.domain.storage.StorageService;

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
            DbxEntry.WithChildren listing = dropboxClient.getDbxClient().getMetadataWithChildren("/");
            for (DbxEntry child : listing.children) {
                System.out.println("	" + child.name + ": " + child.toString());
            }
            return storageItems;
        } catch (Exception e) {
            throw new DropboxException(e);
        }
    }
}
