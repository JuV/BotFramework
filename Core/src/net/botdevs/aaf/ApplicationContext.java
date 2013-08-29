package net.botdevs.aaf;

import java.util.ArrayList;
import java.util.List;

/**
 * User: JuV
 * Date: 25.8.2013
 * Time: 9:46
 */
public final class ApplicationContext {
    private static ApplicationContext _INSTANCE = new ApplicationContext();
    private final ExtensionManager _extension_manager;
    private final List<ClientContext> _clients;

    private ApplicationContext() {
        _extension_manager = new ExtensionManager();
        _clients = new ArrayList<>();
    }

    public static ApplicationContext getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new ApplicationContext();
        }
        return _INSTANCE;
    }

    public ClientContext createClient() {
        return registerClient(new ClientContext(this));
    }

    private ClientContext registerClient(final ClientContext context) {
        _clients.add(context);
        System.out.println("Registered context");
        return context;
    }

    public ExtensionManager getExtensionManager() {
        return _extension_manager;
    }
}
