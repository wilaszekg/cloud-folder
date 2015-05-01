package pl.cloudfolder.infrastructure.dropbox.clients;

import com.dropbox.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.cloudfolder.domain.clients.UserService;
import pl.cloudfolder.infrastructure.config.AppConfig;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Locale;

@Service
public class DropboxUserService implements UserService {

    private final DbxRequestConfig config;
    private final DbxWebAuth webAuth;

    @Autowired
    public DropboxUserService(HttpSession session, AppConfig appConfig) {
        DbxAppInfo dbxAppInfo = new DbxAppInfo(appConfig.dropboxAppKey(), appConfig.dropboxAppSecret());
        DbxStandardSessionStore csrfStore = new DbxStandardSessionStore(session, appConfig.dropboxCSRF());
        config = new DbxRequestConfig(appConfig.dropboxClientIdentifier(), Locale.getDefault().toString());
        webAuth = new DbxWebAuth(config, dbxAppInfo, appConfig.dropboxCallback(), csrfStore);
    }

    @Override
    public String loginURL() {
        return webAuth.start();
    }

    @Override
    public DbxClient finishAuthentication(String state, String code) {
        try {
            HashMap<String, String[]> params = new HashMap<>();
            params.put("code", new String[]{code});
            params.put("state", new String[]{state});
            DbxAuthFinish authFinish = webAuth.finish(params);
            String accessToken = authFinish.accessToken;
            return new DbxClient(config, accessToken);
        } catch (Exception e) {
            throw new DropboxException(e);
        }
    }
}
