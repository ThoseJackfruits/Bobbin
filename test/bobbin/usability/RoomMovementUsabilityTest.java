package bobbin.usability;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.characters.PlayerCharacter;
import bobbin.interaction.ExitToException;
import bobbin.interaction.console.Console;
import bobbin.items.BaseGameEntity;
import bobbin.usability.util.BaseUsabilityTest;
import bobbin.usability.util.BufferedUserInput;
import bobbin.usability.util.TestConsole;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class RoomMovementUsabilityTest extends BaseUsabilityTest {

    @Test
    void moveToAnotherRoomAndLookAround() {
        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendLine("new")
                .appendLine("look")
                .appendLine("open")
                .appendLine("back")
                .appendLine("main menu"));
        assertTimeout(Duration.ofSeconds(1), () -> {
            run(console, playerCharacter);
            assertEquals(room2, gameCharacter.getLocation());
        });
    }

    @Test
    void catchesExitToRoomException() {
        // TODO should be mocked with Mockito instead of creating a custom class
        class SpecialDoor extends Door {
            private SpecialDoor(Room room1, Room room2) {
                super(false, room1, room2);
            }

            @Override public int respondToInteraction(
                    PlayerCharacter actor,
                    BaseGameEntity from,
                    Console console) throws ExitToException {
                throw new Room.ExitToRoomException();
            }
        }

        Room room4 = new Room("Custom Room 4", "connected to room 1 via special door");
        PlayerCharacter customPlayerCharacter
                = new PlayerCharacter(
                "Player Character",
                "The main Player Character, initially in Room 4.",
                room4);

        new SpecialDoor(room1, room4);

        final TestConsole console = new TestConsole(new BufferedUserInput()
                .appendLine("new")
                .appendLine("look")
                .appendLine("open")
                .appendLine("back")
                .appendLine("main menu"));

        assertTimeout(Duration.ofSeconds(1), () -> {
            run(console, customPlayerCharacter);
            assertEquals(room4, customPlayerCharacter.getLocation());
        });
    }
}
