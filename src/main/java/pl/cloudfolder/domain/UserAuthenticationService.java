package pl.cloudfolder.domain;

import java.util.Set;

/**
 * Created by adrian on 25/04/15.
 */
public interface UserAuthenticationService {
    public String loginURL();
    public String finishAuthenticationWithStateAndCode(String status, String code) throws Exception;
    public Set<String> loggedUserIds();
    public String usernameForUserId(String userId) throws Exception;
}
