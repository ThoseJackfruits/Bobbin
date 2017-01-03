package text_engine.usability;

import java.io.BufferedReader;

import text_engine.interaction.ExitToException;
import text_engine.interaction.Interactive;
import text_engine.unit.interaction.BaseConsoleTest;

public class BaseUsabilityTest extends BaseConsoleTest {

    protected void run(BufferedReader reader) {
        Interactive next = playerCharacter;

        while (true) {
            try {
                next.interact(null, reader, writer);
            }
            catch (Interactive.ResetStackException e) {
                next = e.then;
            }
            catch (ExitToException e) {
                System.out.print("Test completed.");
            }
        }
    }
}
