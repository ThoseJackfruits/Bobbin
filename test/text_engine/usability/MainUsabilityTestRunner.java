package text_engine.usability;

import java.io.BufferedReader;
import java.io.StringReader;

import text_engine.interaction.ExitToException;
import text_engine.unit.interaction.BaseConsoleTest;

public class MainUsabilityTestRunner extends BaseConsoleTest {

    public static void main(String[] args) {
        final BufferedReader reader = new BufferedReader(new StringReader("0\n"));
        new MainUsabilityTestRunner().start(reader);
    }

    private void start(BufferedReader reader) {
        try {
            playerCharacter.interact(playerCharacter, null, reader, writer);
        }
        catch (ExitToException e) {
            System.out.print("Test completed.");
        }
    }
}
