package pl.cloudfolder.application.translate;

import org.springframework.stereotype.Component;
import pl.cloudfolder.application.dto.ClientDto;
import pl.cloudfolder.domain.ServiceType;
import pl.cloudfolder.domain.clients.AppClient;

@Component
public class ClientTransformer {
    public ClientDto apply(AppClient client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.id());
        clientDto.setName(client.name());
        clientDto.setDropbox(client.serviceType() == ServiceType.dropbox);
        clientDto.setGoogle(client.serviceType() == ServiceType.google);
        return clientDto;
    }
}
