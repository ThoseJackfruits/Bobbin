package bobbin.items;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

import bobbin.characters.GameCharacter;
import bobbin.constants.Actions;
import bobbin.interaction.actions.ActionList;

public class Inventory extends BaseGameEntity implements List<Item> {
    private final List<Item> items;

    public Inventory(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    public Inventory(Item... items) {
        this(Arrays.asList(items));
    }

    public boolean hasKeyThatMatches(Predicate<Item> match) {
        return stream().filter((item) -> item.getClass().equals(Key.class))
                       .anyMatch(match);
    }

    @Override
    protected ActionList actions(GameCharacter actor, BaseGameEntity from, BufferedReader reader,
                                 PrintWriter writer) {
        ActionList actions = super.actions(actor, from, reader, writer);

        for (Item item : items) {
            actions.add(Actions.ITEM(item));
        }

        return actions;
    }

    // List Methods

    public Item[] toArray() {
        return toArray(new Item[size()]);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return items.toArray(a);
    }

    @Override
    public boolean add(Item item) {
        return items.add(item);
    }

    @Override
    public boolean remove(Object o) {
        return items.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return items.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Item> c) {
        return items.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Item> c) {
        return items.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return items.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return items.retainAll(c);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return items.contains(o);
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    @Override
    public void clear() {
        items.clear();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return items.equals(o);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public Item get(int index) {
        return items.get(index);
    }

    @Override
    public Item set(int index, Item element) {
        return items.set(index, element);
    }

    @Override
    public void add(int index, Item element) {
        items.add(index, element);
    }

    @Override
    public Item remove(int index) {
        return items.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return items.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return items.lastIndexOf(o);
    }

    @Override
    public ListIterator<Item> listIterator() {
        return items.listIterator();
    }

    @Override
    public ListIterator<Item> listIterator(int index) {
        return items.listIterator(index);
    }

    @Override
    public List<Item> subList(int fromIndex, int toIndex) {
        return items.subList(fromIndex, toIndex);
    }

}
