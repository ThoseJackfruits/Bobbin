package text_engine.items;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

  private String name;
  private String description;
  private Map<String, Item> combinations = new HashMap<>();

  public ItemBuilder() {
  }

  public ItemBuilder(String name, String description) {
    setName(name);
  }

  public ItemBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public ItemBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public ItemBuilder addCombination(String name, Item item) {
    this.combinations.put(name, item);
    return this;
  }

  public Item build() {
    List<String> missingAttributes = new ArrayList<>(2);
    if (name == null) {
      missingAttributes.add("name");
    }
    if (description == null) {
      missingAttributes.add("description");
    }

    if (missingAttributes.size() != 0) {
      throw new InvalidStateException(
        String.format("The following attributes were missing from the Item: %s",
                      missingAttributes.toString()));
    }
    return new Item(name, description, combinations);
  }
}
