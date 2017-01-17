package bobbin.constants;

import java.util.Locale;
import java.util.ResourceBundle;

import bobbin.io.settings.Settings;

public class Globals {

    public static final Locale locale = Locale.getDefault();
    public static final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);
    public static final Settings settings = Settings.loadOrDefaults();

    // Language-independent text constants
    public static final String dialogueSeparator = ":s:";
}
