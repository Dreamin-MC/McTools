package fr.dreamin.dreamintools.api.math;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class Math {

  public static void randomizeArray(Object[] array) {
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--) {
      int j = random.nextInt(i + 1);
      Object temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }

  public static List<?> randomizeList(List<?> list) {
    Random random = new Random();
    for (int i = list.size() - 1; i > 0; i--) {
      int j = random.nextInt(i + 1);
      Collections.swap(list, i, j);
    }
    return null;
  }

  public static double calculerRatio(int parametre, int nbrDivisé) {
    double ratio = (double)parametre / (double)nbrDivisé;
    return java.lang.Math.round(ratio * 1000.0) / 1000.0;
  }

  public static String getDirectionalArrow(Player player, Location loc) {

    double playerX = player.getLocation().getX();
    double playerY = player.getLocation().getY();
    double playerZ = player.getLocation().getZ();

    double targetX = loc.getX();
    double targetY = loc.getY();
    double targetZ = loc.getZ();

    // Calculez l'angle entre la position actuelle du joueur et la cible
    double dx = targetX - playerX;
    double dz = targetZ - playerZ;
    double angle = java.lang.Math.atan2(dz, dx);

    // Convertir l'angle en degrés
    angle = java.lang.Math.toDegrees(angle) - 90;

    // Calculez l'angle de yaw du joueur
    double yaw = player.getLocation().getYaw();

    // Soustrayez l'angle de yaw du joueur de l'angle entre la position actuelle et la cible
    double rotation = yaw - angle;

    // Utilisez l'angle de rotation pour déterminer la direction à suivre
    rotation = (rotation + 360) % 360;

    if (rotation >= 337.5 || rotation < 22.5) {
      return "⬆"; // Flèche vers le haut
    } else if (rotation < 67.5) {
      return "⬉"; // Flèche diagonale haut droite
    } else if (rotation < 112.5) {
      return "⬅"; // Flèche vers la gauche
    } else if (rotation < 157.5) {
      return "⬋"; // Flèche diagonale bas gauche
    } else if (rotation < 202.5) {
      return "⬇"; // Flèche vers le bas
    } else if (rotation < 247.5) {
      return "⬊"; // Flèche diagonale bas droite
    } else if (rotation < 292.5) {
      return "➡"; // Flèche vers la droite
    } else {
      return "⬈"; // Flèche diagonale haut gauche
    }
  }

  public static int getRandomNumber(int min, int max) {
    return new Random().nextInt((max - min) + 1) + min;
  }

  public static String chooseElement(String[] names, Double[] values) {
    Random rand = new Random();
    double total = 0;

    for (double value : values) {
      total += value;
    }

    double randomValue = rand.nextDouble() * total;

    for (int i = 0; i < values.length; i++) {
      randomValue -= values[i];
      if (randomValue <= 0) {
        return names[i];
      }
    }

    return names[names.length - 1];
  }

  public static List<String> reverseArray(List<String> array, int id) {
    List<String> result = new ArrayList<>(array.size());
    for (int i = 0; i < array.size(); i++) {
      result.add(array.get((id + i - 1) % array.size()));
    }
    return result;
  }

  public static float calculateProgress(int min, int max) {
    // Récupération de la santé de l'ennemi
    float minFloat = min;
    float maxFloat = max;
    // calculer la progression en pourcentage
    return minFloat / maxFloat;
  }

  public static double calculatePercentage(double min, double max) {
    return min * 100 / max;
  }

  public static List<ItemStack> reorderItemsAZ(List<ItemStack> items, int id) {
    if (items == null || items.isEmpty()) {
      throw new IllegalArgumentException("Invalid list of items");
    }

    // Ajuster id pour qu'il soit toujours positif
    id = (id % items.size() + items.size()) % items.size();

    List<ItemStack> reorderedItems = new ArrayList<>();

    // Ajouter les éléments de l'indice spécifié (après ajustement) jusqu'à la fin de la liste
    for (int i = id; i < items.size(); i++) {
      reorderedItems.add(items.get(i));
    }

    // Ajouter les éléments du début de la liste jusqu'à l'indice spécifié
    for (int i = 0; i < id; i++) {
      reorderedItems.add(items.get(i));
    }

    return reorderedItems;
  }
  public static List<ItemStack> reorderItems(List<ItemStack> items, int id) {
    if (items == null || items.isEmpty() || id < 0 || id >= items.size()) {
      throw new IllegalArgumentException("Invalid list of items or id");
    }

    List<ItemStack> reorderedItems = new ArrayList<>();
    // Ajouter les éléments de l'indice spécifié jusqu'à la fin de la liste
    for (int i = id; i < items.size(); i++) {
      reorderedItems.add(items.get(i));
    }

    return reorderedItems;
  }

  public static Location roundLocation(Location location) {
    double x = location.getBlockX() + 0.5;
    double y = java.lang.Math.round(location.getY());
    double z = location.getBlockZ() + 0.5;
    float yaw = location.getYaw();

    // Determine the closest direction
    float[] yaws = {0, 45, 90, 135, 180, -45, -90, -135, -180};
    float closestYaw = yaws[0];
    float closestYawDifference = java.lang.Math.abs(closestYaw - yaw);
    for (int i = 1; i < yaws.length; i++) {
      float yawDifference = java.lang.Math.abs(yaws[i] - yaw);
      if (yawDifference < closestYawDifference) {
        closestYaw = yaws[i];
        closestYawDifference = yawDifference;
      }
    }

    return new Location(location.getWorld(), x, y, z, closestYaw, 0);
  }

  public static Vector getDirectionVector(Location start, Location destination) {
    // Obtenir les coordonnées x, y, z pour start et destination
    double startX = start.getX();
    double startY = start.getY();
    double startZ = start.getZ();

    double destX = destination.getX();
    double destY = destination.getY();
    double destZ = destination.getZ();

    // Calculer les composantes x, y, z du vecteur direction
    double directionX = destX - startX;
    double directionY = destY - startY;
    double directionZ = destZ - startZ;

    // Créer et retourner le vecteur direction
    return new Vector(directionX, directionY, directionZ).normalize();
  }

}
