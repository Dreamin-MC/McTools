package fr.dreamin.dreamintools.generic.service;

import fr.dreamin.dreamintools.McTools;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class ServiceManager {


    private final Map<Class<?>, Object> otherInjectables = new HashMap<>();
    private final Map<Class<? extends Service>, Service> serviceMap = new HashMap<>();

    @SneakyThrows
    public Service loadService(Class<? extends Service> target) {
        try {
            // first, check if its already enabled, if so, just re-use that one
            if (isServiceEnabled(target)) {
                return serviceMap.get(target);
            }

            Service i = null;
            // require an empty constructor, or this will go fucking boom
            for (Constructor<?> declaredConstructor : target.getDeclaredConstructors()) {
                if (declaredConstructor.isAnnotationPresent(Inject.class)) {
                    // try to fulfill this constructor
                    List<Object> arguments = new ArrayList<>();
                    for (Class<?> parameterType : declaredConstructor.getParameterTypes()) {
                        arguments.add(resolve(parameterType));
                    }
                    i = (Service) declaredConstructor.newInstance(arguments.stream().toArray());
                    break;
                }
            }
            if (i == null) {
                i = target.getConstructor().newInstance();
            }

            // find annotated injections
            for (Field field : target.getDeclaredFields()) {
                boolean hasAnnotation = field.isAnnotationPresent(Inject.class);
                if (hasAnnotation) {
                    Object v = resolve(field.getType());
                    if (v != null) {
                        field.setAccessible(true);
                        field.set(i, v);
                    } else {
                        if (v == null) {
                            McTools.getLog().error("Field " + field.getName() + " in " + target.getSimpleName() + " is null, this is probably a bug");
                        } else {
                            McTools.getLog().warn("WARNING! field " + field.getName() + " in " + target.getSimpleName() + " doesn't have the inject annotation, but it was resolved as " + v.getClass().getName());
                        }
                    }
                }
            }

            serviceMap.put(target, i);
            i.onEnable();
            return i;
        } catch (Exception e) {
            McTools.getLog().error("Failed to load service: " + target.getName());
            throw e;
        }
    }

    public void replaceService(Class<? extends Service> target, Service i) {
        serviceMap.put(target, i);
        serviceMap.put(i.getClass(), i);
    }

    public Object resolve(Class<?> type) {
        if (Service.class.isAssignableFrom(type)) {
            // its depended upon
            return loadService((Class<? extends Service>) type);
        } else if (type == McTools.class) {
            return McTools.getInstance();
        } else {
            if (otherInjectables.containsKey(type)) {
                return otherInjectables.get(type);
            } else if (type.isInterface()) {
                // dont check the value
                for (Map.Entry<Class<?>, Object> entry : otherInjectables.entrySet()) {
                    Class<?> ci = entry.getKey();
                    Object vi = entry.getValue();

                    if (type.isAssignableFrom(ci)) {
                        return vi;
                    }
                }
            } else {
                // last option, it extends, somewhere
                for (Map.Entry<Class<?>, Object> entry : otherInjectables.entrySet()) {
                    Class<?> c = entry.getKey();
                    if (c.isAssignableFrom(type)) {
                        return entry.getValue();
                    }
                }
            }
        }
        return null;
    }

    public void registerDependency(Class<?> key, Object value) {
        if (Service.class.isAssignableFrom(key)) {
            serviceMap.put((Class<? extends Service>) key, (Service) value);
        }
        otherInjectables.put(key, value);
        otherInjectables.put(value.getClass(), value);
    }

    /**
     * Get a Dynamic service implementation
     */
    public <T extends Service> T getService(Class<T> service) {
        return service.cast(loadService(service));
    }

    /**
     * Bulk initialize services
     *
     * @param targets Services
     */
    @SafeVarargs
    public final void loadServices(Class<? extends Service>... targets) {
        for (Class<? extends Service> target : targets) {
            loadService(target);
        }
    }

    /**
     * Returns true if the service is loaded
     *
     * @param s Service
     * @return State
     */
    public boolean isServiceEnabled(Class<? extends Service> s) {
        return serviceMap.containsKey(s);
    }
    
    /**
     * Get a collection of all loaded services, but they may not be enabled!
     *
     * @return instances
     */
    public Collection<Service> allServices() {
        return serviceMap.values();
    }

}
