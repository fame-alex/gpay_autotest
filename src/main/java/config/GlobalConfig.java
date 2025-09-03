package config;
import java.io.IOException;
import java.util.Properties;

public class GlobalConfig {


    public final static String HOST;

    static Properties properties = new Properties();
    static {
        java.io.InputStream inputStream = GlobalConfig.class.getClassLoader().getResourceAsStream("maven.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HOST = properties.getProperty("generalHost");
    }

    //адрес тестового сервера и переменные для авторизации
    public final static String GENERAL_URL = "https://" + HOST + "/";
    public static final String SERVER_LOGIN = "admin";
    public static final String SERVER_PASS = "admin";
    public static final String temp = " ";

    //  Database credentials
    public static String DB_URL = "jdbc:postgresql://" + HOST + ":5432/rtlsdb";
    public static final String USER = "rtls";
    public static final String PASS = "root";


    public GlobalConfig() throws IOException {
    }
}
