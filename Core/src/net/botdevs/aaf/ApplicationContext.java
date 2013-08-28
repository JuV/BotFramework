package net.botdevs.aaf;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * User: JuV
 * Date: 25.8.2013
 * Time: 9:46
 */
public final class ApplicationContext {
    private static final ApplicationContext _instance = new ApplicationContext();
    private final List<ClientContext> _clients;

    private ApplicationContext() {
        _clients = new ArrayList<>();
    }

    public static ApplicationContext get() {
        return _instance;
    }

    public <T> T requestService(Class<T> api) {
        T result = null;
        ServiceLoader<T> impl = ServiceLoader.load(api);

        for (T loadedImpl : impl) {
            result = loadedImpl;
            if (result != null) break;
        }

        if (result == null) {
            throw new RuntimeException("Cannot find implementation for: " + api);
        }

        return result;
    }

    public ClientContext createClient() {
        return registerClient(new ClientContext(this));
    }

    private ClientContext registerClient(ClientContext context) {
        _clients.add(context);
        System.out.println("Registered context");
        return context;
    }

    public <T> boolean hasService(Class<T> service) {
        return ServiceLoader.load(service).iterator().hasNext();
    }
}
