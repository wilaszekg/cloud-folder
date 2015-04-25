package pl.cloudfolder.infrastructure;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.cloudfolder.domain.UserAuthenticationService;
import pl.cloudfolder.infrastructure.config.AppConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by adrian on 25/04/15.
 */

@Service
@Scope("session")
public class GoogleUserAuthenticationService implements UserAuthenticationService {

    private final GoogleAuthorizationCodeFlow authorizationCodeFlow;
    private final String redirectUri;
    private final Map<String, Drive> clients;

    @Autowired
    public GoogleUserAuthenticationService(AppConfig appConfig) throws IOException {
        authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                appConfig.googleClientId(), appConfig.googleClientSecret(),
                DriveScopes.all())
                .setAccessType("online")
                .setCredentialDataStore(StoredCredential.getDefaultDataStore(new MemoryDataStoreFactory()))
                .build();
        redirectUri = appConfig.googleCallback();
        clients = new HashMap<String, Drive>();
    }

    @Override
    public String loginURL() {
        return authorizationCodeFlow
                .newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .build();
    }

    @Override
    public String finishAuthenticationWithStateAndCode(String status, String code) throws IOException {
        GoogleTokenResponse response = authorizationCodeFlow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
        Drive drive = new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
        String clientId = ServiceType.google.name() + drive.about().get().execute().getUser().getEmailAddress();
        clients.put(clientId, drive);
        return clientId;
    }

    @Override
    public Set<String> loggedUserIds() {
        return clients.keySet();
    }

    @Override
    public String usernameForUserId(String userId) throws IOException {
        return clients.get(userId).about().get().execute().getName();
    }
}
