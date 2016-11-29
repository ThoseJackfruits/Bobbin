package text_engine.items.combinable;

import java.util.ArrayList;
import java.util.List;

import text_engine.items.Item;

public class CombinableItemBuilder {

    private String name;
    private String description;
    private Combinations combinations = new Combinations();

    public CombinableItemBuilder() {
    }

    public CombinableItemBuilder(String name, String description) {
        setName(name);
    }

    public CombinableItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CombinableItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CombinableItemBuilder addCombination(Combination combination, Item item) {
        this.combinations.put(combination, item);
        return this;
    }

    private void buildChecks() {
        List<String> missingAttributes = new ArrayList<>(3);
        if (name == null) {
            missingAttributes.add("name");
        }
        if (description == null) {
            missingAttributes.add("description");
        }

        if (combinations.size() == 0) {
            missingAttributes.add("combinations");
        }

        if (missingAttributes.size() != 0) {
            throw new IllegalStateException(
                    String.format("The following attributes were missing from the Item: %s",
                                  missingAttributes.toString()));
        }
    }

    public CombinableItem build() {
        buildChecks();

        return new CombinableItem(name, description, combinations);
    }
}
