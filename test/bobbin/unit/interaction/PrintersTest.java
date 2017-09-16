package bobbin.unit.interaction;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import bobbin.interaction.Printers;
import bobbin.io.settings.Settings;
import bobbin.items.Inventory;
import bobbin.items.Item;
import bobbin.unit.BaseUnitTest;

public class PrintersTest extends BaseUnitTest {

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private final PrintWriter writer = new PrintWriter(stream);

    private void commonListAssertions(List list, String response, boolean ordered) {
        Assert.assertTrue("should have first item", response.contains(list.get(0).toString()));
        Assert.assertTrue("should have last item",
                          response.contains(list.get(list.size() - 1).toString()));

        if (ordered) {
            Assert.assertTrue("should not have number prefixes", response.contains("1. "));
            Assert.assertFalse("should have dash prefixes", response.startsWith("- "));
        } else {
            Assert.assertFalse("should not have number prefixes", response.contains("1. "));
            Assert.assertTrue("should have dash prefixes", response.startsWith("- "));
        }
    }

    @Test
    public void testPrintUnordered_list() {
        List inventory = playerCharacter.getInventory();
        //noinspection unchecked
        Printers.printUnordered(writer, inventory);
        final String response = new String(stream.toByteArray());

        commonListAssertions(inventory, response, false);
    }

    @Test
    public void testPrintOrdered_list() {
        Inventory list = playerCharacter.getInventory();
        Printers.printOrdered(writer, list);
        final String response = new String(stream.toByteArray());

        commonListAssertions(list, response, true);
    }

    @Test
    public void testPrintOrdered_array() {
        Item[] array = playerCharacter.getInventory().toArray();
        Printers.printOrdered(writer, array);
        final String response = new String(stream.toByteArray());

        commonListAssertions(Arrays.asList(array), response, true);
    }

    @Test
    public void testPrint() {
        Item item = playerCharacter.getInventory().get(0);
        Printers.print(writer, item);

        final String response = new String(stream.toByteArray());
        Assert.assertEquals(Settings.MESSAGES_BUNDLE.getString("Messages.prefix.primary") + item.toString()
                            + System.lineSeparator(), response);
    }

    @Test
    public void testPrintBooleanPrompt() {
        Boolean[] defaultResponses = new Boolean[] {null, false, true};
        String[] expectedOptions = new String[] {
                Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions"),
                Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions_noDefault"),
                Settings.MESSAGES_BUNDLE.getString("Prompts.booleanOptions_yesDefault")};
        String prompt = "yes or no?";

        for (int i = 0; i < defaultResponses.length; i++) {
            Printers.printBooleanPrompt(writer, prompt, defaultResponses[i]);
            final String response = new String(stream.toByteArray());
            Assert.assertTrue(response.contains(prompt));
            Assert.assertTrue(response.contains(expectedOptions[i]));
        }
    }
}
