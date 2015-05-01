package pl.cloudfolder.infrastructure.google.clients;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.cloudfolder.domain.clients.UserService;
import pl.cloudfolder.infrastructure.config.AppConfig;

import java.io.IOException;

@Service
public class GoogleUserService implements UserService<GoogleCredential> {

    private final GoogleAuthorizationCodeFlow authorizationCodeFlow;
    private final String redirectUri;

    @Autowired
    public GoogleUserService(AppConfig appConfig) throws IOException {
        authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                appConfig.googleClientId(), appConfig.googleClientSecret(),
                DriveScopes.all())
                .setAccessType("online")
                .setCredentialDataStore(StoredCredential.getDefaultDataStore(new MemoryDataStoreFactory()))
                .build();
        redirectUri = appConfig.googleCallback();
    }

    @Override
    public String loginURL() {
        return authorizationCodeFlow
                .newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .build();
    }

    @Override
    public GoogleCredential finishAuthentication(String status, String code) {
        try {
            GoogleTokenResponse response = authorizationCodeFlow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            return new GoogleCredential().setFromTokenResponse(response);
        } catch (IOException e) {
            throw new GoogleException(e);
        }
    }

}
