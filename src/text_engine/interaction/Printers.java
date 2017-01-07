package text_engine.interaction;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import text_engine.constants.Globals;

/**
 * Primary method of printing to the player console.
 */
public class Printers {

    private static final MessageFormat formatter = new MessageFormat("", Globals.locale);

    /**
     * Protect constructor, since it is a static only class
     */
    protected Printers() {
    }

    private static String format(String messageKey, Object... arguments) {
        formatter.applyPattern(Globals.messages.getString(messageKey));
        return formatter.format(arguments);
    }

    public static void printMessage(PrintWriter writer, String messageKey, Object... arguments) {
        String message = (arguments != null && arguments.length > 0)
                         ? format(messageKey, arguments)
                         : Globals.messages.getString(messageKey);
        writer.println(message);
        writer.flush();
    }

    /**
     * Standard method of printing multiple items where order is not important.
     *
     * Should <i>not</i> be used when the player needs to make a selection from the list. Use {@link
     * #printOrdered(PrintWriter, List)} instead.
     *
     * @param writer     to print prompt to
     * @param collection for the player to choose from
     */
    public static void printUnordered(PrintWriter writer, Collection<Interactive> collection) {
        for (Interactive gameEntity : collection) {
            writer.println(format("Prompts.unorderedList", gameEntity));

            gameEntity.setSeen();
        }
        writer.flush();
    }

    /**
     * Standard method of printing a list with numbered (1-indexed) prefixes.
     *
     * @param writer to print prompt to
     * @param list   for the player to choose from
     */
    public static <T extends Interactive> void printOrdered(PrintWriter writer, List<T> list) {
        int i = 1;
        for (Interactive gameEntity : list) {
            writer.println(format("Prompts.orderedList", i, gameEntity));

            ++i;
            gameEntity.setSeen();
        }
        writer.flush();
    }

    /**
     * Standard method of printing an array with numbered (1-indexed) prefixes.
     *
     * @param writer to print prompt to
     * @param array  for the player to choose from
     */
    public static void printOrdered(PrintWriter writer, Interactive[] array) {
        printOrdered(writer, Arrays.asList(array));
    }

    public static void print(PrintWriter writer, Interactive gameEntity) {
        writer.printf(gameEntity.toString()).flush();
        gameEntity.setSeen();
    }

    /**
     * Prints out a boolean prompt to the user, hinting at the {@code defaultChoice}.
     *
     * @param writer        to print prompt to
     * @param prompt        to display to the user
     * @param defaultChoice the default response of the player, if the player submits empty input. If
     *                      {@code null}, there is no default hinted.
     */
    public static void printBooleanPrompt(PrintWriter writer, String prompt, Boolean defaultChoice) {
        String options;

        if (defaultChoice == null) {
            options = Globals.messages.getString("Prompts.booleanOptions");
        }
        else if (defaultChoice) {
            options = Globals.messages.getString("Prompts.booleanOptions_yesDefault");
        }
        else {
            options = Globals.messages.getString("Prompts.booleanOptions_noDefault");
        }

        writer.println(format("Prompts.booleanPrompt", prompt, options));
        writer.flush();
    }

    public static void printGenericPrompt(PrintWriter writer, String prompt) {
        writer.println(format("Prompts.genericPrompt", prompt));
        writer.flush();
    }
}
