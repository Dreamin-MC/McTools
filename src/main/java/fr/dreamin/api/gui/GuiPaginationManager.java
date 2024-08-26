package fr.dreamin.api.gui;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiPaginationManager {

  private ItemStack previous;
  private int slotPrevious;
  private ItemStack next;
  private int slotNext;
  private GuiPaginationType type;
  private List<ItemStack> items;
  private List<Integer> notAccountSlots;
  private int slotStart;
  private int slotEnd;
  private boolean sync = false;

  public GuiPaginationManager(ItemStack previous, int slotPrevious, ItemStack next, int slotNext, int slotStart, int slotEnd, GuiPaginationType type, List<ItemStack> items, List<Integer> notAccountSlots, boolean sync) {
    this.previous = previous;
    this.slotPrevious = slotPrevious;
    this.next = next;
    this.slotNext = slotNext;
    this.type = type;
    this.items = items;
    this.slotStart = slotStart;
    this.slotEnd = slotEnd;
    this.notAccountSlots = notAccountSlots;
    this.sync = sync;

  }

  public ItemStack getPrevious() {
    return previous;
  }

  public void setPrevious(ItemStack previous) {
    this.previous = previous;
  }

  public ItemStack getNext() {
    return next;
  }

  public void setNext(ItemStack next) {
    this.next = next;
  }

  public GuiPaginationType getType() {
    return type;
  }

  public void setType(GuiPaginationType type) {
    this.type = type;
  }

  public List<ItemStack> getItems() {
    return items;
  }

  public void setItems(List<ItemStack> items) {
    this.items = items;
  }

  public int getSlotStart() {
    return slotStart;
  }

  public void setSlotStart(int slotStart) {
    this.slotStart = slotStart;
  }

  public int getSlotEnd() {
    return slotEnd;
  }

  public void setSlotEnd(int slotEnd) {
    this.slotEnd = slotEnd;
  }

  public int getSlotPrevious() {
    return slotPrevious;
  }

  public void setSlotPrevious(int slotPrevious) {
    this.slotPrevious = slotPrevious;
  }

  public int getSlotNext() {
    return slotNext;
  }

  public void setSlotNext(int slotNext) {
    this.slotNext = slotNext;
  }

  public int getItemsPerPage() {
    return (slotEnd - slotStart) + 1;
  }

  public int getTotalPages() {
    return (int) java.lang.Math.ceil((double) items.size() / getItemsPerPage());
  }

  public List<Integer> getNotAccountSlots() {
    return notAccountSlots;
  }

  public void setNotAccountSlots(List<Integer> slots) {
    this.notAccountSlots = slots;
  }

  public boolean isSync() {
    return sync;
  }

  public void setSync(boolean sync) {
    this.sync = sync;
  }

}
