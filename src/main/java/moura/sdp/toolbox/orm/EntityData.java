package moura.sdp.toolbox.orm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityData {

    private final Map<Integer, Set<String>> relations = new HashMap<>();
    private final Set<Integer> attached = new HashSet<>();

    boolean isNew(Object object) {
        return !attached.contains(System.identityHashCode(object));
    }

    void attach(Object object) {
        attached.add(System.identityHashCode(object));
    }

    boolean isRelationLoaded(Object object, String relation) {
        int hash = System.identityHashCode(object);
        return relations.get(hash) != null && relations.get(hash).contains(relation);
    }

    void markRelationLoaded(Object object, String relation) {
        int hash = System.identityHashCode(object);
        relations.computeIfAbsent(hash, k -> new HashSet<>());
        relations.get(hash).add(relation);
    }

}
