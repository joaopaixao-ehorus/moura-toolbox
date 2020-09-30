package moura.sdp.toolbox.orm;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EntityRegistry {

    private final Map<Class<?>, EntityConfiguration<?>> entries = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> EntityConfiguration<T> getConfig(Class<T> key) {
        return (EntityConfiguration<T>) entries.get(key);
    }

    public <T> void register(EntityConfiguration<T> configuration) {
        entries.put(configuration.getEntityClass(), configuration);
    }

    public void unregister(Class<?> key) {
        entries.remove(key);
    }

    public List<EntityConfiguration<?>> getConfigurations() {
        return new ArrayList<>(entries.values());
    }

}
