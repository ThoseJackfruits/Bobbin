package bobbin.menus;

import bobbin.boundaries.Room;
import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Actions;
import bobbin.interaction.ConsolePrompt;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Printers;
import bobbin.interaction.actions.ActionList;
import bobbin.interaction.actions.BaseAction;
import bobbin.interaction.console.Console;
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.io.settings.Settings;
import bobbin.items.BaseGameEntity;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainMenu extends Menu {

    static PlayerCharacter currentPlayerCharacter;

    static class DummyPlayerCharacter extends PlayerCharacter {
        public DummyPlayerCharacter(String name, String description, Room location) {
            super(name, description, location);
        }
    }

    public static PlayerCharacter dummyPlayerCharacter() {
        Room fakeRoom = new Room("Fake Room", "For dummy character to exist in");
        return new DummyPlayerCharacter("A Dummy Character", "To be used in the main menu", fakeRoom);
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
        final PlayerCharacter playerCharacter = new PlayerCharacter(
                ConsolePrompt.getResponseString(console, "Character Name"),
                ConsolePrompt.getResponseString(console, "Character Tagline"),
                startingRoom);

        MainMenu.currentPlayerCharacter = playerCharacter;

        return playerCharacter;
    }

    public void saveGame(PlayerCharacter playerCharacter) {
        this.saveGame(playerCharacter, null);
    }

    public void saveGame(PlayerCharacter playerCharacter, Console console) {
        Objects.requireNonNull(playerCharacter, "PlayerCharacter must be provided to save the game");
        try {
            new SaveGameSerial(playerCharacter.getName()
                                              .replace(' ', '_')
                                              .replace(File.separatorChar, '.'))
                    .saveData(playerCharacter);
            if (console != null) {
                Printers.printMessage(console, "MainMenu.gameSaved");
            }
        }
        catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public static class ExitToMainMenuException extends ExitToException {
    }

    public MainMenu() {
        super(Settings.MESSAGES_BUNDLE.getString("MainMenu.name"), "");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (MainMenu.currentPlayerCharacter instanceof DummyPlayerCharacter) {
                return;
            }

            this.saveGame(MainMenu.currentPlayerCharacter);
            System.out.println(Settings.MESSAGES_BUNDLE.getString("MainMenu.gameSaved"));
        }));
    }

    @Override
    protected ActionList actions(GameCharacter actor, BaseGameEntity from) {
        ActionList actions = new ActionList();

        if (SaveGameSerial.hasActiveSave()) {
            SaveGameSerial activeSave = SaveGameSerial.loadActiveSave();
            if (activeSave != null && !(activeSave.loadData() instanceof DummyPlayerCharacter)) {
                // Only allow continue if the current player isn't a dummy
                actions.add(Actions.CONTINUE);
            }
        }

        actions.add(Actions.NEW_GAME);
        actions.add(Actions.EXIT_MENU);

        return actions;
    }

    @Override
    public int respondToInteraction(
            PlayerCharacter actor, BaseGameEntity from, Console console) throws ExitToException {
        MainMenu.currentPlayerCharacter = actor;
        Printers.println(console);
        Printers.print(console, this);
        Printers.println(console);

        BaseAction next = ConsolePrompt.getChoice(console, actions(actor, from), null);

        if (next.equals(Actions.CONTINUE)) {
            SaveGameSerial saveGame = SaveGameSerial.loadActiveSave();
            if (saveGame == null) {
                throw new IllegalStateException("SaveGame is null");
            }
            PlayerCharacter playerCharacter = saveGame.loadData();
            MainMenu.currentPlayerCharacter = playerCharacter;
            return playerCharacter.interact(playerCharacter, this, console);
        }

        return next.apply(actor).interact(actor, this, console);
    }
}
