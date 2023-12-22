package ru.clevertec.servlet.task.config;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.servlet.task.util.Constants;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class LoadProperties {

    public static Properties properties = new Properties();

    /**
     * Загружает значения пропертей с файла application.yml
     *
     */
    public static Properties getProperties() {
        if (properties.isEmpty()) {
            try {
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.PROPERTIES_FILE_NAME));
            }
            catch (IOException ioe){
                log.error(ioe.getMessage());
            }
        }
        return properties;
    }

}
