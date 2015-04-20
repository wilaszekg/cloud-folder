package pl.cloudfolder.experiments;

import com.dropbox.core.*;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cloudfolder.infrastructure.config.AppConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by adrian on 20/04/15.
 */

@Controller
@RequestMapping("/dropbox")
public class DropBox {

    private final DbxWebAuth webAuth;
    private final DbxStandardSessionStore csrfStore;
    private static final String CSRF_KEY = "dropbox-auth-csrf-token";
    private final DbxRequestConfig config;
    private DbxClient client;

    @Autowired
    public DropBox(AppConfig appConfig, HttpSession session) {
        DbxAppInfo appInfo = new DbxAppInfo(appConfig.dropboxAppKey(), appConfig.dropboxAppSecret());
        config = new DbxRequestConfig(
                "ZTISCloudCommander", Locale.getDefault().toString());
        csrfStore = new DbxStandardSessionStore(session, CSRF_KEY);
        webAuth = new DbxWebAuth(config, appInfo, "http://localhost:8080/dropbox/auth", csrfStore);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        request.setAttribute("dropboxLogin", webAuth.start());
        return "experiments/dropbox";
    }

    @RequestMapping("/auth")
    public String auth(String state, String code) throws IOException, DbxWebAuth.NotApprovedException, DbxWebAuth.BadRequestException, DbxException, DbxWebAuth.CsrfException, DbxWebAuth.BadStateException, DbxWebAuth.ProviderException {
        System.out.println(state);
        System.out.println(code);
        HashMap<String, String[]> params = new HashMap<String, String[]>();
        params.put("code", new String[]{code});
        params.put("state", new String[]{state});
        DbxAuthFinish authFinish = webAuth.finish(params);
        String accessToken = authFinish.accessToken;
        client = new DbxClient(config, accessToken);
        return "experiments/dropbox";
    }

    @RequestMapping("/list")
    public String list() throws IOException, DbxException {
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            System.out.println("	" + child.name + ": " + child.toString());
        }
        return "experiments/dropbox";
    }
}
