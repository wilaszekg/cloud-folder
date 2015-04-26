package pl.cloudfolder.infrastructure.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.cloudfolder.domain.AppClients;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoogleAppClients extends AppClients<GoogleAppClient, GoogleCredential> {

    @Autowired
    public GoogleAppClients(GoogleUserService googleUserService) {
        super(googleUserService);
    }

    @Override
    protected GoogleAppClient create(GoogleCredential googleCredential) {
        Drive drive = new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), googleCredential).build();
        return new GoogleAppClient(drive);
    }


}
