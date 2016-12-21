package text_engine.interaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * The primary method of interacting with the player console.
 */
public class ConsoleActors {

    /**
     * Protect constructor, since it is a static only class
     */
    protected ConsoleActors() {
    }

    /**
     * Get an integer response for a given prompt. The prompt will be shown repeatedly until valid
     * input is given from the player.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param prompt to present to the user when asking for input
     * @return number given by the player
     */
    public static int getResponseInt(BufferedReader reader, PrintWriter writer, String prompt) {
        Integer selection = null;

        while (selection == null) {
            writer.printf("%s: ", prompt).flush();
            try {
                selection = Integer.parseInt(reader.readLine());
            }
            catch (NumberFormatException e) {
                writer.printf("Invalid input: %s", e.getLocalizedMessage()).flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return selection;
    }

    /**
     * Prompt the player for input and get back the raw string that they input.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param prompt to present to the user when asking for input
     * @return player input
     */
    public static String getResponseString(BufferedReader reader, PrintWriter writer, String prompt) {
        String response = null;

        while (response == null) {
            writer.printf("%s: ", prompt).flush();
            try {
                response = reader.readLine();
            }
            catch (IOException e) {
                writer.printf("Invalid input: %s", e.getLocalizedMessage()).flush();
            }
        }

        return response;
    }

    /**
     * Given a list, ask the player to make a selection from that list and return the index of their
     * selection in that list.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param list   for the player to choose from
     */
    public static int getChoiceIndexFromList(BufferedReader reader, PrintWriter writer,
                                             List list, String prompt) {
        Printers.printOrderedList(writer, list);
        return getResponseInt(reader, writer, prompt) - 1; // Visual list is 1-indexed
    }

    /**
     * Given a list, ask the player to make a selection from that list and return the selected object.
     *
     * @param reader   to read response from
     * @param writer  to print prompt to
     * @param list for the player to choose from
     */
    public static <T> T getChoiceFromList(BufferedReader reader, PrintWriter writer,
                                          List<T> list, String prompt) {
        return list.get(getChoiceIndexFromList(reader, writer, list, prompt));
    }
}
