package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.Converter;

public interface PersistenceContext {

    Database getDatabase();

    Converter getConverter();

}
