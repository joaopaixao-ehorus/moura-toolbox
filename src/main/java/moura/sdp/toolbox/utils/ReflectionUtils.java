package moura.sdp.toolbox.utils;

import moura.sdp.toolbox.converter.ConverterContext;
import moura.sdp.toolbox.exception.AttributeNotFoundException;
import moura.sdp.toolbox.orm.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectionUtils {

    private static final Set<Class<?>> basicTypes = new HashSet<>(Arrays.asList(Byte.class, Short.class, Integer.class, Long.class, String.class));

    private ReflectionUtils() {
    }

    public static void set(Object object, String attribute, Object value, ConverterContext context) {
        try {
            Method method = getSetterFor(object.getClass(), attribute);
            if (method == null) {
                throw new AttributeNotFoundException("The is no setter for " + object.getClass() + "@" + attribute);
            }
            method.invoke(object, context.getConverter().convert(value, method.getParameters()[0].getType()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object get(Object object, String attribute) {
        try {
            Method method = getGetterFor(object.getClass(), attribute);
            if (method == null) {
                throw new RuntimeException("The is no getter for " + object.getClass() + "@" + attribute);
            }
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isGetter(Method method) {
        return method.getName().startsWith("get") &&
                method.getName().length() > 3 &&
                method.getParameterCount() == 0 &&
                !method.getName().equals("getClass");
    }

    public static boolean isSetter(Method method) {
        return method.getName().startsWith("set") &&
                method.getName().length() > 3 &&
                method.getParameterCount() == 1;
    }

    public static Method getSetterFor(Class<?> clazz, String attribute) {
        String name = "set" + StringUtils.camelCase(attribute);
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(name) && isSetter(method)) {
                return method;
            }
        }
        return null;
    }

    public static Method getGetterFor(Class<?> clazz, String attribute) {
        String name = "get" + StringUtils.camelCase(attribute);
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(name) && isGetter(method)) {
                return method;
            }
        }
        return null;
    }

    public static Map<String, Object> getAttributes(Object object) {
        try {
            Map<String, Object> attributes = new HashMap<>();
            for (Method method : object.getClass().getMethods()) {
                if (isGetter(method)) {
                    attributes.put(StringUtils.snakeCase(method.getName().substring(3)), method.invoke(object));
                }
            }
            return attributes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T modelToEntity(Class<T> clazz, Model model) {
        try {
            T entity = clazz.getConstructor().newInstance();
            for (String attribute : model.getAttributeNames()) {
                Method method = getSetterFor(clazz, attribute);
                if (method == null) {
                    continue;
                }
                method.invoke(entity, model.get(attribute, method.getParameters()[0].getType()));
            }
            return entity;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("The is no default constructor for " + clazz);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isBasicType(Class<?> clazz) {
        return basicTypes.contains(clazz);
    }

}
