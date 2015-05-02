package pl.cloudfolder.domain.storage;

import java.util.List;

/**
 * Created by Adiki on 2015-05-02.
 */
public abstract class StorageService <AppClientType> {
    public abstract List<StorageItem> listingAtPath(String path, AppClientType appClient);

    public List<StorageItem> rootListingForAppClient(AppClientType appClient) {
        return listingAtPath("/", appClient);
    }
}

