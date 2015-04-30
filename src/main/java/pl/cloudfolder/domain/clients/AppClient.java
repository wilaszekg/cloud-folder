package pl.cloudfolder.domain.clients;

import pl.cloudfolder.domain.ServiceType;

public interface AppClient {
    String name();

    String id();

    ServiceType serviceType();
}
