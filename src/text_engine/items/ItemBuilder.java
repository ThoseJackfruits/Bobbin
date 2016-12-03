package text_engine.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import text_engine.effects.Effect;
import text_engine.items.combinations.Combination;
import text_engine.items.combinations.Combinations;

public class ItemBuilder {

    private String name;
    private String description;
    private Combinations combinations = new Combinations();
    private Stack<Effect<? extends GameEntity>> effects = new Stack<>();

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

    public ItemBuilder addCombination(Combination combination, Item item) {
        this.combinations.put(combination, item);
        return this;
    }

    public ItemBuilder addConsumption(Effect<? extends GameEntity> effect) {
        this.effects.push(effect);
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

    public Item build() {
        buildChecks();

        return new Item(name, description, combinations, effects);
    }
}
