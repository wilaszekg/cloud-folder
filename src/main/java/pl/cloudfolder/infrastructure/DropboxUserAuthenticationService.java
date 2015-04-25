package pl.cloudfolder.infrastructure;

import com.dropbox.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.cloudfolder.domain.UserAuthenticationService;
import pl.cloudfolder.infrastructure.config.AppConfig;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by adrian on 25/04/15.
 */

@Service
@Scope("session")
public class DropboxUserAuthenticationService implements UserAuthenticationService {

    private final DbxAppInfo dbxAppInfo;
    private final DbxRequestConfig config;
    private final DbxStandardSessionStore csrfStore;
    private final DbxWebAuth webAuth;
    private final Map<String, DbxClient> clients;

    @Autowired
    public DropboxUserAuthenticationService(HttpSession session, AppConfig appConfig) {
        dbxAppInfo = new DbxAppInfo(appConfig.dropboxAppKey(), appConfig.dropboxAppSecret());
        csrfStore = new DbxStandardSessionStore(session, appConfig.dropboxCSRF());
        config = new DbxRequestConfig(appConfig.dropboxClientIdentifier(), Locale.getDefault().toString());
        webAuth = new DbxWebAuth(config, dbxAppInfo, appConfig.dropboxCallback(), csrfStore);
        clients = new HashMap<String, DbxClient>();
    }

    @Override
    public String loginURL() {
        return webAuth.start();
    }

    @Override
    public String finishAuthenticationWithStateAndCode(String state, String code) throws DbxWebAuth.NotApprovedException, DbxWebAuth.BadRequestException, DbxException, DbxWebAuth.CsrfException, DbxWebAuth.BadStateException, DbxWebAuth.ProviderException {
        HashMap<String, String[]> params = new HashMap<String, String[]>();
        params.put("code", new String[]{code});
        params.put("state", new String[]{state});
        DbxAuthFinish authFinish = webAuth.finish(params);
        String accessToken = authFinish.accessToken;
        DbxClient client = new DbxClient(config, accessToken);
        String userID = ServiceType.dropbox.name() + client.getAccountInfo().userId;
        clients.put(userID, client);
        return userID;
    }

    @Override
    public Set<String> loggedUserIds() {
        return clients.keySet();
    }

    @Override
    public String usernameForUserId(String userId) throws DbxException {
        return clients.get(userId).getAccountInfo().displayName;
    }
}
