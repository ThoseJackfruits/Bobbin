package bobbin.unit.interaction;

import bobbin.interaction.Printers;
import bobbin.io.settings.Settings;
import bobbin.items.Inventory;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;
import bobbin.usability.util.TestConsole;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrintersTest extends BaseUnitTest {

    private final TestConsole console = new TestConsole();

    private <T> void commonListAssertions(List<T> list, String response, boolean ordered) {
        assertTrue(response.contains(list.get(0).toString()), "should have first item");
        assertTrue(
                response.contains(list.get(list.size() - 1).toString()),
                "should have last item");

        if (ordered) {
            assertTrue(response.contains("1. "), "should not have number prefixes");
            assertFalse(response.startsWith("- "), "should have dash prefixes");
        }
        else {
            assertFalse(response.contains("1. "), "should not have number prefixes");
            assertTrue(response.startsWith("- "), "should have dash prefixes");
        }
    }

    @Test
    void testPrintUnordered_list() {
        Inventory inventory = playerCharacter.getInventory();
        Printers.printUnordered(console, inventory);
        final String response = new String(console.getStream().toByteArray());

        commonListAssertions(inventory, response, false);
    }

    @Test
    void testPrintOrdered_list() {
        Inventory list = playerCharacter.getInventory();
        Printers.printOrdered(console, list);
        final String response = new String(console.getStream().toByteArray());

        commonListAssertions(list, response, true);
    }

    @Test
    void testPrintOrdered_array() {
        Item[] array = playerCharacter.getInventory().toArray();
        Printers.printOrdered(console, array);
        final String response = new String(console.getStream().toByteArray());

        commonListAssertions(Arrays.asList(array), response, true);
    }

    @Test
    void testPrint() {
        Item item = playerCharacter.getInventory().get(0);
        Printers.print(console, item);

        final String response = new String(console.getStream().toByteArray());
        assertEquals(
                Settings.MESSAGES_BUNDLE.getString("Messages.prefix.primary") + item.toString()
                        + System.lineSeparator(), response);
    }

    @Test
    void testPrintBooleanPrompt() {
        Boolean[] defaultResponses = new Boolean[] { null, false, true };
        String[] expectedOptions = new String[] {
                Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions"),
                Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions_noDefault"),
                Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions_yesDefault") };
        String prompt = "yes or no?";

        for (int i = 0; i < defaultResponses.length; i++) {
            Printers.printBooleanPrompt(console, prompt, defaultResponses[ i ]);
            final String response = new String(console.getStream().toByteArray());
            assertTrue(response.contains(prompt));
            assertTrue(response.contains(expectedOptions[ i ]));
        }
    }
}
