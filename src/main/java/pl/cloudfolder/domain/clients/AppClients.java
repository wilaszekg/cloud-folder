package pl.cloudfolder.domain.clients;

import pl.cloudfolder.domain.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AppClients<AC extends AppClient, Credentials> {

    private final Map<String, AC> clients;
    private final UserService<Credentials> userService;

    protected AppClients(UserService<Credentials> userService) {
        this.userService = userService;
        clients = new HashMap<>();
    }

    public AC register(String status, String code) {
        Credentials credentials = userService.finishAuthentication(status, code);
        return createClient(credentials);
    }

    private AC createClient(Credentials credentials) {
        AC client = create(credentials);
        clients.put(client.id(), client);
        return client;
    }

    public Collection<AppClient> clients() {
        LinkedList<AppClient> appClients = new LinkedList<>();
        appClients.addAll(clients.values());

        return appClients;
    }

    public AppClient byId(String id) {
        return clients.get(id);
    }

    protected abstract AC create(Credentials credentials);

}
