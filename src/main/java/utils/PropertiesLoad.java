package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesLoad {
    private static final String propertiesFile = "application.properties";
    private static final Properties properties;

    static {
        log.info("Loading properties...");
        properties = new Properties();
        try (InputStream resourceAsStream = PropertiesLoad.class.getClassLoader().getResourceAsStream(propertiesFile)){
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty (String name){
        return properties.getProperty(name);
    }
}
