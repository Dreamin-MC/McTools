package fr.dreamin.mctools.api.gui.defaultGui;

import com.rexcantor64.triton.api.language.Language;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.players.DTPlayer;
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
  public void contents(Player player, Inventory inv, GuiItems items) {

  }

  @Override
  public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType action) {

    DTPlayer dtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().containsItemInPagination(getPaginationManager(player, inv), slot)) {
      int index = McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().getIdItemInPagination(player, getPaginationManager(player, inv), slot, getClass());

      if (McTools.getInstance().getTritonManager() != null) {
        Language language = McTools.getInstance().getTritonManager().getLanguageManager().getAllLanguages().get(index);

        if (language != null && dtPlayer.getTritonManager().getLanguagePlayer().getLanguage() != language ) {
          dtPlayer.getTritonManager().getLanguagePlayer().setLang(language);
          player.sendMessage(McTools.getInstance().getTritonManager().getLanguageManager().getText(dtPlayer.getTritonManager().getLanguagePlayer(), "dreamintools.change.language") + " " + language.getRawDisplayName());
        }
      }
    }
  }
}
