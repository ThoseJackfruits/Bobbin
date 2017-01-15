package bobbin.interaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import javax.naming.directory.InvalidSearchFilterException;

import bobbin.constants.Globals;

/**
 * The primary method of interacting with the player console.
 */
public class ConsolePrompt {

    /**
     * Protect constructor, since it is a static only class
     */
    protected ConsolePrompt() {
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
    public static int getResponseInt(BufferedReader reader, PrintWriter writer, String prompt)
            throws NumberFormatException {
        Integer selection = null;

        while (selection == null) {
            try {
                selection = Integer.parseInt(getResponseString(reader, writer, prompt));
            }
            catch (NumberFormatException e) {
                Printers.printMessage(writer, "Error.invalidInput.generic");
            }
        }

        return selection;
    }

    /**
     * Ask a boolean (yes/no) question to the player, optionally providing a default response.
     *
     * @param reader        to read response from
     * @param writer        to print prompt to
     * @param prompt        to present to the player when asking for input
     * @param defaultChoice the default response of the player, if the player submits empty input. If
     *                      {@code null}, an explicit response is required.
     * @return the player's response
     */
    public static boolean getChoiceBoolean(BufferedReader reader, PrintWriter writer,
                                           String prompt, Boolean defaultChoice) {
        boolean choice;
        while (true) {
            Printers.printBooleanPrompt(writer, prompt, defaultChoice);
            try {
                choice = getChoice(defaultChoice, reader.readLine());
                break;
            }
            catch (InputMismatchException ignored) {
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return choice;
    }

    private static boolean getChoice(Boolean defaultChoice, String response)
            throws InputMismatchException {
        Boolean choice = getBoolean(response);
        if (choice != null) {
            return choice;
        }
        if (defaultChoice == null) {
            throw new InputMismatchException();
        }
        return defaultChoice;
    }

    private static Boolean getBoolean(String response) {
        if ("y".equalsIgnoreCase(response)) {
            return true;
        }
        else if ("n".equalsIgnoreCase(response)) {
            return false;
        }

        return null;
    }

    /**
     * Prompt the player for input with a generic prompt and get back the raw string that they input.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @return player input
     */
    public static String getResponseString(BufferedReader reader, PrintWriter writer) {
        return getResponseString(reader, writer, null);
    }

    /**
     * Prompt the player for input and get back the raw string that they input.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param prompt to present to the user when asking for input. If {@code null} or {@link
     *               String#isEmpty()}, will print generic prompt.
     * @return player input
     */
    public static String getResponseString(BufferedReader reader, PrintWriter writer, String prompt) {
        String response = null;

        while (response == null) {
            if (prompt == null || prompt.isEmpty()) {
                Printers.printGenericPrompt(writer);
            }
            else {
                Printers.printNamedPrompt(writer, prompt);
            }

            try {
                response = reader.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    /**
     * Get the index of an {@link Interactive} from the given list whose name or description matches
     * the player's response.
     *
     * @param list     the {@link List<T>} to choose from
     * @param response the player's response to search for in the list
     * @param <T>      the type of {@link Interactive} objects in the given list
     * @return the index of the item that matches
     * @throws InvalidSearchFilterException the number of matches found is not equal to 1
     */
    private static <T extends Interactive> int findChoiceIndexByName(List<T> list, String response)
            throws InvalidSearchFilterException {
        String normalisedResponse = response.toLowerCase(Globals.locale);
        ArrayList<Integer> indexesFound = new ArrayList<>(2);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().toLowerCase(Globals.locale).contains(normalisedResponse)) {
                indexesFound.add(i);
            }

            if (indexesFound.size() > 1) {  // Found more than 1 match
                throw new InvalidSearchFilterException("Error.invalidInput.search.beMoreSpecific");
            }
        }

        if (indexesFound.size() < 1) {  // Found less than 1 match
            throw new InvalidSearchFilterException("Error.invalidInput.generic");
        }

        return indexesFound.get(0);  // Goldilocks
    }

    /**
     * Get an {@link Interactive} from the given list whose name or description matches the player's
     * response.
     *
     * @param list     the {@link List<T>} to choose from
     * @param response the player's response to search for in the list
     * @param <T>      the type of {@link Interactive} objects in the given list
     * @return the index of the item that matches
     * @throws InvalidSearchFilterException the number of matches found is not equal to 1
     */
    private static <T extends Interactive> T findChoiceByName(List<T> list, String response)
            throws InvalidSearchFilterException {
        return list.get(findChoiceIndexByName(list, response));
    }

    /**
     * Given a list, ask the player to make a selection from that list and return the index of their
     * selection in that list.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param list   for the player to choose from
     * @param prompt to display to the user
     */
    public static <T extends Interactive> int
    getChoiceIndex(BufferedReader reader, PrintWriter writer, List<T> list, String prompt) {
        Printers.printOrdered(writer, list);

        Integer choice = null;
        while (choice == null || choice < 0 || choice >= list.size()) {
            String response = getResponseString(reader, writer, prompt);

            try {
                choice = Integer.parseInt(response) - 1;  // Visual list is 1-indexed
            }
            catch (NumberFormatException numberException) {
                try {
                    choice = findChoiceIndexByName(list, response);
                }
                catch (InvalidSearchFilterException e) {
                    Printers.printMessage(writer, e.getMessage());
                }
            }
        }
        return choice;
    }

    /**
     * Given a list, ask the player to make a selection from that list and return the index of their
     * selection in that list.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param array  for the player to choose from
     */
    public static <T extends Interactive> int
    getChoiceIndex(BufferedReader reader, PrintWriter writer, T[] array, String prompt) {
        return getChoiceIndex(reader, writer, Arrays.asList(array), prompt);
    }

    /**
     * Given a list, ask the player to make a selection from that list and return the selected object.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param list   for the player to choose from
     */
    public static <T extends Interactive> T getChoice(BufferedReader reader, PrintWriter writer,
                                                      List<T> list, String prompt) {
        return list.get(getChoiceIndex(reader, writer, list, prompt));
    }

    /**
     * Given an array, ask the player to make a selection from that array and return the selected
     * object.
     *
     * @param reader to read response from
     * @param writer to print prompt to
     * @param array  for the player to choose from
     */
    public static <T extends Interactive> T getChoice(BufferedReader reader, PrintWriter writer,
                                                      T[] array, String prompt) {
        return getChoice(reader, writer, Arrays.asList(array), prompt);
    }
}
