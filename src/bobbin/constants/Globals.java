package bobbin.constants;

import java.util.Locale;
import java.util.ResourceBundle;

import bobbin.io.settings.Settings;

public class Globals {

    public static final Locale locale = Locale.getDefault();
    public static final Settings settings = Settings.loadOrDefaults();
    public static final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);

    // Language-independent string constants
    public static final String DIALOGUE_SEPARATOR = ":s:";
    public static final String PERSISTED_GAME_DATA_BASE_FOLDER = "BobbinSaves";
    public static final String SETTINGS_FILE_NAME = "settings.bbn";
}
