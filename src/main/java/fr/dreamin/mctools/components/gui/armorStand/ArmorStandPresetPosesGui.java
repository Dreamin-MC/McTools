package fr.dreamin.mctools.components.gui.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.armorPose.ArmorPresetPose;
import fr.dreamin.mctools.api.colors.CustomChatColor;
import fr.dreamin.mctools.api.gui.*;
import fr.dreamin.mctools.api.minecraft.Minecraft;
import fr.dreamin.mctools.api.packUtils.ItemsPreset;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import fr.dreamin.mctools.config.LangManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ArmorStandPresetPosesGui implements GuiBuilder {

  @Override
  public String name(MTPlayer mtPlayer) {
    return McTools.getCodex().isResourcePack() ? CustomChatColor.WHITE.getColorWithText((mtPlayer.getArmorStandManager().isInvisibleGui() ? " " : PictureGui.ARMOR_POSE.getName())) : mtPlayer.getMsg(LangMsg.GUI_ARMORSTAND_PRESETPOSES_TITLE, "");
  }

  @Override
  public int getLines() {
    return 4;
  }

  @Override
  public PaginationManager getPaginationManager(MTPlayer mtPlayer, Inventory inventory) {
    return new PaginationManager(ItemsPreset.arrowPrevious.getItem(), 27, ItemsPreset.arrowNext.getItem(), 35, 0, 26, PaginationType.PAGE, LangManager.getPoseStack(mtPlayer.getLang()), Arrays.asList(), false);
  }

  @Override
  public void contents(MTPlayer mtPlayer, Inventory inv, GuiItems guiItems) {
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_GUIIN, (mtPlayer.getArmorStandManager().isInvisibleGui() ? LangMsg.GENERAL_VISIBLE : LangMsg.GENERAL_INVISIBLE)), Material.NAME_TAG, (mtPlayer.getArmorStandManager().isInvisibleGui() ? 3 : 4), 31);

    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_BACK, ""), Material.NAME_TAG, 3, 28);
    guiItems.create(mtPlayer.getMsg(LangMsg.GENERAL_LEAVE, ""), Material.NAME_TAG, 4, 34);

  }

  @Override
  public void onClick(MTPlayer mtPlayer, Inventory inv, ItemStack current, int slot, ClickType action, int indexPagination) {

    if (indexPagination != -1) {

      ArmorPresetPose pose = LangManager.getArmorPresetPoseLists().get(indexPagination);

      mtPlayer.getArmorStandManager().getArmorStandSelected().forEach((armorStand -> {
        pose.setPose(armorStand);
      }));
      return;
    }

    switch (slot) {
      case 31:
        mtPlayer.getArmorStandManager().setInvisibleGui(!mtPlayer.getArmorStandManager().isInvisibleGui());
        break;
      case 28:
        McTools.getService(GuiManager.class).open(mtPlayer.getPlayer(), ArmorStandMenuGui.class);
        break;
      case 34:
        mtPlayer.getPlayer().closeInventory();
        break;
    }
  }





}
