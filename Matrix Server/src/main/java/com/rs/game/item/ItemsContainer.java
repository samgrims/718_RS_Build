package com.rs.game.item;

import java.io.Serializable;

/**
 * Container class.
 * 
 * @author Graham / edited by Dragonkk(Alex)
 * @param <T>
 */
public final class ItemsContainer<T extends Item> implements Serializable {

	private static final long serialVersionUID = 1099313426737026107L;

	private Item[] items;
	private boolean alwaysStackable = false;

	public ItemsContainer(int size, boolean alwaysStackable) {
		items = new Item[size];
		this.alwaysStackable = alwaysStackable;
	}

	public void shift() {
		Item[] oldData = items;
		items = new Item[oldData.length];
		int ptr = 0;
		for (int i = 0; i < items.length; i++) {
			if (oldData[i] != null) {
				items[ptr++] = oldData[i];
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T get(int slot) {
		if (slot < 0 || slot >= items.length) {
			return null;
		}
		return (T) items[slot];
	}

	public void set(int slot, T item) {
		if (slot < 0 || slot >= items.length) {
			return;
		}
		items[slot] = item;
	}

	public void set2(int slot, Item item) {
		if (slot < 0 || slot >= items.length) {
			return;
		}
		items[slot] = item;
	}

	public boolean forceAdd(T item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				return true;
			}
		}
		return false;
	}

	public boolean addSOF(T item) {
		int slot = findAvailableSlot();
		if (slot == -1) {
			return false;
		}

		if(item.getDefinitions().isStackable() || item.getDefinitions().isNoted()){
			items[slot] = item;
		}
		else {
			item.setAmount(1);
			items[slot] = item;
		}
		return true;
	}


	public boolean add(T item) {
		if (alwaysStackable || item.getDefinitions().isStackable() || item.getDefinitions().isNoted()) {
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					if (items[i].getId() == item.getId()) {
						items[i] = new Item(items[i].getId(), items[i].getAmount() + item.getAmount());
						return true;
					}
				}
			}
		} else {
			if (item.getAmount() > 1) {
				if (countAvailableSlots() >= item.getAmount()) {
					for (int i = 0; i < item.getAmount(); i++) {
						int index = findAvailableSlot();
						items[index] = new Item(item.getId(), 1);
					}
					return true;
				} else {
					return false;
				}
			}
		}
		int index = findAvailableSlot();
		if (index == -1) {
			return false;
		}
		items[index] = item;
		return true;
	}

	public int countAvailableSlots() {
		int numberOfSlots = 0;
		for (Item aData : items) {
			if (aData == null) {
				numberOfSlots++;
			}
		}
		return numberOfSlots;
	}

	public int remove(T item) {
		int removed = 0, toRemove = item.getAmount();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].getId() == item.getId()) {
					int amt = items[i].getAmount();
					if (amt > toRemove) {
						removed += toRemove;
						amt -= toRemove;
						toRemove = 0;
						items[i] = new Item(items[i].getId(), amt);
						return removed;
					} else {
						removed += amt;
						toRemove -= amt;
						items[i] = null;
					}
				}
			}
		}
		return removed;
	}

	public void removeAll(T item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].getId() == item.getId()) {
					items[i] = null;
				}
			}
		}
	}

	public boolean containsOne(T item) {
		for (Item aData : items) {
			if (aData != null) {
				if (aData.getId() == item.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean contains(T item) {
		int amtOf = 0;
		for (Item aData : items) {
			if (aData != null) {
				if (aData.getId() == item.getId()) {
					amtOf += aData.getAmount();
				}
			}
		}
		return amtOf >= item.getAmount();
	}

	public int findAvailableSlot() {
		for (int slot = 0; slot < items.length; slot++) {
			if (items[slot] == null) {
				return slot;
			}
		}
		return -1;
	}

	public void clear() {
		for (int i = 0; i < items.length; i++) {
			items[i] = null;
		}
	}

	public int getSize() {
		return items.length;
	}

	public int getUsedSlots() {
		int s = 0;
		for (Item aData : items) {
			if (aData != null) {
				s++;
			}
		}
		return s;
	}

	public int getNumberOf(Item item) {
		int count = 0;
		for (Item aData : items) {
			if (aData != null) {
				if (aData.getId() == item.getId()) {
					count += aData.getAmount();
				}
			}
		}
		return count;
	}

	public int getNumberOf(int item) {
		int count = 0;
		for (Item aData : items) {
			if (aData != null) {
				if (aData.getId() == item) {
					count += aData.getAmount();
				}
			}
		}
		return count;
	}

	public Item[] getItems() {
		return items;
	}

	public Item[] getItemsCopy() {
		Item[] newData = new Item[items.length];
		System.arraycopy(items, 0, newData, 0, newData.length);
		return newData;
	}

	public ItemsContainer<Item> asItemContainer() {
		ItemsContainer<Item> c = new ItemsContainer<Item>(items.length, this.alwaysStackable);
		System.arraycopy(items, 0, c.items, 0, items.length);
		return c;
	}

	public int getFreeSlot() {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public int getThisItemSlot(T item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].getId() == item.getId()) {
					return i;
				}
			}
		}
		return getFreeSlot();
	}

	public Item lookup(int id) {
		for (Item aData : items) {
			if (aData == null) {
				continue;
			}
			if (aData.getId() == id) {
				return aData;
			}
		}
		return null;
	}

	public int lookupSlot(int id) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				continue;
			}
			if (items[i].getId() == id) {
				return i;
			}
		}
		return -1;
	}

	public void reset() {
		items = new Item[items.length];
	}

	public int remove(int preferredSlot, Item item) {
		int removed = 0, toRemove = item.getAmount();
		if (items[preferredSlot] != null) {
			if (items[preferredSlot].getId() == item.getId()) {
				int amt = items[preferredSlot].getAmount();
				if (amt > toRemove) {
					removed += toRemove;
					amt -= toRemove;
					toRemove = 0;
					// data[preferredSlot] = new
					// Item(data[preferredSlot].getDefinition().getId(), amt);
					set2(preferredSlot, new Item(items[preferredSlot].getId(),
							amt));
					return removed;
				} else {
					removed += amt;
					toRemove -= amt;
					// data[preferredSlot] = null;
					set(preferredSlot, null);
				}
			}
		}
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].getId() == item.getId()) {
					int amt = items[i].getAmount();
					if (amt > toRemove) {
						removed += toRemove;
						amt -= toRemove;
						toRemove = 0;
						// data[i] = new Item(data[i].getDefinition().getId(),
						// amt);
						set2(i, new Item(items[i].getId(), amt));
						return removed;
					} else {
						removed += amt;
						toRemove -= amt;
						// data[i] = null;
						set(i, null);
					}
				}
			}
		}
		return removed;
	}

	public void addAll(ItemsContainer<T> container) {
		for (int i = 0; i < container.getSize(); i++) {
			T item = container.get(i);
			if (item != null) {
				this.add(item);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addAll(Item[] container) {
		for (int i = 0; i < container.length; i++) {
			Item item = container[i];
			if (item != null) {
				this.add((T) item);
			}
		}
	}

	public boolean hasSpaceFor(ItemsContainer<T> container) {
		for (int i = 0; i < container.getSize(); i++) {
			T item = container.get(i);
			if (item != null) {
				if (!this.hasSpaceForItem(item)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean hasSpaceForItem(T item) {
		if (alwaysStackable || item.getDefinitions().isStackable()
				|| item.getDefinitions().isNoted()) {
			for (Item aData : items) {
				if (aData != null) {
					if (aData.getId() == item.getId()) {
						return true;
					}
				}
			}
		} else {
			if (item.getAmount() > 1) {
				return countAvailableSlots() >= item.getAmount();
			}
		}
		int index = findAvailableSlot();
		return index != -1;
	}

	public Item[] toArray() {
		return items;
	}

}
