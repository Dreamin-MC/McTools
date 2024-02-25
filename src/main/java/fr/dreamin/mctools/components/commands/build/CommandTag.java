package fr.dreamin.mctools.components.commands.build;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.player.manager.MessageManager;
import fr.dreamin.mctools.components.build.Tag;
import fr.dreamin.mctools.components.build.TagCategory;
import fr.dreamin.mctools.components.game.manager.BuildManager;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandMenuGui;
import fr.dreamin.mctools.components.gui.tag.TagCategoryListGui;
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

  private static final String PREFIX = McTools.getCodex().getPrefix();
  private static final int MAX_TAG_LENGTH = 16;

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
      return true;
    }

    Player player = (Player) sender;
    MTPlayer MTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (MTPlayer == null) {
      sendErrorMessage(player, "Une erreur est survenue.");
      return true;
    }

    if (args.length == 0) {
      McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
    } else {
      switch (args[0]) {
        case "set":
          setTag(player, MTPlayer);
          break;
        case "remove":
          removeTag(player, MTPlayer);
          break;
        case "create":
          createTagOrCategory(player, MTPlayer, args);
          break;
        default:
          sendErrorMessage(player, "Merci de mettre un argument valide");
          break;
      }
    }
    return false;
  }

  private void setTag(Player player, MTPlayer MTPlayer) {
    if (MTPlayer.getBuildManager().getTag() == null) {
      sendErrorMessage(player, "Veuillez sélectionner un tag");
      return;
    }

    for (ArmorStand armorStandSet : MTPlayer.getArmorStandManager().getArmorStandSelected()) {
      if (!armorStandSet.getPersistentDataContainer().has(MTPlayer.getBuildManager().getTag().getNamespacedKey())) {
        armorStandSet.getPersistentDataContainer().set(MTPlayer.getBuildManager().getTag().getNamespacedKey(), PersistentDataType.STRING, MTPlayer.getBuildManager().getTag().getValue());
      }
    }
  }

  private void removeTag(Player player, MTPlayer MTPlayer) {
    if (MTPlayer.getBuildManager().getTag() == null) {
      sendErrorMessage(player, "Veuillez sélectionner un tag.");
      return;
    }

    for (ArmorStand armorStandRemove : MTPlayer.getArmorStandManager().getArmorStandSelected()) {
      if (armorStandRemove.getPersistentDataContainer().has(MTPlayer.getBuildManager().getTag().getNamespacedKey())) {
        armorStandRemove.getPersistentDataContainer().remove(MTPlayer.getBuildManager().getTag().getNamespacedKey());
      }
    }
  }

  private void createTagOrCategory(Player player, MTPlayer MTPlayer, String[] args) {
    if (args.length < 2) {
      sendErrorMessage(player, "Merci de mettre un argument valide");
      return;
    }

    String subCommand = args[1];
    switch (subCommand) {
      case "tag":
        createTag(player, MTPlayer, args);
        break;
      case "category":
        createCategory(player, MTPlayer, args);
        break;
      default:
        sendErrorMessage(player, "Merci de mettre un argument valide");
        break;
    }
  }

  private void createTag(Player player, MTPlayer MTPlayer, String[] args) {
    if (args.length < 4) {
      sendErrorMessage(player, "Veuillez entrer un nom et une valeur de tag.");
      return;
    }

    String key = args[2];
    String value = args[3];

    if (key.length() > MAX_TAG_LENGTH || value.length() > MAX_TAG_LENGTH) {
      sendErrorMessage(player, "Le nom ou la valeur du tag dépasse la limite de caractères.");
      return;
    }

    if (MTPlayer.getBuildManager().getTagCategory() == null) {
      sendErrorMessage(player, "Veuillez sélectionner une catégorie de tag.");
      return;
    }

    BuildManager.newTag(key, value, MTPlayer.getBuildManager().getTagCategory().getId());

    Tag tag = BuildManager.getTag(key, value);
    MTPlayer.getBuildManager().setTag(tag);

    McTools.getService(GuiManager.class).getGuiConfig().openGuiForAll(TagCategoryListGui.class);
  }

  private void createCategory(Player player, MTPlayer MTPlayer, String[] args) {
    if (args.length < 3) {
      sendErrorMessage(player, "Veuillez entrer une valeur de catégorie.");
      return;
    }

    String valueCategory = args[2];

    if (valueCategory.length() > MAX_TAG_LENGTH) {
      sendErrorMessage(player, "La valeur de la catégorie dépasse la limite de caractères.");
      return;
    }

    BuildManager.newTagCategory(valueCategory);

    TagCategory tagCategory = BuildManager.getTagCategory(valueCategory);

    MTPlayer.getBuildManager().setTagCategory(tagCategory);

    McTools.getService(GuiManager.class).getGuiConfig().openGuiForAll(TagCategoryListGui.class);
  }

  private void sendErrorMessage(Player player, String message) {
    MessageManager.sendError(player, PREFIX, message);
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

