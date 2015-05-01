package pl.cloudfolder.infrastructure.dropbox.clients;

import com.dropbox.core.DbxAccountInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.clients.ClientIds;
import pl.cloudfolder.domain.ServiceType;

public class DropboxAppClient implements AppClient {

    private final DbxClient dbxClient;
    private final DbxAccountInfo accountInfo;

    public DropboxAppClient(DbxClient dbxClient) {
        this.dbxClient = dbxClient;
        try {
            accountInfo = dbxClient.getAccountInfo();
        } catch (DbxException e) {
            throw new DropboxException(e);
        }
    }

    @Override
    public String name() {
        return accountInfo.displayName;
    }

    @Override
    public String id() {
        return ClientIds.newId(ServiceType.dropbox, externalServiceId());
    }

    @Override
    public ServiceType serviceType() {
        return ServiceType.dropbox;
    }

    private String externalServiceId() {
        return Long.toString(accountInfo.userId);
    }
}
