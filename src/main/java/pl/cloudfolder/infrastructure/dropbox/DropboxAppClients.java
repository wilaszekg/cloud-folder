package pl.cloudfolder.infrastructure.dropbox;

import com.dropbox.core.DbxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.cloudfolder.domain.AppClients;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DropboxAppClients extends AppClients<DropboxAppClient, DbxClient> {

    @Autowired
    public DropboxAppClients(DropboxUserService dropboxUserService) {
        super(dropboxUserService);
    }

    @Override
    protected DropboxAppClient create(DbxClient dbxClient) {
        return new DropboxAppClient(dbxClient);
    }

}
