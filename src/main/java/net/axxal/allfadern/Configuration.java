package net.axxal.allfadern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final String fileName = "app.config";
    private static final Properties properties = new Properties();

    private static void checkForEnvironmentFile() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                FileWriter writer = new FileWriter(fileName);
                writer.write("app.token=<TOKEN_GOES_HERE>");
                writer.flush();
                writer.close();
                System.out.println("Please take a look at the app.config before starting the program again.");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProperties() {
        checkForEnvironmentFile();
        try (FileInputStream stream = new FileInputStream(fileName)) {
            properties.load(stream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String getDiscordToken() {
        return properties.getProperty("app.token");
    }
}
