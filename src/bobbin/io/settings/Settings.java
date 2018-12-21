package bobbin.io.settings;

import bobbin.io.gamedata.PersistedGameDataSerial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Settings extends Properties {

    private static Settings settings;

    public static Settings getSettings() {
        if (settings == null) {
            settings = Settings.loadOrDefaults();
        }
        return settings;
    }

    public static final Locale LOCALE = Locale.getDefault();
    public static final ResourceBundle
            MESSAGES_BUNDLE = ResourceBundle.getBundle("MessagesBundle", LOCALE);

    public static final String DIALOGUE_SEPARATOR = ":s:";
    public static final String PERSISTED_GAME_DATA_BASE_FOLDER = "BobbinSaves";
    private static final String SETTINGS_FILE_NAME = "settings.bbn";

    private final PersistedGameDataSerial gameData =
            new PersistedGameDataSerial(SETTINGS_FILE_NAME);

    public static class Keys {
        public static final String CURRENT_SAVE = "currentSave";
        public static final String LANGUAGE = "language";
        public static final String COUNTRY = "country";
    }

    public Settings() throws IOException, InterruptedException {
    }

    public Settings(Properties defaults) throws IOException, InterruptedException {
        super(defaults);
    }

    /**
     * Load the {@link Settings} from the standard settings file.
     *
     * @throws IOException {@link Properties#load(InputStream)} throws an {@link IOException}
     */
    public synchronized void load() throws IOException {
        super.load(gameData.getInputStream());
    }

    /**
     * Store the {@link Settings} in the standard settings file.
     *
     * @throws IOException {@link Properties#store(OutputStream, String)} throws an {@link
     *                     IOException}
     */
    public void store() throws IOException {
        super.store(gameData.getOutputStream(), gameData.getDescription());
    }

    /**
     * Get default {@link Settings}.
     *
     * @return default {@link Settings}
     * @throws IOException          if the constructor throws an {@link IOException}
     * @throws InterruptedException if the constructor throws an {@link InterruptedException}
     */
    public static Settings defaults() throws IOException, InterruptedException {
        Settings settings = new Settings();

        settings.setProperty(Keys.CURRENT_SAVE, "");
        settings.setProperty(Keys.LANGUAGE, LOCALE.getLanguage());
        settings.setProperty(Keys.COUNTRY, LOCALE.getCountry());

        return settings;
    }

    /**
     * Try to load an existing settings file, falling back to loading and saving the default settings
     * if no existing settings could be found.
     *
     * @return existing settings, or defaults if no existing settings could be found, or {@code null}
     * if an error occurred with both methods.
     */
    public static Settings loadOrDefaults() {
        try {
            Settings settings = new Settings();
            settings.load();
            return settings;
        }
        catch (InterruptedException | IOException ignored) {

        }

        try {
            Settings defaultSettings = defaults();
            defaultSettings.store();
            return defaults();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
