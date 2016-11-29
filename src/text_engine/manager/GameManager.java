package text_engine.manager;

import java.util.ArrayList;
import java.util.Scanner;

import text_engine.characters.GameCharacter;

/**
 * Manages the game. Oversees prompting the user for input, and communicating that to instances of all
 * necessary classes.
 */
public class GameManager {

    private static ArrayList<String> actionList;
    private static ArrayList<String> articleList;
    private GameCharacter gameCharacter;
    private Scanner myScan;
    private Input input;

    /**
     * Constructs a {@link GameManager} object
     *
     * @param gameCharacter The {@link GameCharacter}
     */
    public GameManager(GameCharacter gameCharacter) {
        this.gameCharacter = gameCharacter;
        this.input = new Input();
    }

    /**
     * Get input from the player.
     */
    public void prompt() {

        System.out.printf(">> ");

        this.input.parse(myScan.nextLine());

    }

    /**
     * Perform the specified action if the input is valid
     */
    public void parseInput() {

        if (actionList.contains(input.getAction()) && articleList.contains(input.getArticle())) {
            // Do something
        }

    }

}
