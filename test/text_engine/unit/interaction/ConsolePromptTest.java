package text_engine.unit.interaction;

import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import text_engine.constants.Prompts;
import text_engine.interaction.ConsolePrompt;
import text_engine.items.Item;
import text_engine.usability.BufferedUserInput;

import static org.junit.Assert.assertEquals;

public class ConsolePromptTest extends BaseConsoleTest {

    @Test
    public void getResponseInt() {
        final String prompt = "Enter a Number: ";
        final BufferedReader reader = new BufferedUserInput().appendLine(1).appendLine(10).build();

        assertEquals(1, ConsolePrompt.getResponseInt(reader, writer, prompt));
        assertEquals(10, ConsolePrompt.getResponseInt(reader, writer, prompt));

        assertPromptOutput(prompt);
    }

    @Test
    public void getResponseString() {
        final String prompt = "Say a thing: ";
        final String[] responses = {"flabbergasted", "darn-tootin'"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            assertEquals(response, ConsolePrompt.getResponseString(reader, writer, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void getChoiceIndexFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            assertEquals(
                    Integer.parseInt(response) - 1,
                    ConsolePrompt.getChoiceIndex(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void getChoiceFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            assertEquals(
                    list.get(Integer.parseInt(response) - 1),
                    ConsolePrompt.getChoice(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void getChoiceIndex_byName() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = {Prompts.messages.getString("Items.BLUEBERRY.name"),
                                    Prompts.messages.getString("Items.FLOUR.name")};
        final int[] expected = new int[]{0, 1};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (int index : expected) {
            assertEquals(index, ConsolePrompt.getChoiceIndex(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }
}
