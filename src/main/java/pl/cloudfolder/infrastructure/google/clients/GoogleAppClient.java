package pl.cloudfolder.infrastructure.google.clients;

import com.google.api.services.drive.Drive;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.ClientIds;
import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.storage.StorageItem;

import java.io.IOException;
import java.util.Collection;

public class GoogleAppClient implements AppClient {

    private final String id;
    private final Drive drive;

    public GoogleAppClient(Drive drive) {
        id = ClientIds.newId(ServiceType.dropbox);
        this.drive = drive;
    }

    @Override
    public String name() {
        try {
            return drive.about().get().execute().getUser().getDisplayName();
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
    public Collection<StorageItem> listingAtPath(String path) {
        return null;
    }
}
