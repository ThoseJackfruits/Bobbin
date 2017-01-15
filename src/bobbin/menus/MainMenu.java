package bobbin.menus;

import bobbin.characters.GameCharacter;
import bobbin.constants.Actions;
import bobbin.interaction.actions.ActionList;
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.items.BaseGameEntity;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class MainMenu extends Menu {

    @Override
    protected ActionList actions(GameCharacter actor, BaseGameEntity from,
                                 BufferedReader reader, PrintWriter writer) {
        ActionList actions = super.actions(actor, from, reader, writer);

        if (SaveGameSerial.hasActiveSave()) {
            actions.add(Actions.CONTINUE);
        }

        actions.add(Actions.NEW_GAME);
        actions.add(Actions.EXIT_GAME);

        return actions;
    }
}
