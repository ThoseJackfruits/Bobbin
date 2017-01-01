package text_engine.unit.interaction;

import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import text_engine.interaction.ConsoleActors;
import text_engine.items.Item;
import text_engine.usability.BufferedUserInput;

import static org.junit.Assert.assertEquals;

public class ConsoleActorsTest extends BaseConsoleTest {
    @Test
    public void getResponseInt() {
        final String prompt = "Enter a Number: ";
        final BufferedReader reader = new BufferedUserInput().appendLine(1).appendLine(10).build();

        assertEquals(1, ConsoleActors.getResponseInt(reader, writer, prompt));
        assertEquals(10, ConsoleActors.getResponseInt(reader, writer, prompt));

        assertPromptOutput(prompt);
    }

    @Test
    public void getResponseString() {
        final String prompt = "Say a thing: ";
        final String[] responses = {"flabbergasted", "darn-tootin'"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            assertEquals(response, ConsoleActors.getResponseString(reader, writer, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void getChoiceIndexFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> listToChooseFrom = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            assertEquals(
                    Integer.parseInt(response) - 1,
                    ConsoleActors.getChoiceIndex(reader, writer, listToChooseFrom, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void getChoiceFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> listToChooseFrom = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            assertEquals(
                    listToChooseFrom.get(Integer.parseInt(response) - 1),
                    ConsoleActors.getChoice(reader, writer, listToChooseFrom, prompt));
        }

        assertPromptOutput(prompt);
    }
}
