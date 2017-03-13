package bobbin.usability;

import org.junit.Test;

import java.io.BufferedReader;

import static org.junit.Assert.assertEquals;

public class RoomMovementUsabilityTest extends BaseUsabilityTest {

    @Test(timeout = 1000)
    public void moveToAnotherRoomAndLookAround() throws Exception {
        final BufferedReader reader = new BufferedUserInput("look")
                .appendLine("open")
                .appendLine("back")
                .appendLine("main menu").build();
        run(reader);
        assertEquals(room2, gameCharacter.getLocation());

        // Need to find out a way to properly mock out user input. This will be fine for now, but it
        // is limiting. This only allows us to give a set of user actions, apply them, then check the
        // state of the game afterwards to make sure everything is as expected. Intermediate state
        // checks, made before all of the mock user input has been consumed, cannot be made currently.
    }
}
