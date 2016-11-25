package text_engine;

import text_engine.items.Item;

/**
 * Global constants
 */
public class Constants {

  /**
   * Would like to have something like this to hold a global, standardised list
   * of items to reduce redundancy and one-offs. Not sure what the best way to
   * do it would be. Perhaps a Map would work better.
   */
  public class Items {
    public Item BLUEBERRY = new Item("Blueberry", "A delicious dark berry.");
    public Item BED = new Item("Bed", "Something you can sleep in.");
  }

  public Items items = new Items();
}
