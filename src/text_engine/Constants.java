package text_engine;

import text_engine.items.GameEntity;

/**
 * Global constants
 */
public class Constants {

    public Items items = new Items();

    /**
     * Would like to have something like this to hold a global, standardised list
     * of items to reduce redundancy and one-offs. Not sure what the best way to
     * do it would be. Perhaps a Map would work better.
     */
    public class Items {

        public GameEntity BLUEBERRY = new GameEntity("Blueberry", "A delicious dark berry.");
        public GameEntity BED = new GameEntity("Bed", "Something you can sleep in.");
    }
}
