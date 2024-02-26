package fr.dreamin.mctools.api.gui.defaultGui;

import com.rexcantor64.triton.api.language.Language;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class LanguageConfifGui implements GuiBuilder {

  @Override
  public String name(Player player) {
    return "§6Configuration de la langue";
  }

  @Override
  public int getLines() {
    return 5;
  }

  @Override
  public PaginationManager getPaginationManager(Player player, Inventory inv) {
    return new PaginationManager(new ItemBuilder(Material.ARROW).toItemStack(), 28, new ItemBuilder(Material.ARROW).toItemStack(), 34, 21, 23, PaginationType.LOOP_LINE, McTools.getInstance().getTritonManager().getAllItemsLanguage(), new ArrayList<>(), false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    if (McTools.getInstance().getTritonManager() != null) {
      Language language = McTools.getInstance().getTritonManager().getLanguageManager().getAllLanguages().get(indexPagination);

      if (language != null && mtPlayer.getTritonManager().getLanguagePlayer().getLanguage() != language ) {
        mtPlayer.getTritonManager().getLanguagePlayer().setLang(language);
        mtPlayer.getPlayer().sendMessage(McTools.getInstance().getTritonManager().getLanguageManager().getText(mtPlayer.getTritonManager().getLanguagePlayer(), "dreamintools.change.language") + " " + language.getRawDisplayName());
      }
    }
  }
}
