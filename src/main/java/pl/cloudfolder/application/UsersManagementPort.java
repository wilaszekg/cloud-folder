package pl.cloudfolder.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.cloudfolder.application.dto.ClientDto;
import pl.cloudfolder.application.translate.ClientTransformer;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.AppClients;
import pl.cloudfolder.domain.ServiceCoordinator;
import pl.cloudfolder.domain.clients.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UsersManagementPort {

    @Autowired
    private UserService dropboxUserService;
    @Autowired
    private UserService googleUserService;
    @Autowired
    private AppClients googleAppClients;
    @Autowired
    private AppClients dropboxAppClients;
    @Autowired
    private ServiceCoordinator serviceCoordinator;
    @Autowired
    private ClientTransformer clientTransformer;


    public String loginUrlForDropbox() {
        return dropboxUserService.loginURL();
    }

    public String loginUrlForGoogle() {
        return googleUserService.loginURL();
    }

    public ClientDto finishDropboxAuthenticationWithStateAndCode(String state, String code) {
        AppClient appClient = dropboxAppClients.register(state, code);
        return clientTransformer.apply(appClient);
    }

    public ClientDto finishGoogleAuthenticationWithCode(String code) {
        AppClient appClient = googleAppClients.register(null, code);
        return clientTransformer.apply(appClient);
    }

    public Collection<ClientDto> userClients() {
        return serviceCoordinator.allClients()
                .stream()
                .map(client -> clientTransformer.apply(client))
                .collect(Collectors.toList());
    }

}
