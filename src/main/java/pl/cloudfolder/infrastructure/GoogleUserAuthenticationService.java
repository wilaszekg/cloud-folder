package pl.cloudfolder.infrastructure;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.cloudfolder.domain.UserAuthenticationService;

/**
 * Created by adrian on 25/04/15.
 */

@Service
@Scope("session")
public class GoogleUserAuthenticationService implements UserAuthenticationService {
    @Override
    public String redirectionURL() {
        return "http://google.pl";
    }

    @Override
    public String userIdForStateAndCode(String status, String code) {
        return null;
    }

    @Override
    public String usernameForUserId(String userId) {
        return null;
    }
}
