package moura.sdp.toolbox.validator;

public interface Rule<T> {

    void validate(T object, ValidationContext context);


}
