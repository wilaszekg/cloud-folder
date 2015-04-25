package pl.cloudfolder.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.cloudfolder.domain.UserAuthenticationService;
import pl.cloudfolder.infrastructure.ServiceType;

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

    public String dropboxUserIdForStateAndCode(String state, String code) throws Exception {
        return userIdForServiceStateAndCode(dropboxUserAuthenticationService, state, code);
    }

    public String googleUserIdForCode(String code) {
        return null;
    }

    public String usernameForUserId(String userId) throws Exception {
        ServiceType serviceType = serviceTypeForUserId(userId);
        UserAuthenticationService userAuthenticationService = userServiceForServiceType(serviceType);
        return usernameForServiceAndUserId(userAuthenticationService, userId);
    }

    private String redirectionURLForService(UserAuthenticationService userAuthenticationService) {
        return userAuthenticationService.redirectionURL();
    }

    private String userIdForServiceStateAndCode(UserAuthenticationService userAuthenticationService, String state, String code) throws Exception {
        return userAuthenticationService.userIdForStateAndCode(state, code);
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
