package moura.sdp.toolbox.utils;

public class StringUtils {

    public StringUtils() {
    }

    public static String capitalize(String string) {
        if (string == null) {
            return null;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String snakeCase(String string) {
        return string.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
    }

    public static String camelCase(String str)
    {
        // Capitalize first letter of string
        str = str.substring(0, 1).toUpperCase()
                + str.substring(1);

        // Convert to StringBuilder
        StringBuilder builder
                = new StringBuilder(str);

        // Traverse the string character by
        // character and remove underscore
        // and capitalize next letter
        for (int i = 0; i < builder.length(); i++) {

            // Check char is underscore
            if (builder.charAt(i) == '_') {

                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        // Return in String type
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(snakeCase("JoseLuiz"));
    }

}
