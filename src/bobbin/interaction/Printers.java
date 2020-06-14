package bobbin.interaction;

import bobbin.interaction.console.Console;
import bobbin.io.settings.Settings;

import javax.validation.constraints.NotNull;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Primary method of printing to the player console.
 */
public class Printers {

    private static final MessageFormat formatter = new MessageFormat("", Settings.LOCALE);

    /**
     * Protect constructor, since it is a static only class
     */
    protected Printers() {
    }

    public static String format(String messageKey, Object... arguments) {
        formatter.applyPattern(Settings.MESSAGES_BUNDLE.getString(messageKey));
        return formatter.format(arguments);
    }

    public static void printMessage(Console console, String messageKey, Object... arguments) {
        final PrintWriter writer = console.getWriter();
        writer.print(Settings.MESSAGES_BUNDLE.getString("Messages.prefix.secondary"));
        String message = (arguments != null && arguments.length > 0)
                ? format(messageKey, arguments)
                : Settings.MESSAGES_BUNDLE.getString(messageKey);
        writer.println(message);
        writer.flush();
    }

    /**
     * Standard method of printing multiple items where order is not important.
     * <p>
     * Should <i>not</i> be used when the player needs to make a selection from the list. Use {@link
     * #printOrdered(Console, List)} instead.
     *
     * @param console    to print prompt to
     * @param collection for the player to choose from
     */
    public static <T extends Interactive> void printUnordered(Console console, Collection<T> collection) {
        final PrintWriter writer = console.getWriter();
        for (Interactive gameEntity : collection) {
            writer.println(format("Prompts.unorderedList", gameEntity));

            gameEntity.markSeen();
        }
        writer.flush();
    }

    /**
     * Standard method of printing a list with numbered (1-indexed) prefixes.
     *
     * @param console to print prompt to
     * @param list    for the player to choose from
     */
    public static <T extends Interactive> void printOrdered(Console console, List<T> list) {
        final PrintWriter writer = console.getWriter();
        int i = 1;
        for (Interactive gameEntity : list) {
            writer.println(format("Prompts.orderedList", i, gameEntity));

            ++i;
            gameEntity.markSeen();
        }
        writer.flush();
    }

    /**
     * Standard method of printing an array with numbered (1-indexed) prefixes.
     *
     * @param console to print prompt to
     * @param array   for the player to choose from
     */
    public static void printOrdered(Console console, Interactive[] array) {
        printOrdered(console, Arrays.asList(array));
    }

    public static void print(Console console, Interactive gameEntity) {
        final PrintWriter writer = console.getWriter();
        writer.print(Settings.MESSAGES_BUNDLE.getString("Messages.prefix.primary"));
        writer.println(gameEntity.toString());
        writer.flush();
        gameEntity.markSeen();
    }

    public static void println(Console console) {
        final PrintWriter writer = console.getWriter();
        writer.println();
        writer.flush();
    }

    /**
     * Prints out a boolean prompt to the user, hinting at the {@code defaultChoice}.
     *
     * @param console       to print prompt to
     * @param prompt        to display to the user
     * @param defaultChoice the default response of the player, if the player submits empty input. If
     *                      {@code null}, there is no default hinted.
     */
    public static void printBooleanPrompt(
            Console console,
            String prompt,
            Boolean defaultChoice) {
        final PrintWriter writer = console.getWriter();
        String options;

        if (defaultChoice == null) {
            options = Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions");
        }
        else if (defaultChoice) {
            options = Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions_yesDefault");
        }
        else {
            options = Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions_noDefault");
        }

        writer.println(format("Prompts.booleanPrompt", prompt, options));
        writer.flush();
    }

    public static void printGenericPrompt(Console console) {
        final PrintWriter writer = console.getWriter();
        writer.print(Settings.MESSAGES_BUNDLE.getString("Prompts.genericPrompt"));
        writer.flush();
    }

    public static void printNamedPrompt(Console console, @NotNull String prompt) {
        final PrintWriter writer = console.getWriter();
        writer.print(format("Prompts.namedPrompt", Objects.requireNonNull(prompt)));
        writer.flush();
    }
}
