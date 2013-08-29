package net.botdevs.aaf;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: joni.stromberg
 * Date: 29.8.2013
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public final class ExtensionManager {
    private final Map<String, ServiceData> DEFAULT_SERVICES;

    ExtensionManager() {
        DEFAULT_SERVICES = new HashMap<>();
    }

    public <T> T requestService(Class<T> service) {
        T result = null;
        ServiceLoader<T> impl = ServiceLoader.load(service);

        for (T loadedImpl : impl) {
            result = loadedImpl;
            if (result != null && DEFAULT_SERVICES.containsKey(service.getName())) {
                if (DEFAULT_SERVICES.get(service.getName()).equals(result.getClass().getName())) {
                    System.out.println("had default");
                    break;
                }
            } else if (result != null) {
                break;
            }
        }

        if (result == null) {
            throw new RuntimeException("Cannot find implementation for: " + service);
        }

        return result;
    }

    public <T> boolean hasService(Class<T> service) {
        return ServiceLoader.load(service).iterator().hasNext();
    }

    public <T> void setDefaultForService(Class<T> service, Class<? extends T> newDefault, boolean allowOverride) {
        String serviceName = service.getName();
        if (DEFAULT_SERVICES.containsKey(serviceName)) {
            if (DEFAULT_SERVICES.get(serviceName).allowOverride()) {
                DEFAULT_SERVICES.put(serviceName, new ServiceData(newDefault.getName(), allowOverride));
            } else {
                throw new IllegalAccessError("There is a default service for " + service.getSimpleName() + " that doesn't allow to be overridden.");
            }
        } else {
            DEFAULT_SERVICES.put(serviceName, new ServiceData(newDefault.getName(), allowOverride));
        }

    }

    private class ServiceData {
        private final String name;
        private final boolean allowOverride;

        public ServiceData(String name, boolean allowOverride) {
            this.name = name;
            this.allowOverride = allowOverride;
        }

        public String getName() {
            return name;
        }

        public boolean allowOverride() {
            return allowOverride;
        }
    }

}
