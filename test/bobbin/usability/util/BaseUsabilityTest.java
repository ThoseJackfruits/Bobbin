package bobbin.usability.util;

import bobbin.characters.GameCharacter;
import bobbin.characters.PlayerCharacter;
import bobbin.constants.Actions;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Interactive;
import bobbin.interaction.actions.ActionList;
import bobbin.interaction.actions.BaseAction;
import bobbin.interaction.console.Console;
import bobbin.items.BaseGameEntity;
import bobbin.menus.MainMenu;
import bobbin.unit.interaction.BaseConsoleTest;

public class BaseUsabilityTest extends BaseConsoleTest {

    private class TestMainMenu extends MainMenu {
        @Override
        protected ActionList actions(GameCharacter actor, BaseGameEntity from) {
            ActionList actions = new ActionList();

            actions.add(new BaseAction(
                    Actions.NEW_GAME.getName(),
                    Actions.NEW_GAME.getDescription(),
                    playerCharacter1 -> playerCharacter));
            actions.add(Actions.EXIT_GAME);

            return actions;
        }
    }

    protected void run(Console console, PlayerCharacter playerCharacter) {
        Interactive next = new TestMainMenu();

        while (true) {
            try {
                next.interact(playerCharacter, playerCharacter, console);
            }
            catch (Interactive.ResetStackException e) {
                next = e.then;
            }
            catch (ExitToException e) {
                break;
            }
        }
    }
}
