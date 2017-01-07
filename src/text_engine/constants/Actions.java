package text_engine.constants;

import text_engine.characters.GameCharacter;
import text_engine.interaction.Interactive;
import text_engine.interaction.actions.BaseAction;

public class Actions {

    public static final BaseAction LOOK_AROUND =
            new BaseAction(Prompts.messages.getString("Actions.LOOK_AROUND.name"),
                           Prompts.messages.getString("Actions.LOOK_AROUND.description"),
                           GameCharacter::getLocation);

    public static final BaseAction OPEN_INVENTORY =
            new BaseAction(Prompts.messages.getString("Actions.OPEN_INVENTORY.name"), "",
                           GameCharacter::getInventory);

    public static final BaseAction EXIT_GAME =
            new BaseAction(Prompts.messages.getString("Actions.EXIT_GAME.name"), "",
                           Interactive::exitGame);
}
