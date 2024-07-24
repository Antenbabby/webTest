package top.antennababy.demo.web.webtest.demos.test;

public class UTF8StringLength {
    public static void main(String[] args) {
        String testString ="188测试、华为0573-01、testone、杨波、150220"; // Example string
        System.out.println(testString.length());
        int byteCount = getUTF8StringLength(testString);
        System.out.println("The string occupies " + byteCount + " bytes in UTF-8.");
    }

    public static int getUTF8StringLength(String input) {
        try {
            byte[] utf8Bytes = input.getBytes("UTF-8");
            return utf8Bytes.length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 in case of an error
        }
    }
}
