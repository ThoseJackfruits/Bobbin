package bobbin.interaction;

import java.io.BufferedReader;
import java.io.PrintWriter;

import bobbin.characters.PlayerCharacter;
import bobbin.interaction.actions.Action;
import bobbin.items.BaseGameEntity;

public class Confirmation extends Interactive {

    private final Action action;
    private final String prompt;
    private final Boolean defaultChoice;

    /**
     * Create and show a confirmation to the user with the given prompt. Will run the given {@link
     * Action} if the player answers in the positive, or return to the given {@link Interactive} if
     * the player answers in the negative.
     *
     * @param action        {@link Action} to run if the player responds in the positive
     * @param prompt        to present to the player when asking for a response
     * @param defaultChoice the default response of the player, if the player submits empty input. If
     *                      {@code null}, an explicit response is required.
     */
    public Confirmation(Action action, String prompt, Boolean defaultChoice) {
        this.action = action;
        this.prompt = prompt;
        this.defaultChoice = defaultChoice;
    }

    @Override
    protected int respondToInteraction(PlayerCharacter actor, BaseGameEntity from, BufferedReader reader,
                                       PrintWriter writer) throws ExitToException {
        if (ConsolePrompt.getChoiceBoolean(reader, writer, prompt, defaultChoice)) {
            action.apply(actor);
        }

        return GoTo.PARENT;
    }
}
