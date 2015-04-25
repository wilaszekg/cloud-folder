package pl.cloudfolder.application;

import pl.cloudfolder.domain.UserAuthenticationService;

/**
 * Created by adrian on 25/04/15.
 */
public class UsersService {
    private UserAuthenticationService dropboxUserAuthenticationService;
    private UserAuthenticationService googleUserAuthenticationService;

    public void loginWithDropbox() {
        loginWithService(dropboxUserAuthenticationService);
    }

    public void loginWithGoogle() {
        loginWithService(googleUserAuthenticationService);
    }

    private void loginWithService(UserAuthenticationService dropboxUserAuthenticationService) {

    }
}
