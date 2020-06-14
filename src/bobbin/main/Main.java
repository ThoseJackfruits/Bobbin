package bobbin.main;

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
import bobbin.interaction.console.Console;
import bobbin.interaction.console.SystemConsole;
import bobbin.items.BaseGameEntity;
import bobbin.menus.MainMenu;
import bobbin.situations.SituationNode;
import bobbin.situations.SituationRoot;

public class Main {

    public static void main(String[] args) {
        start(MainMenu.dummyPlayerCharacter(), new SystemConsole());
    }

    private static void start(
            PlayerCharacter playerCharacter, Console console) {
        PlayerCharacter actor = playerCharacter;
        MainMenu mainMenu = new MainMenu();
        Interactive next = mainMenu;

        for (;;) {
            try {
                next.interact(actor, null, console);
            }
            catch (Interactive.ResetStackException e) {
                next = e.then;
                actor = e.actor;
            }
            catch (MainMenu.ExitToMainMenuException e) {
                mainMenu.saveGame(console, playerCharacter);
                next = mainMenu;
            }
            catch (Interactive.ExitToSystemException e) {
                System.out.println("Goodbye!");
                break;
            }
            catch (ExitToException e) {
                System.err.println("Unhandled ExitToException: " + e.getClass().getName());
                break;
            }
        }
    }

    /**
     * Build a room, allowing the player to configure their character.
     *
     * @param console player input/output
     *
     * @return {@link PlayerCharacter} in the new game.
     */
    public static PlayerCharacter buildNewGame(Console console) {
        Room startingRoom = new Room("Starting Room", "A Whole New Room");
        return new PlayerCharacter(
                ConsolePrompt.getResponseString(console, "Character Name"),
                ConsolePrompt.getResponseString(console, "Character Tagline"),
                startingRoom);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static BaseGameEntity buildStockGame() {
        Room startingRoom = new Room("Starting Room", "The room you start in",
                                     Items.getCopiesOf(Items.BLUEBERRY, Items.BED));
        Room otherRoom = new Room("Another Room", "Not the room you start in",
                                  Items.getCopiesOf(Items.FLOUR));

        Door door = new Door("A door", "Door between starting room and other room", true,
                             startingRoom, otherRoom);

        new NonPlayerCharacter(
                "Non Player Character",
                "An NPC, initially in Room 2.",
                otherRoom,
                new SituationRoot().addChildNodes(
                        new SituationNode("Greetings, stranger.", "(no response)",
                                          GameCharacterEffect.NULL,
                                          new BaseAction("Okay...", "[growling]", Action.NULL)),
                        new SituationNode("You are mean.", "Yes. Yes I am.",
                                          GameCharacterEffect.CLEAR_INVENTORY, Action.NULL)),
                Items.getCopyOf(Items.WATER).get(),
                door.makeKey("Mysterious Key", "Must fit something."));

        final PlayerCharacter pc = new PlayerCharacter(
                "The Mighty Whitey", "It's you.", startingRoom,
                Items.getCopyOf(Items.WATER).get(),
                Items.getCopyOf(Items.FLOUR).get(),
                door.makeKey("Key to a door", "Might open something"));

        return new BaseGameEntity() {
            @Override
            public int respondToInteraction(
                    PlayerCharacter actor, BaseGameEntity from,
                    Console console)
                    throws ExitToException {
                throw new ResetStackException(pc, pc);
            }
        };
    }

}
