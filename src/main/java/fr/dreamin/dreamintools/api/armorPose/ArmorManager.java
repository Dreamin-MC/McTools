package fr.dreamin.dreamintools.api.armorPose;

import fr.dreamin.dreamintools.McTools;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ArmorManager {

  private static List<ArmorPose> armors = new ArrayList<>();

  public static List<ArmorPose> getArmors() {
    return armors;
  }

  public static ArmorPose getArmor(ArmorStand armorStand) {
    for (ArmorPose armorSitting : armors) {
      if (armorSitting.getArmorStand().equals(armorStand)) {
        return armorSitting;
      }
    }
    return null;
  }

  public static ArmorPose getArmor(Player player) {
    for (ArmorPose armorSitting : armors) {
      if (armorSitting.getPlayer().equals(player)) {
        return armorSitting;
      }
    }
    return null;
  }

  public static boolean haveTagSeat(ArmorStand armorStand) {
    return armorStand.getPersistentDataContainer().has(new NamespacedKey(McTools.getInstance(), "seat"), PersistentDataType.STRING);
  }

  public static boolean haveTagSleep(ArmorStand armorStand) {
    return armorStand.getPersistentDataContainer().has(new NamespacedKey(McTools.getInstance(), "sleep"), PersistentDataType.STRING);
  }

  public static void addArmor(ArmorStand armorStand, Player player, Pose pose) {

    ArmorPose armorSitting = new ArmorPose(armorStand, player, pose);

    armors.add(armorSitting);

  }

  public static void removeArmor(ArmorPose armorSitting) {
    armors.remove(armorSitting);
  }
}
