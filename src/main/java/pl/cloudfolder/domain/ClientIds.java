package pl.cloudfolder.domain;

public class ClientIds {
    public static String newId(ServiceType serviceType, String externalId) {
        return serviceType.name() + externalId;
    }
}
