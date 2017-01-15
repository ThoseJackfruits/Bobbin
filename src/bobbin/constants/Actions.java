package bobbin.constants;

import bobbin.boundaries.Door;
import bobbin.characters.GameCharacter;
import bobbin.interaction.Interactive;
import bobbin.interaction.Printers;
import bobbin.interaction.actions.BaseAction;
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.items.BaseGameEntity;
import bobbin.items.Item;
import bobbin.main.Main;

// I think these will need to be refactored soon. It's fine
// to have them in one big file right now, but I think that
// is going to get inconvenient fast. I'm thinking each
// Interactive object could have a public, static Action
// class nested inside it with the relevant Actions used
// (primarily) by that class. This will allow actions to
// be accessed globally while still showing who primarily
// uses them. It seems like most actions are only used by
// a single class anyways, so I think that would be fine.
public class Actions {

    public static BaseAction BACK(BaseGameEntity from) {
        return new BaseAction(Globals.messages.getString("Actions.BACK.name"),
                              "",
                              playerCharacter -> from);
    }

    public static BaseAction CONSUME(Item item) {
        return new BaseAction(Globals.messages.getString("Actions.CONSUME.name"),
                              "",
                              playerCharacter -> item.consume());
    }

    public static BaseAction CONTINUE =
        new BaseAction(Globals.messages.getString("Actions.CONTINUE.name"),
                       "",
                       playerCharacter -> SaveGameSerial.loadActiveSave());

    public static final BaseAction EXIT_GAME =
            new BaseAction(Globals.messages.getString("Actions.EXIT_GAME.name"),
                           "",
                           Interactive::exitGame);

    public static BaseAction ITEM(Item item) {
        return new BaseAction(item.getName(),
                              item.getDescription(),
                              playerCharacter -> item);
    }

    public static final BaseAction LOOK_AROUND =
            new BaseAction(Globals.messages.getString("Actions.LOOK_AROUND.name"),
                           Globals.messages.getString("Actions.LOOK_AROUND.description"),
                           GameCharacter::getLocation);

    public static final BaseAction NEW_GAME =
            new BaseAction(Globals.messages.getString("Actions.NEW_GAME.name"),
                           "",
                           gameCharacter -> Main.stockGame());

    public static BaseAction OPEN_DOOR(Door door) {
        return new BaseAction(Printers.format("Actions.OPEN_DOOR", door.getName()),
                              door.getDescription(),
                              playerCharacter -> door);
    }

    public static final BaseAction OPEN_INVENTORY =
            new BaseAction(Globals.messages.getString("Actions.OPEN_INVENTORY.name"),
                           "",
                           GameCharacter::getInventory);
}
