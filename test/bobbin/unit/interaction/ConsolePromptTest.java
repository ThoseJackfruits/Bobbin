package bobbin.unit.interaction;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.List;

import bobbin.interaction.ConsolePrompt;
import bobbin.io.settings.Settings;
import bobbin.items.Item;
import bobbin.usability.BufferedUserInput;

public class ConsolePromptTest extends BaseConsoleTest {

    @Test
    public void testGetResponseInt() {
        final String prompt = "Enter a Number: ";
        final BufferedReader reader = new BufferedUserInput().appendLine(1).appendLine(10).build();

        Assert.assertEquals(1, ConsolePrompt.getResponseInt(reader, writer, prompt));
        Assert.assertEquals(10, ConsolePrompt.getResponseInt(reader, writer, prompt));

        assertPromptOutput(prompt);
    }

    @Test
    public void testGetResponseString() {
        final String prompt = "Say a thing: ";
        final String[] responses = {"flabbergasted", "darn-tootin'"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            Assert.assertEquals(response, ConsolePrompt.getResponseString(reader, writer, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void testGetChoiceIndexFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            Assert.assertEquals(Integer.parseInt(response) - 1,
                                ConsolePrompt.getChoiceIndex(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void testGetChoiceFromList() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = {"1", "2"};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (String response : responses) {
            Assert.assertEquals(list.get(Integer.parseInt(response) - 1),
                                ConsolePrompt.getChoice(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }

    @Test
    public void testGetChoiceIndex_byName() {
        final String prompt = "Choose from the list: ";
        final List<Item> list = playerCharacter.getInventory();

        final String[] responses = {Settings.MESSAGES_BUNDLE.getString("Items.BLUEBERRY.name"),
                                    Settings.MESSAGES_BUNDLE.getString("Items.FLOUR.name")};
        final int[] expected = new int[]{0, 1};
        final BufferedReader reader = new BufferedUserInput().appendAllOnNewLines(responses).build();

        for (int index : expected) {
            Assert.assertEquals(index, ConsolePrompt.getChoiceIndex(reader, writer, list, prompt));
        }

        assertPromptOutput(prompt);
    }
}
