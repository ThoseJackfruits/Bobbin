package text_engine.constants;

import text_engine.characters.GameCharacter;
import text_engine.interaction.Interactive;
import text_engine.interaction.actions.BaseAction;
import text_engine.items.BaseGameEntity;

public class Actions {

    public static final BaseAction LOOK_AROUND =
            new BaseAction(Globals.messages.getString("Actions.LOOK_AROUND.name"),
                           Globals.messages.getString("Actions.LOOK_AROUND.description"),
                           GameCharacter::getLocation);

    public static final BaseAction OPEN_INVENTORY =
            new BaseAction(Globals.messages.getString("Actions.OPEN_INVENTORY.name"), "",
                           GameCharacter::getInventory);

    public static final BaseAction EXIT_GAME =
            new BaseAction(Globals.messages.getString("Actions.EXIT_GAME.name"), "",
                           Interactive::exitGame);

    public static BaseAction BACK(BaseGameEntity from) {
        return new BaseAction(Globals.messages.getString("Actions.BACK.name"), "",
                              playerCharacter -> from);
    }
}
