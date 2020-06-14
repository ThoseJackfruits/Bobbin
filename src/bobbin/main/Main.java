package bobbin.main;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Interactive;
import bobbin.interaction.console.Console;
import bobbin.interaction.console.SystemConsole;
import bobbin.menus.MainMenu;

public class Main {

    public static void main(String[] args) {
        start(MainMenu.dummyPlayerCharacter(), new SystemConsole());
    }

    private static void start(
            PlayerCharacter playerCharacter, Console console) {
        PlayerCharacter actor = playerCharacter;
        MainMenu mainMenu = new MainMenu();
        Interactive next = mainMenu;

        for (;;) {
            try {
                next.interact(actor, null, console);
            }
            catch (Interactive.ResetStackException e) {
                next = e.then;
                actor = e.actor;
            }
            catch (MainMenu.ExitToMainMenuException e) {
                mainMenu.saveGame(playerCharacter, console);
                next = mainMenu;
            }
            catch (Interactive.ExitToSystemException e) {
                System.out.println("Goodbye!");
                break;
            }
            catch (ExitToException e) {
                System.err.println("Unhandled ExitToException: " + e.getClass().getName());
                break;
            }
        }
    }
}
