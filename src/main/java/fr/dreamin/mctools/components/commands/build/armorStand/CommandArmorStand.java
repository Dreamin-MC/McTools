package fr.dreamin.mctools.components.commands.build.armorStand;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.gui.GuiManager;
import fr.dreamin.mctools.api.player.PlayerPerm;
import fr.dreamin.mctools.api.player.manager.MessageManager;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListLockedGui;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListRadiusGui;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandListSelectedGui;
import fr.dreamin.mctools.components.gui.armorStand.ArmorStandMenuGui;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.api.service.manager.players.PlayersService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
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
      sender.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
      return true;
    }

    Bukkit.broadcastMessage("1");

    Player player = (Player) sender;

    if (player.hasPermission(PlayerPerm.BUILD.getPerm())) {

      Bukkit.broadcastMessage("2");

      MTPlayer mTPlayer = McTools.getService(PlayersService.class).getPlayer(player);

      if (mTPlayer == null) {
        MessageManager.sendError(player, McTools.getCodex().getPrefix(), "Une erreur est survenue, veuillez réeesayer.");
        return true;
      }

      if (args.length == 0) McTools.getService(GuiManager.class).open(player, ArmorStandMenuGui.class);
      else {

        Bukkit.broadcastMessage("3");

        switch (args[0]) {
          case "set":
            if (args.length <= 1) {
              MessageManager.sendError(player, McTools.getCodex().getPrefix(), "Merci de mettre une valeur");
              return false;
            }
            switch (args[1]) {

              case "helmet":
                mTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {armorStand.setHelmet(player.getItemInHand());});
                break;
              case "weapon":
                mTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {armorStand.setItem((mTPlayer.getArmorStandManager().isLeftArmPos() ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND), player.getItemInHand());});
                break;
              case "invisible":
                mTPlayer.getArmorStandManager().setInvisibleArmorStand(!mTPlayer.getArmorStandManager().isSetInvisibleArmorStand());
                break;
              case "move":
                mTPlayer.getArmorStandManager().setDistanceMoveArmorStand(Double.valueOf(args[1]));
                break;
              case "shulker":
                mTPlayer.getArmorStandManager().getArmorStandSelected().forEach(armorStand -> {
                  // Spawn the Shulker and disable its AI
                  Shulker shulkerSet = (Shulker) mTPlayer.getPlayer().getWorld().spawnEntity(armorStand.getLocation(), EntityType.SHULKER);
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
                    mTPlayer.getArmorStandManager().setArmorStandRotation(Float.valueOf(args[1]));
                    break;
                  case "arm":
                    mTPlayer.getArmorStandManager().setArmRotate(Double.valueOf(args[1]));
                    break;
                }
            }
            break;
          case "list":
            if (args.length <= 1) {
              MessageManager.sendError(player, McTools.getCodex().getPrefix(), "Merci de mettre une valeur");
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
              MessageManager.sendError(player, McTools.getCodex().getPrefix(), "Merci de mettre une valeur");
              return false;
            }
            switch (args[1]) {
              case "all":

                switch (args[2]) {
                  case "locked":
                    mTPlayer.getArmorStandManager().removeAllArmorStandLocked(true);
                    break;
                  case "selected":
                    mTPlayer.getArmorStandManager().removeAllArmorStandSelected(true);
                    break;
                }

                break;
            }
            break;
          case "get":
            if (args.length <= 1) {
              MessageManager.sendError(player, McTools.getCodex().getPrefix(), "Merci de mettre une valeur");
              return false;
            }
            switch (args[1]) {
              case "rotation":
                for (int i = 0; i < mTPlayer.getArmorStandManager().getArmorStandSelected().size(); i++) {
                  player.sendMessage("ArmorStand : "+ i +" | Yaw: " + mTPlayer.getArmorStandManager().getArmorStandSelected().get(i).getLocation().getYaw());
                }
                break;
              case "radius":
                if (args.length <= 1) {
                  MessageManager.sendError(player, McTools.getCodex().getPrefix(), "Merci de mettre une valeur");
                  return false;
                }

                mTPlayer.getArmorStandManager().getArmorStandRadius().clear();

                try {
                  int v = Integer.parseInt(args[2]);

                  mTPlayer.getArmorStandManager().getArmorStandRadius().addAll(getNearbyArmorStands(mTPlayer, v));
                  McTools.getService(GuiManager.class).open(player, ArmorStandListRadiusGui.class);

                } catch (NumberFormatException e) {
                  player.sendMessage("§cErreur : §7Veuillez entrer un nombre valide.");
                  return true;
                }
                break;
            }
            break;
          case "add":
            if (args.length <= 1) {
              MessageManager.sendError(player, McTools.getCodex().getPrefix(), "Merci de mettre une valeur");
              return false;
            }
            switch (args[1]) {
              case "lock":
                mTPlayer.getArmorStandManager().addAllArmorStandLocked(mTPlayer.getArmorStandManager().getArmorStandSelected());
                mTPlayer.getArmorStandManager().removeAllArmorStandSelected(false);
                break;
              case "shulker":
                Location location = mTPlayer.getPlayer().getLocation();

                // Arrondir les coordonnées
                long x = Math.round(location.getX());
                long y = Math.round(location.getY());
                long z = Math.round(location.getZ());

                // Créer une nouvelle location avec les coordonnées arrondies
                Location roundedLocation = new Location(location.getWorld(), x, y, z, 90,0).add(0.5, 0, 0.5);

                // Spawn the Shulker and disable its AI
                Shulker shulker = (Shulker) mTPlayer.getPlayer().getWorld().spawnEntity(roundedLocation, EntityType.SHULKER);
                shulker.setAI(false);
                shulker.setBodyYaw(90);

                ArmorStand armorStand = Objects.requireNonNull(mTPlayer.getPlayer().getWorld()).spawn(roundedLocation, ArmorStand.class);

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
    else {
      MessageManager.sendError(player, McTools.getCodex().getPrefix(), McTools.getCodex().getErrorCommand());
      return false;
    }
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    Player player = (Player) sender;

    if (player.hasPermission(PlayerPerm.BUILD.getPerm())) {

      Bukkit.broadcastMessage("test");

      switch (args.length) {
        case 1:
          return Arrays.asList("set", "list", "remove", "get", "add");
        case 2:
          switch (args[0]) {
            case "set":
              return Arrays.asList("helmet", "weapon", "rotate", "move", "shulker", "invisible");
            case "list":
              return Arrays.asList("radius", "locked", "selected");
            case "remove":
              return Arrays.asList("all");
            case "get":
              return Arrays.asList("rotation", "radius");
            case "add":
              return Arrays.asList("lock", "shulker");
          }
          break;
        case 3:
          switch (args[1]) {
            case "all":
              if (args[0] == "remove") return Arrays.asList("locked", "selected");
              break;
            case "radius":
              if (args[0] == "get") return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "20", "30", "40", "50", "100");
              break;
            case "rotate":
              if (args[0] == "set") return Arrays.asList("armorstand", "arm");
              break;
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

