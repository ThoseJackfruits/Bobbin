package bobbin.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import bobbin.boundaries.Door;
import bobbin.boundaries.Room;
import bobbin.characters.NonPlayerCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Items;
import bobbin.effects.GameCharacterEffect;
import bobbin.interaction.ConsolePrompt;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Interactive;
import bobbin.interaction.actions.Action;
import bobbin.interaction.actions.BaseAction;
import bobbin.menus.MainMenu;
import bobbin.situations.SituationNode;
import bobbin.situations.SituationRoot;

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
        Interactive next = mainMenu;

        while (true) {
            try {
                next.interact(actor, null, reader, writer);
            }
            catch (Interactive.ResetStackException e) {
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

    /**
     * Build a room, allowing the player to configure their character.
     *
     * @return {@link PlayerCharacter} in the new game.
     */
    public static PlayerCharacter buildNewGame(BufferedReader reader, PrintWriter writer) {
        Room startingRoom = new Room("Starting Room", "A Whole New Room");
        return new PlayerCharacter(
                ConsolePrompt.getResponseString(reader, writer, "Character Name"),
                ConsolePrompt.getResponseString(reader, writer, "Character Tagline"),
                startingRoom);
    }

    public static PlayerCharacter buildStockGame() {
        Room startingRoom = new Room("Starting Room", "The room you start in",
                                     Items.getCopiesOf(Items.BLUEBERRY, Items.BED));
        Room otherRoom = new Room("Another Room", "Not the room you start in",
                                  Items.getCopiesOf(Items.FLOUR));

        Door door = new Door("A door", "Door between starting room and other room", true,
                             startingRoom, otherRoom);

        NonPlayerCharacter nonPlayerCharacter = new NonPlayerCharacter(
                "Non Player Character",
                "An NPC, initially in Room 2.",
                otherRoom,
                new SituationRoot()
                        .addChildNode(
                                new SituationNode("Greetings, stranger.", "(no response)",
                                                  GameCharacterEffect.NULL,
                                                  new BaseAction("Okay...", "[growling]", Action.NULL)))
                        .addChildNode(
                                new SituationNode("You are mean.", "Yes. Yes I am.",
                                                  GameCharacterEffect.CLEAR_INVENTORY, Action.NULL)),
                Items.getCopyOf(Items.WATER));

        return new PlayerCharacter("The Mighty Whitey", "It's you.", startingRoom,
                                   Items.getCopyOf(Items.WATER),
                                   Items.getCopyOf(Items.FLOUR),
                                   door.makeKey("Key to a door", "Might open something"));
    }

}
