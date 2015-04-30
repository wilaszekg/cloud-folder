package pl.cloudfolder.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.AppClients;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ServiceCoordinator {
    private final UserService googleUserService;
    private final UserService dropboxUserService;
    private final AppClients googleAppClients;
    private final AppClients dropboxAppClients;

    @Autowired
    public ServiceCoordinator(UserService googleUserService, UserService dropboxUserService, AppClients googleAppClients, AppClients appClients) {
        this.googleUserService = googleUserService;
        this.dropboxUserService = dropboxUserService;
        this.googleAppClients = googleAppClients;
        this.dropboxAppClients = appClients;
    }

    public Collection<AppClient> allClients() {
        Stream<AppClient> allClients = Stream.concat(
                googleAppClients.clients().stream(),
                dropboxAppClients.clients().stream());
        return allClients.collect(Collectors.toList());
    }

    public UserService userService(ServiceType serviceType) {
        switch (serviceType) {
            case dropbox:
                return dropboxUserService;
            case google:
                return googleUserService;
            default:
                throw new IllegalStateException("no matched user service");
        }
    }

    public AppClients appClients(ServiceType serviceType) {
        switch (serviceType) {
            case dropbox:
                return dropboxAppClients;
            case google:
                return googleAppClients;
            default:
                throw new IllegalStateException("no matched user service");
        }
    }

    public AppClient appClient(String id) {
        return appClients(serviceType(id)).byId(id);
    }

    private ServiceType serviceType(String userId) {
        if (userId.startsWith(ServiceType.dropbox.name())) {
            return ServiceType.dropbox;
        } else if (userId.startsWith(ServiceType.google.name())) {
            return ServiceType.google;
        } else {
            throw new IllegalStateException("User id not matched for any provider");
        }
    }
}
