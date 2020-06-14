package bobbin.menus;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.ConsolePrompt;
import bobbin.interaction.ExitToException;
import bobbin.interaction.Printers;
import bobbin.interaction.console.Console;
import bobbin.items.BaseGameEntity;

public class ExitMenu extends Menu {

    public ExitMenu() {
        super("Exit", "");
    }

    @Override
    public int respondToInteraction(
            PlayerCharacter actor, BaseGameEntity from, Console console) throws ExitToException {
        Printers.println(console);
        Printers.print(console, this);
        Printers.println(console);

        boolean shouldQuit = ConsolePrompt.getChoiceBoolean(
                console, "Are you sure you want to quit?", true);

        if (shouldQuit) {
            throw new ExitToSystemException();
        } else {
            throw new MainMenu.ExitToMainMenuException();
        }
    }
}
