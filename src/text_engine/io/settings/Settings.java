package text_engine.io.settings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import text_engine.constants.Globals;
import text_engine.io.gamedata.PersistedGameDataSerial;

public class Settings extends Properties {

    private final PersistedGameDataSerial gameDataSerial =
            new PersistedGameDataSerial(Globals.messages.getString("Persisted.settingsFile"));

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
        super.load(gameDataSerial.getInputStream());
    }

    /**
     * Store the {@link Settings} in the standard settings file.
     *
     * @throws IOException {@link Properties#store(OutputStream, String)} throws an {@link
     *                     IOException}
     */
    public void store() throws IOException {
        super.store(gameDataSerial.getOutputStream(), gameDataSerial.getDescription());
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

        settings.setProperty("currentSave", "");
        settings.setProperty("language", Globals.locale.getLanguage());
        settings.setProperty("country", Globals.locale.getCountry());

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
        catch (IOException | InterruptedException ignored) {
        }

        return null;
    }
}
