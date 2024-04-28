package logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class AppLogger {
    private static final Logger LOGGER = Logger.getLogger(AppLogger.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO); // Set the default logging level
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize logger", e);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
