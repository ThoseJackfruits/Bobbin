package text_engine.manager;

import text_engine.characters.Player;

/**
 * Created by JackDavis on 2/12/16.
 *
 * Manages the game. Oversees prompting the user for input, and communicating that to
 * instances of all necessary classes.
 */
public class GameManager {

  private Player thePlayer;

  /**
   * Constructs a {@link GameManager} object
   *
   * @param thePlayer The Player
   */
  public GameManager(Player thePlayer) {
    this.thePlayer = thePlayer;
  }

  public void prompt() {

    System.out.println(">> " + this.nextPrompt());

  }

  private String nextPrompt() {

    return "";

  }

}
