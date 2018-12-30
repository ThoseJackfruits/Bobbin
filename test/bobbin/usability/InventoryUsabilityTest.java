package bobbin.usability;

import bobbin.usability.util.BaseUsabilityTest;
import bobbin.usability.util.BufferedUserInput;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

class InventoryUsabilityTest extends BaseUsabilityTest {

    @Test
    void openInventoryAndInteractWithItem() {
        final BufferedReader reader = new BufferedUserInput()
                .appendLine("new")
                .appendLine("inventory")
                .appendLine("2")
                .appendLine("back")
                .appendLine("back")
                .appendLine("main menu")
                .build(baos, writer);

        assertTimeout(
                Duration.ofSeconds(1),
                () -> run(reader, playerCharacter));
    }
}
