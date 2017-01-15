package text_engine.menus;

import text_engine.characters.GameCharacter;
import text_engine.constants.Actions;
import text_engine.interaction.actions.ActionList;
import text_engine.io.gamedata.SaveGameSerial;
import text_engine.items.BaseGameEntity;

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
