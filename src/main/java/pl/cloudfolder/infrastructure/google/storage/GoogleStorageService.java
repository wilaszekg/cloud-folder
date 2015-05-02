package pl.cloudfolder.infrastructure.google.storage;

import com.google.api.services.drive.Drive;
import org.springframework.stereotype.Component;
import pl.cloudfolder.domain.storage.StorageItem;
import pl.cloudfolder.domain.storage.StorageService;
import pl.cloudfolder.infrastructure.google.clients.GoogleAppClient;

import java.util.List;

/**
 * Created by Adiki on 2015-05-02.
 */
@Component
public class GoogleStorageService extends StorageService<GoogleAppClient> {

    @Override
    protected List<StorageItem> listingAtPath(String path, GoogleAppClient appClient) {
        Drive drive = appClient.getDrive();
        Drive.Files files = drive.files();
        return null;
    }

}
