package text_engine.constants;

import java.util.Locale;
import java.util.ResourceBundle;

public class Prompts {
    public static final Locale locale = Locale.getDefault();
    public static final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);
}
