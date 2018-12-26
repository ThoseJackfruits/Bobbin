package bobbin.unit.interaction;

import bobbin.interaction.ConsolePrompt;
import bobbin.io.settings.Settings;
import bobbin.items.Item;
import bobbin.usability.util.BufferedUserInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.util.List;

class ConsolePromptTest extends BaseConsoleTest {

    @Test
    void testGetResponseInt() {
        final String prompt = "Enter a Number: ";
        final BufferedReader reader = new BufferedUserInput()
                .appendLine(1)
                .appendLine(10)
                .build(baos, writer);

        assertEquals(1, ConsolePrompt.getResponseInt(reader, writer, prompt));
        assertEquals(10, ConsolePrompt.getResponseInt(reader, writer, prompt));

        assertPromptOutput(prompt);
    }

    @Test
    void testGetResponseString() {
        final String prompt = "Say a thing: ";
        final String[] responses = { "flabbergasted", "darn-tootin'" };
        final BufferedReader reader = new BufferedUserInput()
                .appendAllOnNewLines(responses)
                .build(baos, writer);

        for (String response : responses) {
            assertEquals(response, ConsolePrompt.getResponseString(reader, writer, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    void testGetChoiceIndexFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = { "1", "2" };
        final BufferedReader reader = new BufferedUserInput()
                .appendAllOnNewLines(responses)
                .build(baos, writer);

        for (String response : responses) {
            assertEquals(
                    Integer.parseInt(response) - 1,
                    ConsolePrompt.getChoiceIndex(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    void testGetChoiceFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = { "1", "2" };
        final BufferedReader reader = new BufferedUserInput()
                .appendAllOnNewLines(responses)
                .build(baos, writer);

        for (String response : responses) {
            assertEquals(
                    list.get(Integer.parseInt(response) - 1),
                    ConsolePrompt.getChoice(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    void testGetChoiceIndex_byName() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = { Settings.MESSAGES_BUNDLE.getString("Items.BLUEBERRY.name"),
                Settings.MESSAGES_BUNDLE.getString("Items.FLOUR.name") };
        final int[] expected = new int[] { 0, 1 };
        final BufferedReader reader = new BufferedUserInput()
                .appendAllOnNewLines(responses)
                .build(baos, writer);

        for (int index : expected) {
            assertEquals(index, ConsolePrompt.getChoiceIndex(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }
}
