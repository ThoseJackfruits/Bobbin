package bobbin.menus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.io.settings.Settings;
import bobbin.items.BaseGameEntity;
import bobbin.main.Main;

public class MainMenu extends Menu {

    class DummyPlayerCharacter extends PlayerCharacter {
        public DummyPlayerCharacter(String name, String description, Room location) {
            super(name, description, location);
        }
    }

    public static PlayerCharacter dummyPlayerCharacter() {
        Room fakeRoom = new Room("Fake Room", "For dummy character to exist in");
        return new PlayerCharacter("A Dummy Character", "To be used in the main menu", fakeRoom,
                Items.getCopiesOf(Items.BLUEBERRY, Items.FLOUR));
    }

    public void saveGame(PrintWriter writer, PlayerCharacter playerCharacter) {
        try {
            new SaveGameSerial(playerCharacter.getName()
                                              .replace(' ', '_')
                                              .replace(File.separatorChar, '.'))
                    .saveData(playerCharacter);
            Printers.printMessage(writer, "MainMenu.gameSaved");
        }
        catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public MainMenu() {
        super(Settings.MESSAGES_BUNDLE.getString("MainMenu.name"), "");
    }

    public static class ExitToMainMenuException extends ExitToException {

        private final PlayerCharacter playerCharacter;

        public ExitToMainMenuException(PlayerCharacter playerCharacter) {
            this.playerCharacter = playerCharacter;
        }
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
        actions.add(Actions.EXIT_GAME);

        return actions;
    }

    @Override
    public int respondToInteraction(PlayerCharacter actor, BaseGameEntity from, BufferedReader reader,
                                    PrintWriter writer) throws ExitToException {
        Printers.println(writer);
        Printers.print(writer, this);
        Printers.println(writer);

        BaseAction next = ConsolePrompt.getChoice(reader, writer, actions(actor, from), null);

        if (next.equals(Actions.CONTINUE)) {
            SaveGameSerial saveGame = SaveGameSerial.loadActiveSave();
            if (saveGame == null) {
                throw new IllegalStateException("SaveGame is null");
            }
            PlayerCharacter playerCharacter;
            playerCharacter = saveGame.loadData();
            return playerCharacter.interact(playerCharacter, this, reader, writer);
        }

        return next.apply(actor).interact(actor, this, reader, writer);
    }
}
