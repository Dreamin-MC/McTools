package fr.dreamin.api.gui;

import fr.dreamin.api.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GuiItems {
  private Inventory inv;

  public GuiItems(Inventory inv) {
    this.inv = inv;
  }

  //Create itemStack

  public void create(ItemStack itemStack, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(itemStack);

    if (lore.length > 0) it.setLore(lore);
    inv.setItem(slot, it.toItemStack());
  }

  //Create basic faster
  public void create(String name, Material material, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setLore(lore);
    inv.setItem(slot, it.toItemStack());
  }

  //Create basic customId
  public void create(String name, Material material, int customId, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(material).setName(name).setCustomMeta(customId).setLore(lore);
    inv.setItem(slot, it.toItemStack());
  }

  //Create PlayerHead
  public void create(String name, String value, PlayerHeadMethod method, int slot, String...lore) {
    ItemBuilder it = new ItemBuilder(Material.PLAYER_HEAD).setName(name).setLore(lore);

    if (method.equals(PlayerHeadMethod.URL)) it.setPlayerHFromUrl(value);
    else if (method.equals(PlayerHeadMethod.PLAYER_NAME)) it.setPlayerHFromName(value);
    else if (method.equals(PlayerHeadMethod.PLAYER_UUID)) it.setPlayerHFromUuid(UUID.fromString(value));
    else if (method.equals(PlayerHeadMethod.BASE64)) it.setPlayerHFromBase64(value);

    inv.setItem(slot, it.toItemStack());
  }

  //Create Multi items basic
  public void createList(String name, Material material, int[] listSlot, String...lore) {
    for (int slot : listSlot) {
      create(name, material, slot, lore);
    }
  }

  //Create Multi items itemStack
  public void createList(ItemStack itemStack, int[] listSlot) {
    for (int slot : listSlot) {
      create(itemStack, slot);
    }
  }

  public enum PlayerHeadMethod {
    URL(),
    PLAYER_NAME(),
    PLAYER_UUID(),
    BASE64();

  }

}
