package bobbin.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Items;
import bobbin.interaction.BaseInteractive;
import bobbin.interaction.ExitToException;
import bobbin.menus.MainMenu;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        start(reader, writer, MainMenu.dummyPlayerCharacter());
    }

    private static void start(BufferedReader reader, PrintWriter writer,
                              PlayerCharacter playerCharacter) {
        PlayerCharacter actor = playerCharacter;
        MainMenu mainMenu = new MainMenu();
        BaseInteractive next = mainMenu;

        while (true) {
            try {
                next.interact(actor, null, reader, writer);
            }
            catch (BaseInteractive.ResetStackException e) {
                next = e.then;
                actor = e.actor;
            }
            catch (MainMenu.ExitToMainMenuException e) {
                mainMenu.saveGame(writer, playerCharacter);
                next = mainMenu;
            }
            catch (ExitToException e) {
                System.out.print("Exiting.");
                break;
            }
        }
    }

    public static PlayerCharacter stockGame() {
        Room startingRoom = new Room("Starting Room", "The room you start in",
                                     Items.getCopiesOf(Items.BLUEBERRY, Items.BED));
        Room otherRoom = new Room("Another Room", "Not the room you start in",
                                  Items.getCopiesOf(Items.FLOUR));

        Door door = new Door("A door", "Door between starting room and other room", true,
                             startingRoom, otherRoom);

        return new PlayerCharacter("The Mighty Whitey", "It's you.", startingRoom,
                                   Items.getCopyOf(Items.WATER),
                                   Items.getCopyOf(Items.FLOUR),
                                   door.makeKey("Key to a door", "Might open something"));
    }
}
