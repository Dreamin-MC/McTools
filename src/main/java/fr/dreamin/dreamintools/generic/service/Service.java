package fr.dreamin.dreamintools.generic.service;

import fr.dreamin.dreamintools.McTools;

public abstract class Service implements Servicable {

    public void onEnable() {}

    public void onDisable() {
        // unused, but can be overwritten
    }

    public void log(String s) {
        McTools.getLog().info(getClass().getSimpleName() + ": " + s);
    }

    public <T extends Servicable> T getService(Class<T> s) {
        if (Service.class.isAssignableFrom(s)) {
            Class<? extends Service> sc = (Class<? extends Service>) s;
            return s.cast(McTools.getInstance().getServiceManager().getService(sc));
        }
        return s.cast(McTools.getInstance().getServiceManager().resolve(s));
    }


}
