package fr.dreamin.mctools.components.commands.build;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.components.build.Tag;
import fr.dreamin.mctools.components.build.TagCategory;
import fr.dreamin.mctools.components.game.manager.BuildManager;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandMenuGui;
import fr.dreamin.mctools.components.gui.tag.TagCategoryListGui;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTag implements CommandExecutor, TabCompleter {
  private static final int MAX_TAG_LENGTH = 16;

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(Lang.en_US, ""));
      return true;
    }

    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (args.length == 0) {
      McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
    } else {
      switch (args[0]) {
        case "set":
          setTag(player, mtPlayer);
          break;
        case "remove":
          removeTag(player, mtPlayer);
          break;
        case "create":
          createTagOrCategory(player, mtPlayer, args);
          break;
        default:
          mtPlayer.sendMsg(LangMsg.ERROR_VALIDPUTVALUE, "");
          break;
      }
    }
    return false;
  }

  private void setTag(Player player, MTPlayer mtPlayer) {
    if (mtPlayer.getBuildManager().getTag() == null) {
      mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_SELECTTAG, "");
      return;
    }

    for (ArmorStand armorStandSet : mtPlayer.getArmorStandManager().getArmorStandSelected()) {
      if (!armorStandSet.getPersistentDataContainer().has(mtPlayer.getBuildManager().getTag().getNamespacedKey())) {
        armorStandSet.getPersistentDataContainer().set(mtPlayer.getBuildManager().getTag().getNamespacedKey(), PersistentDataType.STRING, mtPlayer.getBuildManager().getTag().getValue());
      }
    }
  }

  private void removeTag(Player player, MTPlayer mtPlayer) {
    if (mtPlayer.getBuildManager().getTag() == null) {
      mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_SELECTTAG, "");
      return;
    }

    for (ArmorStand armorStandRemove : mtPlayer.getArmorStandManager().getArmorStandSelected()) {
      if (armorStandRemove.getPersistentDataContainer().has(mtPlayer.getBuildManager().getTag().getNamespacedKey())) {
        armorStandRemove.getPersistentDataContainer().remove(mtPlayer.getBuildManager().getTag().getNamespacedKey());
      }
    }
  }

  private void createTagOrCategory(Player player, MTPlayer mtPlayer, String[] args) {
    if (args.length < 2) {
      mtPlayer.sendMsg(LangMsg.ERROR_VALIDPUTVALUE, "");
      return;
    }

    String subCommand = args[1];
    switch (subCommand) {
      case "tag":
        createTag(player, mtPlayer, args);
        break;
      case "category":
        createCategory(player, mtPlayer, args);
        break;
      default:
        mtPlayer.sendMsg(LangMsg.ERROR_VALIDPUTVALUE, "");
        break;
    }
  }

  private void createTag(Player player, MTPlayer mtPlayer, String[] args) {
    if (args.length < 4) {
      mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_SETTAGANDVALUE, "");
      return;
    }

    String key = args[2];
    String value = args[3];

    if (key.length() > MAX_TAG_LENGTH || value.length() > MAX_TAG_LENGTH) {
      mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_TAGCHARLIMIT, "");
      return;
    }

    if (mtPlayer.getBuildManager().getTagCategory() == null) {
      mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_TAGCATEGORY, "");
      return;
    }

    BuildManager.newTag(key, value, mtPlayer.getBuildManager().getTagCategory().getId());

    Tag tag = BuildManager.getTag(key, value);
    mtPlayer.getBuildManager().setTag(tag);

    McTools.getService(GuiManager.class).getGuiConfig().openGuiForAll(TagCategoryListGui.class);
  }

  private void createCategory(Player player, MTPlayer mtPlayer, String[] args) {
    if (args.length < 3) {
      mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_PUTCATEGORYVALUE, "");
      return;
    }

    String valueCategory = args[2];

    if (valueCategory.length() > MAX_TAG_LENGTH) {
      mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_CATEGORYCHARLIMIT, "");
      return;
    }

    BuildManager.newTagCategory(valueCategory);

    TagCategory tagCategory = BuildManager.getTagCategory(valueCategory);

    mtPlayer.getBuildManager().setTagCategory(tagCategory);

    McTools.getService(GuiManager.class).getGuiConfig().openGuiForAll(TagCategoryListGui.class);
  }


  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (args.length == 1)
      return Arrays.asList("set", "remove", "create", "createCategory");
    else if (args.length == 2) {
      switch (args[0]) {
        case "create":
          return Arrays.asList("tag", "category");
      }
    }
    return new ArrayList<>(); // null = all player names
  }

}

