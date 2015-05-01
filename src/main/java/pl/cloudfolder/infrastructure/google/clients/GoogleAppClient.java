package pl.cloudfolder.infrastructure.google.clients;

import com.google.api.services.drive.Drive;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.ClientIds;
import pl.cloudfolder.domain.ServiceType;

import java.io.IOException;

public class GoogleAppClient implements AppClient {

    private final Drive drive;

    public GoogleAppClient(Drive drive) {
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
        return ClientIds.newId(ServiceType.google, externalServiceId());
    }

    @Override
    public ServiceType serviceType() {
        return ServiceType.google;
    }

    private String externalServiceId() {
        try {
            return drive.about().get().execute().getUser().getEmailAddress();
        } catch (IOException e) {
            throw new GoogleException(e);
        }
    }
}
