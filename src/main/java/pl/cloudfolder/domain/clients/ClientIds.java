package pl.cloudfolder.domain.clients;

import pl.cloudfolder.domain.ServiceType;

public class ClientIds {
    public static String newId(ServiceType serviceType, String externalId) {
        return serviceType.name() + externalId.replaceAll("@", "(at)");
    }
}
