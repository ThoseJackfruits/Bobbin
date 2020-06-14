package bobbin.unit.interaction;

import bobbin.interaction.ConsolePrompt;
import bobbin.io.settings.Settings;
import bobbin.items.Item;
import bobbin.usability.util.BufferedUserInput;
import bobbin.usability.util.TestConsole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsolePromptTest extends BaseConsoleTest {

    @Test
    void testGetResponseInt() {
        final String prompt = "Enter a Number: ";
        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendLine(1)
                .appendLine(10));

        assertEquals(1, ConsolePrompt.getResponseInt(console, prompt));
        assertEquals(10, ConsolePrompt.getResponseInt(console, prompt));

        assertPromptOutput(console, prompt);
    }

    @Test
    void testGetResponseString() {
        final String prompt = "Say a thing: ";
        final String[] responses = { "flabbergasted", "darn-tootin'" };
        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendAllOnNewLines(responses));

        for (String response : responses) {
            assertEquals(response, ConsolePrompt.getResponseString(console, prompt));
        }

        assertPromptOutput(console, prompt);
    }

    @Test
    void testGetChoiceIndexFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = { "1", "2" };
        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendAllOnNewLines(responses));

        for (String response : responses) {
            assertEquals(
                    Integer.parseInt(response) - 1,
                    ConsolePrompt.getChoiceIndex(console, list, prompt));
        }

        assertPromptOutput(console, prompt);
    }

    @Test
    void testGetChoiceFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = { "1", "2" };
        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendAllOnNewLines(responses));

        for (String response : responses) {
            assertEquals(
                    list.get(Integer.parseInt(response) - 1),
                    ConsolePrompt.getChoice(console, list, prompt));
        }

        assertPromptOutput(console, prompt);
    }

    @Test
    void testGetChoiceIndex_byName() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = { Settings.MESSAGES_BUNDLE.getString("Items.BLUEBERRY.name"),
                Settings.MESSAGES_BUNDLE.getString("Items.FLOUR.name") };
        final int[] expected = new int[] { 0, 1 };
        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendAllOnNewLines(responses));

        for (int index : expected) {
            assertEquals(index, ConsolePrompt.getChoiceIndex(console, list, prompt));
        }

        assertPromptOutput(console, prompt);
    }
}
