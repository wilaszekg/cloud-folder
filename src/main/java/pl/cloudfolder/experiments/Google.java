package pl.cloudfolder.experiments;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.cloudfolder.infrastructure.config.AppConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/google")
public class Google {

    private final AuthorizationCodeFlow authorizationCodeFlow;
    private String userId = "just me";

    @Autowired
    public Google(AppConfig appConfig) throws IOException {

        authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                appConfig.googleClientId(), appConfig.googleClientSecret(),
                DriveScopes.all())
                .setAccessType("online")
                .setCredentialDataStore(StoredCredential.getDefaultDataStore(new MemoryDataStoreFactory()))
                .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        AuthorizationCodeRequestUrl redirectUri = authorizationCodeFlow
                .newAuthorizationUrl()
                .setRedirectUri("http://localhost:8080/google/auth");
        request.setAttribute("googleLogin", redirectUri.build());
        return "experiments/google";
    }

    @RequestMapping("/auth")
    public String auth(String code) throws IOException {
        AuthorizationCodeTokenRequest tokenRequest = authorizationCodeFlow.newTokenRequest(code);
        tokenRequest.setRedirectUri("http://localhost:8080/google/auth");
        TokenResponse response = tokenRequest.execute();
        authorizationCodeFlow.createAndStoreCredential(response, userId);
        return "experiments/google";
    }

    @RequestMapping("/list")
    public String list() throws IOException {
        Credential credential = authorizationCodeFlow.loadCredential(userId);
        Drive drive = new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential).build();

        List<File> items = drive.files().list().execute().getItems();
        About about = drive.about().get().execute();

        return "experiments/google";
    }

}
