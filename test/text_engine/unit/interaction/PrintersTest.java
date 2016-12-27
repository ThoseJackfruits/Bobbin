package text_engine.unit.interaction;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import text_engine.interaction.Printers;
import text_engine.items.Item;
import text_engine.unit.BaseUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrintersTest extends BaseUnitTest {

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final PrintWriter writer = new PrintWriter(baos);

    private void commonListAssertions(List list, String response) {
        assertTrue("should have first item", response.contains(list.get(0).toString()));
        assertTrue("should have last item", response.contains(list.get(list.size() - 1).toString()));
    }

    @Test
    public void printUnorderedList() {
        List list = playerCharacter.getInventory();
        Printers.printUnorderedList(writer, list);
        final String response = new String(baos.toByteArray());

        assertFalse("should not have number prefixes", response.contains("1. "));
        assertTrue("should have dash prefixes", response.startsWith("- "));
        commonListAssertions(list, response);
    }

    @Test
    public void printOrderedList() {
        List list = playerCharacter.getInventory();
        Printers.printOrderedList(writer, list);
        final String response = new String(baos.toByteArray());

        assertTrue("should have number prefixes", response.contains("1. "));
        assertFalse("should not have dash prefixes", response.startsWith("- "));
        commonListAssertions(list, response);
    }

    @Test
    public void print() {
        Item item = playerCharacter.getInventory().get(0);
        Printers.print(writer, item);

        final String response = new String(baos.toByteArray());
        assertEquals(item.toString(), response);
    }
}
