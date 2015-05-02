package pl.cloudfolder.domain.clients;

import pl.cloudfolder.domain.ServiceType;

import java.util.UUID;

public class ClientIds {

    public static String newId(ServiceType serviceType) {
        return serviceType.name() + UUID.randomUUID().toString();
    }
}
