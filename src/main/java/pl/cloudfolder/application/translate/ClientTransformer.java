package pl.cloudfolder.application.translate;

import org.springframework.stereotype.Component;
import pl.cloudfolder.application.dto.ClientDto;
import pl.cloudfolder.application.dto.Service;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.ServiceType;

@Component
public class ClientTransformer {
    public ClientDto apply(AppClient client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.id());
        clientDto.setName(client.name());
        clientDto.setService(service(client.serviceType()));
        return clientDto;
    }

    private Service service(ServiceType serviceType) {
        switch (serviceType) {
            case dropbox:
                return Service.dropbox;
            case google:
                return Service.google;
            default:
                throw new IllegalStateException("not matching service typ");
        }
    }
}
