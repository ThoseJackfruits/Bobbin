package text_engine.boundaries;

import org.junit.Test;

import text_engine.items.Key;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link Door}s.
 */
public class DoorTest {

  Room r1 = new Room("r1");
  Room r2 = new Room("r2");
  Door d1 = new Door(r1, r2, true);

  @Test
  public void testKeyFitting() {
    int keyCount = 100;
    Key[] keys = new Key[keyCount];

    for (int i = 0; i < keyCount; i++) {
      keys[i] = d1.makeKey(String.format("Key #%d", i), "spooky key");
    }

    for (Key k : keys) {
      assertTrue(d1.fits(k));
    }
  }
}
