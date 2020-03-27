package com.example.RestaurantManager.services;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class PropertiesService {

    transient String value = "";
    InputStream inputStream;

    public String getPropValues(String key) throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";
            inputStream = PropertiesService.class.getClassLoader().getResourceAsStream(propFileName);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // get the property value and print it out

            value = prop.getProperty(key);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return value;
    }
}