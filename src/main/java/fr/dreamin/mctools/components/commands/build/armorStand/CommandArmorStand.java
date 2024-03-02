package fr.dreamin.mctools.components.commands.build.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.api.player.manager.MessageManager;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListLockedGui;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListRadiusGui;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListSelectedGui;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandMenuGui;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandArmorStand implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (!(sender instanceof Player)) {
      sender.sendMessage(LangMsg.ERROR_CONSOLE.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    Player player = (Player) sender;
    MTPlayer mtPlayer = McTools.getService(PlayersService.class).getPlayer(player);

    if (mtPlayer == null) {
      player.sendMessage(LangMsg.ERROR_OCCURRED.getMsg(McTools.getCodex().getDefaultLang(), ""));
      return true;
    }

    if (player.hasPermission(PlayerPerm.BUILD.getPerm())) {

      if (args.length == 0) McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
      else {

        switch (args[0]) {
          case "set":
            if (args.length <= 1) {
              mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
              return false;
            }
            switch (args[1]) {

              case "helmet":
                mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {armorStand.setHelmet(player.getItemInHand());});
                break;
              case "weapon":
                mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {armorStand.setItem((mtPlayer.getArmorStandManager().isLeftArmPos() ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND), player.getItemInHand());});
                break;
              case "invisible":
                mtPlayer.getArmorStandManager().setInvisibleArmorStand(!mtPlayer.getArmorStandManager().isSetInvisibleArmorStand());
                break;
              case "move":
                mtPlayer.getArmorStandManager().setDistanceMoveArmorStand(Double.valueOf(args[1]));
                break;
              case "shulker":
                mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
                  // Spawn the Shulker and disable its AI
                  Shulker shulkerSet = (Shulker) mtPlayer.getPlayer().getWorld().spawnEntity(armorStand.getLocation(), EntityType.SHULKER);
                  shulkerSet.setAI(false);

                  shulkerSet.setBodyYaw(90);

                  // Rendre le Shulker invisible
                  shulkerSet.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));

                  shulkerSet.lookAt(armorStand.getLocation());

                  armorStand.addPassenger(shulkerSet);
                });
                break;
              case "rotate":
                switch (args[2]) {
                  case "armorstand":
                    mtPlayer.getArmorStandManager().setArmorStandRotation(Float.valueOf(args[1]));
                    break;
                  case "arm":
                    mtPlayer.getArmorStandManager().setArmRotate(Double.valueOf(args[1]));
                    break;
                }
                break;
              case "tag":
                if (mtPlayer.getBuildManager().getTag() == null) {
                  mtPlayer.sendMsg(LangMsg.CMD_TAG_ERROR_SELECTTAG, "");
                  return true;
                }
                mtPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {if (!armorStand.getPersistentDataContainer().has(mtPlayer.getBuildManager().getTag().getNamespacedKey())) armorStand.getPersistentDataContainer().set(mtPlayer.getBuildManager().getTag().getNamespacedKey(), PersistentDataType.STRING, mtPlayer.getBuildManager().getTag().getValue());});
                break;
            }
            break;
          case "list":
            if (args.length <= 1) {
              mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
              return false;
            }
            switch (args[1]) {
              case "radius":
                McTools.getService(GuiManager.class).open(player, ArmorStandListRadiusGui.class);
                break;
              case "locked":
                McTools.getService(GuiManager.class).open(player, ArmorStandListLockedGui.class);
                break;
              case "selected":
                McTools.getService(GuiManager.class).open(player, ArmorStandListSelectedGui.class);
                break;
            }
            break;
          case "remove":
            if (args.length <= 1) {
              mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
              return false;
            }
            switch (args[1]) {
              case "all":

                switch (args[2]) {
                  case "locked":
                    mtPlayer.getArmorStandManager().removeAllArmorStandLocked(true);
                    break;
                  case "selected":
                    mtPlayer.getArmorStandManager().removeAllArmorStandSelected(true);
                    break;
                }

                break;
            }
            break;
          case "get":
            if (args.length <= 1) {
              mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
              return false;
            }
            switch (args[1]) {
              case "rotation":
                for (int i = 0; i < mtPlayer.getArmorStandManager().getArmorStandSelected().size(); i++) {
                  player.sendMessage("ArmorStand : "+ i +" | Yaw: " + mtPlayer.getArmorStandManager().getArmorStandSelected().get(i).getLocation().getYaw());
                }
                break;
              case "radius":

                mtPlayer.getArmorStandManager().getArmorStandRadius().clear();

                if (args.length <= 2) {
                  mtPlayer.getArmorStandManager().getArmorStandRadius().addAll(getNearbyArmorStands(mtPlayer, 1));
                  McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removeGuiPage(player, ArmorStandListRadiusGui.class.getSimpleName());
                  McTools.getService(GuiManager.class).open(player, ArmorStandListRadiusGui.class);
                }
                else {
                  try {
                    int v = Integer.parseInt(args[2]);

                    mtPlayer.getArmorStandManager().getArmorStandRadius().addAll(getNearbyArmorStands(mtPlayer, v));
                    McTools.getService(GuiManager.class).getGuiConfig().getGuiPageManager().removeGuiPage(player, ArmorStandListRadiusGui.class.getSimpleName());
                    McTools.getService(GuiManager.class).open(player, ArmorStandListRadiusGui.class);

                  } catch (NumberFormatException e) {
                    mtPlayer.sendMsg(LangMsg.ERROR_VALIDNUMBER, "");
                    return false;
                  }
                }
                break;
            }
            break;
          case "add":
            if (args.length <= 1) {
              mtPlayer.sendMsg(LangMsg.ERROR_PUTVALUE, "");
              return false;
            }
            switch (args[1]) {
              case "lock":
                mtPlayer.getArmorStandManager().addAllArmorStandLocked(mtPlayer.getArmorStandManager().getArmorStandSelected());
                mtPlayer.getArmorStandManager().removeAllArmorStandSelected(false);
                break;
              case "shulker":
                Location location = mtPlayer.getPlayer().getLocation();

                // Arrondir les coordonnées
                long x = Math.round(location.getX());
                long y = Math.round(location.getY());
                long z = Math.round(location.getZ());

                // Créer une nouvelle location avec les coordonnées arrondies
                Location roundedLocation = new Location(location.getWorld(), x, y, z, 90,0).add(0.5, 0, 0.5);

                // Spawn the Shulker and disable its AI
                Shulker shulker = (Shulker) mtPlayer.getPlayer().getWorld().spawnEntity(roundedLocation, EntityType.SHULKER);
                shulker.setAI(false);
                shulker.setBodyYaw(90);

                ArmorStand armorStand = Objects.requireNonNull(mtPlayer.getPlayer().getWorld()).spawn(roundedLocation, ArmorStand.class);

                // Rendre le Shulker invisible
                shulker.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
                shulker.lookAt(roundedLocation);
                armorStand.addPassenger(shulker);
                break;
            }
            break;
        }
      }

      return false;
    }
    return true;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    Player player = (Player) sender;

    if (player.hasPermission(PlayerPerm.BUILD.getPerm())) {

      switch (args.length) {
        case 1: return Arrays.asList("set", "list", "remove", "get", "add");
        case 2:
          switch (args[0]) {
            case "set": return Arrays.asList("helmet", "weapon", "rotate", "move", "shulker", "invisible", "tag");
            case "list": return Arrays.asList("radius", "locked", "selected");
            case "remove": return Arrays.asList("all");
            case "get": return Arrays.asList("rotation", "radius");
            case "add": return Arrays.asList("lock", "shulker");
          }
          break;
        case 3:
          switch (args[1]) {
            case "all": return Arrays.asList("locked", "selected");
            case "radius": return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "20", "30", "40", "50", "100");
            case "rotate": return Arrays.asList("armorstand", "arm");
          }
          break;
      }
    }
    else return Arrays.asList("Error");


    return new ArrayList<>();
  }

  private List<ArmorStand> getNearbyArmorStands(MTPlayer MTPlayer, double radius) {
    // Récupère toutes les entités dans le rayon spécifié autour du joueur
    List<Entity> nearbyEntities = MTPlayer.getPlayer().getNearbyEntities(radius, radius, radius);

    List<ArmorStand> armorStands = new ArrayList<>(); // Créer une liste d'armor stand

    // Parcourt chaque entité pour vérifier si c'est un armor stand
    for (Entity entity : nearbyEntities) {
      if (entity instanceof ArmorStand) {

        ArmorStand armorStand = (ArmorStand) entity; // Cast l'entité en armor stand

        if (MTPlayer.getArmorStandManager().getIfArmorStandSelected(armorStand) == null) armorStands.add((ArmorStand) entity); // Ajoute l'armor stand à la liste

      }
    }

    return armorStands; // Retourne la liste d'armor stand
  }

}

