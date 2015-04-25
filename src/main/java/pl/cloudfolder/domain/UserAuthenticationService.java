package pl.cloudfolder.domain;

/**
 * Created by adrian on 25/04/15.
 */
public interface UserAuthenticationService {
    public String redirectionURL();
    public String userIdForStateAndCode(String status, String code) throws Exception;
    public String usernameForUserId(String userId) throws Exception;
}
