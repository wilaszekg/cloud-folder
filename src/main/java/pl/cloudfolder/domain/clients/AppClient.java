package pl.cloudfolder.domain.clients;

import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.storage.StorageItem;

import java.util.Collection;

public interface AppClient {
    String name();

    String id();

    ServiceType serviceType();

    Collection<StorageItem> listingAtPath(String path);
}
