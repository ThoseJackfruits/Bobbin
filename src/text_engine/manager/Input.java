package text_engine.manager;

/**
 * Represents the user's input through text. Divides input into sections to be used.
 */
public class Input {

  private String action;
  private String article;
  private String subject;

  /**
   * Constructs an {@link Input} object. Parses the string into separate words.
   *
   * Assumes the format: Action Article Subject. Example: "Take the rock" where "Take" is the action,
   * "the" is the article, and "rock" is the subject.
   *
   * @param input The input string
   */
  public Input(String input) {

    parse(input);

  }

  /**
   * Default constructor for Input.
   */
  public Input() {
    action = article = subject = "";
  }

  /**
   * Parse the string, splitting it into action, article, and subject
   *
   * @param input The string to be parsed.
   */
  public void parse(String input) {
    String[] elements = input.split("[ ]+");
    action = elements[0];
    article = elements[1];
    subject = elements[2];
  }

  /**
   * Get the action
   *
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * Get the article
   *
   * @return the article
   */
  public String getArticle() {
    return article;
  }

  /**
   * Get the subject
   *
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

}
