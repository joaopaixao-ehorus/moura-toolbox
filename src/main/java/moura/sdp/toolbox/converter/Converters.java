package moura.sdp.toolbox.converter;

public class Converters {

    private Converters() {
    }

    public static Short byteToShort(Byte object) {
        return object != null ? object.shortValue() : null;
    }

    public static Integer byteToInt(Byte object) {
        return object != null ? object.intValue() : null;
    }

    public static Long byteToLong(Byte object) {
        return object != null ? object.longValue() : null;
    }

    public static String byteToString(Byte object) {
        return object != null ? object.toString() : null;
    }

    public static Byte shortToByte(Short object) {
        return object != null ? object.byteValue() : null;
    }

    public static Integer shortToInt(Short object) {
        return object != null ? object.intValue() : null;
    }

    public static Long shortToLong(Short object) {
        return object != null ? object.longValue() : null;
    }

    public static String shortToString(Short object) {
        return object != null ? object.toString() : null;
    }

    public static Byte intToByte(Integer object) {
        return object != null ? object.byteValue() : null;
    }

    public static Short intToShort(Integer object) {
        return object != null ? object.shortValue() : null;
    }

    public static Long intToLong(Integer object) {
        return object != null ? object.longValue() : null;
    }

    public static String intToString(Integer object) {
        return object != null ? object.toString() : null;
    }

    public static Byte longToByte(Long object) {
        return object != null ? object.byteValue() : null;
    }

    public static Short longToShort(Long object) {
        return object != null ? object.shortValue() : null;
    }

    public static Integer longToInt(Long object) {
        return object != null ? object.intValue() : null;
    }

    public static String longToString(Long object) {
        return object != null ? object.toString() : null;
    }

    public static Byte strToByte(String object) {
        return object != null ? Byte.parseByte(object) : null;
    }

    public static Short strToShort(String object) {
        return object != null ? Short.parseShort(object) : null;
    }

    public static Integer strToInt(String object) {
        return object != null ? Integer.parseInt(object) : null;
    }

    public static Long strToLong(String object) {
        return object != null ? Long.parseLong(object) : null;
    }

}
