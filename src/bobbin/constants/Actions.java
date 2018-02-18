package bobbin.constants;

import java.io.BufferedReader;
import java.io.PrintWriter;

import bobbin.boundaries.Door;
import bobbin.characters.GameCharacter;
import bobbin.characters.NonPlayerCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Interactive;
import bobbin.interaction.Printers;
import bobbin.interaction.actions.BaseAction;
import bobbin.io.gamedata.SaveGameSerial;
import bobbin.io.settings.Settings;
import bobbin.items.BaseGameEntity;
import bobbin.items.Item;
import bobbin.main.Main;
import bobbin.menus.MainMenu;

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

    /**
     * For providing a {@link BaseGameEntity} that simply returns 1 to return to the parent object.
     */
    public static final BaseAction BACK =  // See comment on MAIN_MENU below
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.BACK.name"),
                           "",
                           playerCharacter -> new BaseGameEntity() {
                               @Override
                               public int respondToInteraction(PlayerCharacter actor,
                                                               BaseGameEntity from,
                                                               BufferedReader reader,
                                                               PrintWriter writer)
                                       throws ExitToException {
                                   return GoTo.GRANDPARENT;
                               }
                           });

    public static BaseAction BACK(BaseGameEntity from) {
        return new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.BACK.name"),
                              "",
                              playerCharacter -> from);
    }

    public static BaseAction CONSUME(Item item) {
        return new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.CONSUME.name"),
                              "",
                              playerCharacter -> item.consume());
    }

    /**
     * Only offer if {@link SaveGameSerial#hasActiveSave()} is {@code true}.
     */
    @SuppressWarnings("ConstantConditions")
    public static final BaseAction CONTINUE =
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.CONTINUE.name"),
                           "",
                           playerCharacter -> SaveGameSerial.loadActiveSave().loadData());

    public static BaseAction CONVERSE(NonPlayerCharacter npc) {
        return new BaseAction(npc.getName(),
                              npc.getDescription(),
                              playerCharacter -> npc);
    }

    public static final BaseAction EXIT_GAME =
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.EXIT_GAME.name"),
                           "",
                           Interactive::exitGame);

    public static BaseAction ITEM(Item item) {
        return new BaseAction(item.getName(),
                              item.getDescription(),
                              playerCharacter -> item);
    }

    public static final BaseAction LOOK_AROUND =
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.LOOK_AROUND.name"),
                           Settings.MESSAGES_BUNDLE.getString("Actions.LOOK_AROUND.description"),
                           GameCharacter::getLocation);

    /*
     * This is real ugly. It works, and it's not problematic from a resource perspective (it only
     * gets generated once and uses the same instance everywhere) but it's ugly. I'm open to finding
     * a better way to set this kind of thing up (things that fit into the Action framework and can
     * simply return a value or throw an exception). Unfortunately, we can't use a lambda to simplify
     * this because exceptions cannot be thrown from lambdas.
     */
    public static final BaseAction MAIN_MENU =
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("MainMenu.name"),
                           "",
                           playerCharacter -> new BaseGameEntity() {
                               @Override
                               public int respondToInteraction(PlayerCharacter actor,
                                                               BaseGameEntity from,
                                                               BufferedReader reader,
                                                               PrintWriter writer)
                                       throws ExitToException {
                                   throw new MainMenu.ExitToMainMenuException(playerCharacter);
                               }
                           });

    public static final BaseAction NEW_GAME =
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.NEW_GAME.name"),
                           "",
                           gameCharacter -> Main.buildStockGame());

    public static BaseAction OPEN_DOOR(Door door) {
        return new BaseAction(Printers.format("Actions.OPEN_DOOR", door.getName()),
                              door.getDescription(),
                              playerCharacter -> door);
    }

    public static final BaseAction OPEN_INVENTORY =
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.OPEN_INVENTORY.name"),
                           "",
                           GameCharacter::getInventory);

    public static final BaseAction PICK_UP =
            new BaseAction(Settings.MESSAGES_BUNDLE.getString("Actions.PICK_UP.name"),
                           "",
                           playerCharacter -> new BaseGameEntity() {
                               @Override
                               public int respondToInteraction(PlayerCharacter actor,
                                                               BaseGameEntity from,
                                                               BufferedReader reader,
                                                               PrintWriter writer)
                                       throws ExitToException {
                                   return GoTo.GRANDPARENT;
                               }
                           });
}
