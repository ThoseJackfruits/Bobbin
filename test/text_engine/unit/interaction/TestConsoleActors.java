package text_engine.unit.interaction;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import text_engine.interaction.ConsoleActors;
import text_engine.items.Item;
import text_engine.unit.BaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestConsoleActors extends BaseTest {
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final PrintWriter writer = new PrintWriter(baos);

    private void testPromptOutput(String prompt) {
        final String consoleOutput = new String(baos.toByteArray());
        assertTrue(consoleOutput.contains(prompt));
    }

    @Test
    public void testGetResponseInt() {
        final String prompt = "Enter a Number: ";
        final BufferedReader reader = new BufferedReader(new StringReader("1\n10\n"));

        assertEquals(1, ConsoleActors.getResponseInt(reader, writer, prompt));
        assertEquals(10, ConsoleActors.getResponseInt(reader, writer, prompt));

        testPromptOutput(prompt);
    }

    @Test
    public void testGetResponseString() {
        final String prompt = "Say a thing: ";
        final String[] responses = {"flabbergasted", "darn-tootin'"};
        final String input = String.join(System.lineSeparator(), responses);
        final BufferedReader reader = new BufferedReader(new StringReader(input));

        for (String response : responses) {
            assertEquals(response, ConsoleActors.getResponseString(reader, writer, prompt));
        }

        testPromptOutput(prompt);
    }

    @Test
    public void testGetChoiceIndexFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> listToChooseFrom = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final String input = String.join(System.lineSeparator(), responses);
        final BufferedReader reader = new BufferedReader(new StringReader(input));

        for (String response : responses) {
            assertEquals(
                    Integer.parseInt(response) - 1,
                    ConsoleActors.getChoiceIndexFromList(reader, writer, listToChooseFrom, prompt));
        }

        testPromptOutput(prompt);
    }

    @Test
    public void testGetChoiceFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> listToChooseFrom = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final String input = String.join(System.lineSeparator(), responses);
        final BufferedReader reader = new BufferedReader(new StringReader(input));

        for (String response : responses) {
            assertEquals(
                    listToChooseFrom.get(Integer.parseInt(response) - 1),
                    ConsoleActors.getChoiceFromList(reader, writer, listToChooseFrom, prompt));
        }

        testPromptOutput(prompt);
    }
}
