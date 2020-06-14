package bobbin.menus;

import bobbin.boundaries.Room;
import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Actions;
import bobbin.constants.Items;
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

public class MainMenu extends Menu {

    static class DummyPlayerCharacter extends PlayerCharacter {
        public DummyPlayerCharacter(String name, String description, Room location) {
            super(name, description, location);
        }
    }

    public static PlayerCharacter dummyPlayerCharacter() {
        Room fakeRoom = new Room("Fake Room", "For dummy character to exist in");
        return new PlayerCharacter("A Dummy Character", "To be used in the main menu", fakeRoom,
                                   Items.getCopiesOf(Items.BLUEBERRY, Items.FLOUR));
    }

    public void saveGame(Console console, PlayerCharacter playerCharacter) {
        try {
            new SaveGameSerial(playerCharacter.getName()
                                              .replace(' ', '_')
                                              .replace(File.separatorChar, '.'))
                    .saveData(playerCharacter);
            Printers.printMessage(console, "MainMenu.gameSaved");
        }
        catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public MainMenu() {
        super(Settings.MESSAGES_BUNDLE.getString("MainMenu.name"), "");
    }

    public static class ExitToMainMenuException extends ExitToException {
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
        Printers.println(console);
        Printers.print(console, this);
        Printers.println(console);

        BaseAction next = ConsolePrompt.getChoice(console, actions(actor, from), null);

        if (next.equals(Actions.CONTINUE)) {
            SaveGameSerial saveGame = SaveGameSerial.loadActiveSave();
            if (saveGame == null) {
                throw new IllegalStateException("SaveGame is null");
            }
            PlayerCharacter playerCharacter;
            playerCharacter = saveGame.loadData();
            return playerCharacter.interact(playerCharacter, this, console);
        }

        return next.apply(actor).interact(actor, this, console);
    }
}
