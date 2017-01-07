package text_engine.constants;

import java.util.Locale;
import java.util.ResourceBundle;

import text_engine.io.settings.Settings;

public class Globals {

    public static final Locale locale = Locale.getDefault();
    public static final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);
    public static final Settings settings = Settings.loadOrDefaults();
}
