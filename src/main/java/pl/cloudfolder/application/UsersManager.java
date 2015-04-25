package pl.cloudfolder.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.cloudfolder.domain.UserAuthenticationService;
import pl.cloudfolder.infrastructure.ServiceType;

import java.util.Set;

/**
 * Created by adrian on 25/04/15.
 */
@Service
@Scope("session")
public class UsersManager {

    @Autowired
    private UserAuthenticationService dropboxUserAuthenticationService;
    @Autowired
    private UserAuthenticationService googleUserAuthenticationService;

    public String redirectionURLForDropboxService() {
        return redirectionURLForService(dropboxUserAuthenticationService);
    }

    public String redirectionURLForGoogleService() {
        return redirectionURLForService(googleUserAuthenticationService);
    }

    public String finishDropboxAuthenticationWithStateAndCode(String state, String code) throws Exception {
        return finishAuthenticationWithServiceStateAndCode(dropboxUserAuthenticationService, state, code);
    }

    public String finishGoogleAuthenticationWithCode(String code) throws Exception {
        return finishAuthenticationWithServiceStateAndCode(googleUserAuthenticationService, null, code);
    }

    public Set<String> loggedDropboxUserIds() {
        return loggedUserIdsForService(dropboxUserAuthenticationService);
    }

    public Set<String> loggedGoogleUserIds() {
        return loggedUserIdsForService(googleUserAuthenticationService);
    }

    public String usernameForUserId(String userId) throws Exception {
        if (userId == null) {
            return null;
        }
        ServiceType serviceType = serviceTypeForUserId(userId);
        UserAuthenticationService userAuthenticationService = userServiceForServiceType(serviceType);
        return usernameForServiceAndUserId(userAuthenticationService, userId);
    }

    private String redirectionURLForService(UserAuthenticationService userAuthenticationService) {
        return userAuthenticationService.loginURL();
    }

    private String finishAuthenticationWithServiceStateAndCode(UserAuthenticationService userAuthenticationService, String state, String code) throws Exception {
        return userAuthenticationService.finishAuthenticationWithStateAndCode(state, code);
    }

    private Set<String> loggedUserIdsForService(UserAuthenticationService userAuthenticationService) {
        return userAuthenticationService.loggedUserIds();
    }

    private ServiceType serviceTypeForUserId(String userId) {
        if (userId.startsWith(ServiceType.dropbox.name())) {
            return ServiceType.dropbox;
        } else if (userId.startsWith(ServiceType.google.name())) {
            return ServiceType.google;
        }
        return null;
    }

    private UserAuthenticationService userServiceForServiceType(ServiceType serviceType) {
        switch (serviceType) {
            case dropbox:
                return dropboxUserAuthenticationService;
            case google:
                return googleUserAuthenticationService;
        }
        return null;
    }

    private String usernameForServiceAndUserId(UserAuthenticationService userAuthenticationService, String userId) throws Exception {
        return userAuthenticationService.usernameForUserId(userId);
    }
}
