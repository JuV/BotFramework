package net.botdevs.aaf;

import net.botdevs.aaf.applet.AppletProvider;
import net.botdevs.aaf.ui.ClientUI;

import java.applet.Applet;

/**
 * User: JuV
 * Date: 25.8.2013
 * Time: 9:48
 */
public final class ClientContext {
    private final ApplicationContext _context;
    private final AppletProvider _appletProvider;
    private Applet applet;

    ClientContext(final ApplicationContext context) {
        this._context = context;
        this._appletProvider = context.requestService(AppletProvider.class);

        initialize();
    }

    private void initialize() {
        applet = _appletProvider.provide();
        if (_context.hasService(ClientUI.class)) {
            ClientUI ui = _context.requestService(ClientUI.class);
            ui.getContainer().add(applet);
            ui.display();
        }
    }

    public Applet getApplet() {
        return applet;
    }
}
