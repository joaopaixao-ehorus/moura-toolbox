package moura.sdp.toolbox.query;

import moura.sdp.toolbox.common.Builder;

import java.util.List;

public interface RelationFetchBuilder<T> extends
        RelationFetchConfigurer<RelationFetchBuilder<T>>, Builder<List<RelationToFetch<?, T>>> {
}
