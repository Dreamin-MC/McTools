package fr.dreamin.mctools.api.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.cuboide.Cuboide;
import fr.dreamin.mctools.components.players.MTPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Minecraft {

  private static final String PROFILE_API_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
  public static List<Material> voidMaterial = Arrays.asList(Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD, Material.IRON_BARS, Material.DARK_OAK_DOOR, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.CRIMSON_DOOR, Material.IRON_DOOR, Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.WARPED_DOOR, Material.DARK_OAK_TRAPDOOR, Material.ACACIA_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.CRIMSON_TRAPDOOR, Material.IRON_TRAPDOOR, Material.JUNGLE_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.WARPED_TRAPDOOR, Material.DARK_OAK_SIGN, Material.ACACIA_SIGN, Material.BIRCH_SIGN, Material.JUNGLE_SIGN, Material.OAK_SIGN, Material.SPRUCE_SIGN, Material.WARPED_SIGN, Material.DARK_OAK_WALL_SIGN, Material.ACACIA_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.CRIMSON_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.OAK_WALL_SIGN, Material.SPRUCE_WALL_SIGN, Material.WARPED_WALL_SIGN, Material.DARK_OAK_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.DARK_OAK_FENCE, Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.CRIMSON_FENCE, Material.JUNGLE_FENCE, Material.OAK_FENCE, Material.SPRUCE_FENCE, Material.WARPED_FENCE);
  public static List<Material> fullMaterial = Arrays.asList(Material.SCAFFOLDING);

  public static String getPlayerName(String uuid) throws Exception {
    URL url = new URL(PROFILE_API_URL + uuid);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }
    reader.close();

    String response = builder.toString();
    if (response == null || response.isEmpty()) throw new Exception("Response from server is empty.");

    JsonObject json = new JsonParser().parse(response).getAsJsonObject();
    if (!json.has("name")) throw new Exception("Invalid UUID.");

    String name = json.get("name").getAsString();
    return name;
  }
  public static void clearPlayerItem(Player player, Material material) {
    for (ItemStack item : player.getInventory().getContents().clone()) {
      if (item != null && item.getType().equals(material)) player.getInventory().remove(item);
    }
  }
  public static Boolean hasLocationInRayon(List<Location> locList, Location centerLoc, Double rayon) {
    for (Location location : locList) {
      if (centerLoc.distance(location) <= rayon) return true;
    }
    return false;
  }

  public static boolean compareItem(ItemStack itemA, ItemStack itemB) {

    return itemA != null
      && itemB != null &&
      ((itemA.getItemMeta() == null && itemB.getItemMeta() == null) || (itemA.getItemMeta() != null && itemB.getItemMeta() != null && itemA.getItemMeta().getDisplayName().equals(itemB.getItemMeta().getDisplayName())))
      && itemA.getType().equals(itemB.getType());

  }

  public static void dropInventory(Inventory inventory, Location location, boolean clearInv) {
    if (inventory.getContents().length > 0) {
      for (ItemStack item : inventory.getContents()) {
        if (item != null) location.getWorld().dropItem(location, item);
      }

      if (clearInv) inventory.clear();
    }
  }

  public static void copyInventory(Inventory invOrigin, Inventory invGoal, Location outLocation) {
    ItemStack[] items = invOrigin.getContents();

    for (ItemStack item : items) {
      if (item != null) {
        if (invGoal.firstEmpty() == -1) outLocation.getWorld().dropItemNaturally(outLocation, item);
        else invGoal.addItem(item);
      }
    }
  }

  public static void copyInventory(Inventory invOrigin, Inventory invGoal) {
    ItemStack[] items = invOrigin.getContents();

    for (ItemStack item : items) {
      if (item != null && invGoal.firstEmpty() != -1) invGoal.addItem(item);
    }
  }

  public static void startParticleWave(Player player, double durationInMicroSeconds, Boolean showAll) {
    new BukkitRunnable() {
      private double radius = 0.0;
      private double elapsedMicroSeconds = 0.0;
      private final double radiusIncrement = 1.5; // Modifier cette valeur pour ajuster la vitesse à laquelle le cercle s'agrandit.

      @Override
      public void run() {
        if (elapsedMicroSeconds >= durationInMicroSeconds) {
          this.cancel();
          return;
        }

        for (int degree = 0; degree < 360; degree += 15) {
          double angle = degree * Math.PI / 180;
          double x = radius * Math.cos(angle);
          double z = radius * Math.sin(angle);

          if (showAll) player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(new Vector(x, 0, z)), 0, new Particle.DustOptions(Color.RED, 1));
          else player.spawnParticle(Particle.REDSTONE, player.getLocation().add(new Vector(x, 0, z)), 0, new Particle.DustOptions(Color.RED, 1));
        }
        radius += radiusIncrement;
        elapsedMicroSeconds += 0.1;
      }
    }.runTaskTimer(McTools.getInstance(), 0L, 1L);
  }
  public static void startReverseParticleWave(Player player, double durationInMicroSeconds, Boolean showAll) {
    new BukkitRunnable() {
      private double radius = 7.5;
      private double elapsedMicroSeconds = 0.0;
      private final double radiusIncrement = -1.5; // Modifier cette valeur pour ajuster la vitesse à laquelle le cercle s'agrandit.

      @Override
      public void run() {
        if (elapsedMicroSeconds >= durationInMicroSeconds) {
          this.cancel();
          return;
        }

        for (int degree = 0; degree < 360; degree += 15) {
          double angle = degree * Math.PI / 180;
          double x = radius * Math.cos(angle);
          double z = radius * Math.sin(angle);

          if (showAll) player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(new Vector(x, 0, z)), 0, new Particle.DustOptions(Color.GREEN, 1));
          else player.spawnParticle(Particle.REDSTONE, player.getLocation().add(new Vector(x, 0, z)), 0, new Particle.DustOptions(Color.GREEN, 1));
        }
        radius += radiusIncrement;
        elapsedMicroSeconds += 0.1;
      }
    }.runTaskTimer(McTools.getInstance(), 0L, 1L);
  }
  public static String brouilleMessage(String message, int modulo) {
    StringBuilder resultat = new StringBuilder();

    for (int i = 0; i < message.length(); i++) {
      char lettre = message.charAt(i);

      if (i % modulo == 0) resultat.append("§k" + lettre + "§r");
      else resultat.append(lettre);
    }
    return resultat.toString();
  }
  public static Location getLocWithYawAndPitch(Location location, float yaw, float pitch) {
    Location loc = location.clone();
    loc.setYaw(yaw);
    loc.setPitch(pitch);
    return loc;
  }
  public static boolean isBlockNearPlayer(Player player, Material material, int radius) {
    Location playerLocation = player.getLocation();
    World world = playerLocation.getWorld();
    int playerX = playerLocation.getBlockX();
    int playerY = playerLocation.getBlockY();
    int playerZ = playerLocation.getBlockZ();

    for (int x = playerX - radius; x <= playerX + radius; x++) {
      for (int y = playerY - radius; y <= playerY + radius; y++) {
        for (int z = playerZ - radius; z <= playerZ + radius; z++) {
          Block block = world.getBlockAt(x, y, z);
          if (block.getType() == material) return true;
        }
      }
    }
    return false;
  }
  public static Location getLocationToGround(Location loc) {
    Location location = loc.getBlock().getLocation().clone();

    boolean isOnGround = false;
    while (!isOnGround) {
      location.add(0, -1, 0);
      Block block = location.getBlock();
      Material blockType = block.getType();

      if (blockType.isSolid()) {
        isOnGround = true;
        location.setX((double) Math.round(loc.getX() * 10000.0) / 10000.0);
        location.setZ((double) Math.round(loc.getZ() * 10000.0) / 10000.0);

        adjustLocationForSpecialBlocks(location, loc);

        location.setPitch(loc.getPitch());
        location.setYaw(loc.getYaw());

        return location;
      }
    }
    return null;
  }
  public static void teleportPlayerToGround(Player player) {
    Location location = player.getLocation().getBlock().getLocation().clone();

    boolean isOnGround = false;

    while (!isOnGround) {
      location.add(0, -1, 0);
      Block block = location.getBlock();
      Material blockType = block.getType();

      if (blockType.isSolid()) {
        isOnGround = true;
        location.setX((double) Math.round(player.getLocation().getX() * 10000.0) / 10000.0);
        location.setZ((double) Math.round(player.getLocation().getZ() * 10000.0) / 10000.0);

        adjustLocationForSpecialBlocks(location, player.getLocation());

        location.setPitch(player.getLocation().getPitch());
        location.setYaw(player.getLocation().getYaw());
        player.teleport(location);
      }
    }
  }
  private static void adjustLocationForSpecialBlocks(Location location, Location playerLocation)  {
    Block block = location.getBlock();
    Material blockType = block.getType();

    if (block.getBlockData() instanceof Slab) {
      Slab slab = (Slab) block.getBlockData();

      if (slab.getType() == Slab.Type.TOP) location.add(0, 1, 0);
      else if (slab.getType() == Slab.Type.DOUBLE) location.add(0, 1, 0);
      else location.add(0, 0.5, 0);
    }
    else if (block.getBlockData() instanceof TrapDoor) {
      TrapDoor trapDoor = (TrapDoor) block.getBlockData();
      if (trapDoor.getHalf() == Bisected.Half.TOP) location.add(0, 1, 0);
      else if (trapDoor.isOpen()) location.add(0, 1, 0);
      else location.add(0, 0.19, 0);
    }
    else if (block.getBlockData() instanceof Stairs) {
      Stairs stairs = (Stairs) block.getBlockData();

      if (stairs.getHalf() == Bisected.Half.TOP) location.add(0, 1, 0);
      else {
        double yDifference = playerLocation.getY() - location.getY();
        boolean isPlayerInAir = !playerLocation.getBlock().getRelative(0, -1, 0).getType().isSolid();
        boolean isBlockAboveSolid = block.getRelative(0, 1, 0).getType().isSolid();

        if (yDifference >= 1.0 && !isPlayerInAir) location.add(0, 1, 0);
        else if (yDifference >= 0.5 && !isPlayerInAir && !isBlockAboveSolid) location.add(0, 0.5, 0);
        else location.add(0, 1, 0);
      }
    }
    else if (blockType == Material.SNOW) {
      Snow snow = (Snow) block.getBlockData();
      int layers = snow.getLayers(); // obtient le nombre de couches
      // convertit le nombre de couches en hauteur (chaque couche mesure 1/8 bloc)
      double height = layers / 8.0;
      location.add(0, height, 0);
    }
    else if (blockType.name().contains("FENCE") || blockType.name().contains("WALL")) location.add(0, 1.5, 0);
    else location.add(0, 1, 0);
  }
  public static boolean isInBlocOrUnderSolidBloc(Location location) {
    Block blockUnder = location.getBlock();
    Block blockFeet = location.clone().add(0, -1, 0).getBlock();

    if (blockUnder.getType().isSolid() || blockFeet.getType().isSolid()) return true;
    else return false;
  }
  public static Player raycastPlayerWithParticules(Player player, double maxDistance, Particle particle, int nbrParticule, double vitesse, double v1, double v2, double v3, double particleGap, boolean particulePassedThrough) {
    Location playerLocation = player.getEyeLocation();
    Vector playerDirection = playerLocation.getDirection();

    World world = player.getWorld();
    RayTraceResult result = playerLocation.getWorld().rayTrace(playerLocation, playerDirection, maxDistance, FluidCollisionMode.NEVER, true, 0.5, entity -> entity instanceof Player && !entity.getUniqueId().equals(player.getUniqueId()));

    double distance = maxDistance;
    for (double i = 0; i < distance; i += particleGap) {
      Location particleLoc = playerLocation.clone().add(playerDirection.clone().multiply(i));
      if (!particulePassedThrough && particleLoc.getBlock().getType().isSolid()) break;
      world.spawnParticle(particle, particleLoc, nbrParticule, vitesse ,v1,v2, v3);
    }

    if (result != null) {
      Entity hitEntity = result.getHitEntity();
      if (hitEntity instanceof Player) return (Player) hitEntity;
    }
    return null;
  }
  public static Player raycastPlayer(Player player, double maxDistance) {
    Location playerLocation = player.getEyeLocation();
    Vector playerDirection = playerLocation.getDirection().normalize();

    RayTraceResult result = playerLocation.getWorld().rayTrace(playerLocation, playerDirection, maxDistance, FluidCollisionMode.NEVER, true, 0.5, entity -> entity instanceof Player && !entity.getUniqueId().equals(player.getUniqueId()));

    if (result != null) {
      if (result.getHitEntity() instanceof Player) return (Player) result.getHitEntity();
    }
    return null;
  }
  public static void clearAllPlayersEffects() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      for (PotionEffect effect : player.getActivePotionEffects()) {
        player.removePotionEffect(effect.getType());
      }
    }
  }
  public static void removeItemFromSlot(Inventory inventory, int slot, int amount) {
    ItemStack itemStack = inventory.getItem(slot);

    if (itemStack != null) {
      if (itemStack.getAmount() <= amount) inventory.clear(slot);
      else itemStack.setAmount(itemStack.getAmount() - amount);
    }
  }
  public static void removeItemFromHand(Player player, EquipmentSlot hand, int amount) {
    ItemStack itemStack = hand == EquipmentSlot.HAND ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand();

    if (itemStack != null) {
      if (itemStack.getAmount() <= amount) itemStack.setAmount(0);
      else itemStack.setAmount(itemStack.getAmount() - amount);
    }
  }
  public static Block getBlockUnderPlayer(Entity entity) {
    Location loc = entity.getLocation(); // récupère l'emplacement du joueur
    loc.subtract(0, 1, 0); // décale l'emplacement d'une unité vers le bas
    return loc.getBlock(); // récupère le bloc à l'emplacement décalé
  }
  public static Location getNearestLocation(Location playerLocation, List<Location> locations) {
    Location closestLocation = null;
    double closestDistance = Double.MAX_VALUE;

    for (Location location : locations) {
      double distance = location.distance(playerLocation);
      if (distance < closestDistance) {
        closestLocation = location;
        closestDistance = distance;
      }
    }

    return closestLocation;
  }
  public static Location findNearestEmptySpace(int y, Location location) {
    int radius = 10; // Rayon de 10 blocs
    World world = location.getWorld();
    Location nearestLocation = null;
    double minDistance = Double.MAX_VALUE;

    for (int x = -radius; x <= radius; x++) {
      for (int z = -radius; z <= radius; z++) {
        Location currentLocation = new Location(world, location.getBlockX() + x, y, location.getBlockZ() + z);
        Block lowerBlock = currentLocation.getBlock();
        Block upperBlock = currentLocation.clone().add(0, 1, 0).getBlock();
        Block blockBelow = currentLocation.clone().add(0, -1, 0).getBlock(); // Bloc sous l'espace vide

        if (lowerBlock.getType() == Material.AIR && upperBlock.getType() == Material.AIR && blockBelow.getType() != Material.AIR) {
          double distance = location.distanceSquared(currentLocation);
          if (distance < minDistance) {
            minDistance = distance;
            nearestLocation = currentLocation;
          }
        }
      }
    }

    return nearestLocation;
  }
  public static void moveVibrationParticleToPlayer(Player player, Location start, int arrivalTime) {
    Vibration vibrationData = new Vibration(start, new Vibration.Destination.EntityDestination(player), arrivalTime);
    player.spawnParticle(Particle.VIBRATION, start, 1, vibrationData);
  }
  public static void moveVibrationParticleToLocation(Player player, Location start, Location destination, int arrivalTime) {
    Vibration vibrationData = new Vibration(start, new Vibration.Destination.BlockDestination(destination), arrivalTime);
    player.spawnParticle(Particle.VIBRATION, start, 1, vibrationData);
  }
  public static String getSkinBase64(String uuid) throws Exception {
    URL url = new URL(PROFILE_API_URL + uuid);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }
    reader.close();

    String response = builder.toString();
    if (response == null || response.isEmpty()) throw new Exception("Response from server is empty.");

    JsonObject json = new JsonParser().parse(response).getAsJsonObject();

    if (json.has("properties")) {
      for (int i = 0; i < json.getAsJsonArray("properties").size(); i++) {
        JsonObject property = json.getAsJsonArray("properties").get(i).getAsJsonObject();
        if (property.has("name") && "textures".equals(property.get("name").getAsString())) return property.get("value").getAsString();
      }
    }

    throw new Exception("Cannot find 'textures' value.");
  }
  public static Location max(Location pos1, Location pos2) {
    World world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
    return new Location(world, java.lang.Math.max(pos1.getX(), pos2.getX()), java.lang.Math.max(pos1.getY(), pos2.getY()), java.lang.Math.max(pos1.getZ(), pos2.getZ()));
  }
  public static Location min(Location pos1, Location pos2) {
    World world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
    return new Location(world, java.lang.Math.min(pos1.getX(), pos2.getX()), java.lang.Math.min(pos1.getY(), pos2.getY()), java.lang.Math.min(pos1.getZ(), pos2.getZ()));
  }

  public static void spawnFirework(Location location, Color color, int power, int height, int life, boolean trail) {
    Firework firework = location.getWorld().spawn(location, Firework.class);
    FireworkMeta meta = firework.getFireworkMeta();
    List<Color> colors = new ArrayList<>();
    colors.add(color);
    meta.addEffect(FireworkEffect.builder().withColor(colors).with(FireworkEffect.Type.BALL).trail(trail).build());
    meta.setPower(power);
    firework.setFireTicks(life);
    firework.setFireworkMeta(meta);
  }

  public static Location setPitchAndYaw(Location location, float  pitchAndYaw) {
    location.setPitch(pitchAndYaw);
    location.setYaw(pitchAndYaw);

    return location;
  }

  public static void showCuboid(Player player, List<Cuboide> cuboid) {
    World world = player.getWorld();

    for (Cuboide cuboide : cuboid) {
      Location coin1 = cuboide.getLocA();
      Location coin2 = cuboide.getLocB();

      if (coin1 == null) coin1 = coin2;
      else if (coin2 == null) coin2 = coin1;

      // Récupérer les coordonnées des coins
      int x1 = Math.min(coin1.getBlockX(), coin2.getBlockX());
      int y1 = Math.min(coin1.getBlockY(), coin2.getBlockY());
      int z1 = Math.min(coin1.getBlockZ(), coin2.getBlockZ());
      int x2 = Math.max(coin1.getBlockX(), coin2.getBlockX());
      int y2 = Math.max(coin1.getBlockY(), coin2.getBlockY());
      int z2 = Math.max(coin1.getBlockZ(), coin2.getBlockZ());

      // Afficher les particules aux coins du cuboid
      for (int x = x1; x <= x2; x++) {
        for (int y = y1; y <= y2; y++) {
          for (int z = z1; z <= z2; z++) {
            if (x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2) {
              if (player.getLocation().distance(new Location(world,x, y, z)) <= 30) player.spawnParticle(Particle.VILLAGER_HAPPY, x + 0.5, y + 0.5, z + 0.5, 1, 0, 0, 0, 0);
            }
          }
        }
      }
    }
  }

  public static void showCuboid(Player player, Cuboide cuboid, Particle.DustOptions color) {
    World world = player.getWorld();


    Location coin1 = cuboid.getLocA();
    Location coin2 = cuboid.getLocB();

    if (coin1 == null) coin1 = coin2;
    else if (coin2 == null) coin2 = coin1;

    // Récupérer les coordonnées des coins
    int x1 = Math.min(coin1.getBlockX(), coin2.getBlockX());
    int y1 = Math.min(coin1.getBlockY(), coin2.getBlockY());
    int z1 = Math.min(coin1.getBlockZ(), coin2.getBlockZ());
    int x2 = Math.max(coin1.getBlockX(), coin2.getBlockX());
    int y2 = Math.max(coin1.getBlockY(), coin2.getBlockY());
    int z2 = Math.max(coin1.getBlockZ(), coin2.getBlockZ());

    // Afficher les particules aux coins du cuboid
    for (int x = x1; x <= x2; x++) {
      for (int y = y1; y <= y2; y++) {
        for (int z = z1; z <= z2; z++) {
          if (x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2) {
            Location loc = new Location(world, x, y, z);
            if (player.getLocation().distance(loc) <= 30) player.spawnParticle(Particle.REDSTONE, loc.add(0.5, 0.5, 0.5), 1, 0, 0, 0, 0, color);
          }
        }
      }
    }


  }

  public static void showCuboid(Player player, Location coin1, Location coin2) {
    World world = player.getWorld();

    if (coin1 == null) coin1 = coin2;
    else if (coin2 == null) coin2 = coin1;

    // Récupérer les coordonnées des coins
    int x1 = Math.min(coin1.getBlockX(), coin2.getBlockX());
    int y1 = Math.min(coin1.getBlockY(), coin2.getBlockY());
    int z1 = Math.min(coin1.getBlockZ(), coin2.getBlockZ());
    int x2 = Math.max(coin1.getBlockX(), coin2.getBlockX());
    int y2 = Math.max(coin1.getBlockY(), coin2.getBlockY());
    int z2 = Math.max(coin1.getBlockZ(), coin2.getBlockZ());

    // Afficher les particules aux coins du cuboid
    for (int x = x1; x <= x2; x++) {
      for (int y = y1; y <= y2; y++) {
        for (int z = z1; z <= z2; z++) {
          if (x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2) {
            if (player.getLocation().distance(new Location(world,x, y, z)) <= 50) player.spawnParticle(Particle.VILLAGER_HAPPY, x + 0.5, y + 0.5, z + 0.5, 1, 0, 0, 0, 0);
          }
        }
      }
    }
  }

  public static void showCuboid(Player player, Location coin1, Location coin2, Particle particle) {
    World world = player.getWorld();

    if (coin1 == null) coin1 = coin2;
    else if (coin2 == null) coin2 = coin1;

    // Récupérer les coordonnées des coins
    int x1 = Math.min(coin1.getBlockX(), coin2.getBlockX());
    int y1 = Math.min(coin1.getBlockY(), coin2.getBlockY());
    int z1 = Math.min(coin1.getBlockZ(), coin2.getBlockZ());
    int x2 = Math.max(coin1.getBlockX(), coin2.getBlockX());
    int y2 = Math.max(coin1.getBlockY(), coin2.getBlockY());
    int z2 = Math.max(coin1.getBlockZ(), coin2.getBlockZ());

    // Afficher les particules aux coins du cuboid
    for (int x = x1; x <= x2; x++) {
      for (int y = y1; y <= y2; y++) {
        for (int z = z1; z <= z2; z++) {
          if (x == x1 || x == x2 || y == y1 || y == y2 || z == z1 || z == z2) {
            if (player.getLocation().distance(new Location(world,x, y, z)) <= 50) player.spawnParticle(particle, x + 0.5, y + 0.5, z + 0.5, 1, 0, 0, 0, 0);
          }
        }
      }
    }
  }

  public static void setArmorStandPose(ArmorStand armorStand, double rightArmRoll, double rightArmYaw, double rightArmPitch, double leftArmRoll, double leftArmYaw, double leftArmPitch, double rightLegRoll, double rightLegYaw, double rightLegPitch, double leftLegRoll, double LeftLegYaw, double llp_yaw, double headRoll, double headYaw, double headPitch, double bodyRoll, double bodyYaw, double bodyPitch) {

    // Set general settings
    armorStand.setArms(true);
    armorStand.setBasePlate(false);
    armorStand.setGravity(false);

    // Calculate and set right arm settings
    rightArmRoll = Math.toRadians(rightArmRoll);
    rightArmYaw = Math.toRadians(rightArmYaw);
    rightArmPitch = Math.toRadians(rightArmPitch);
    EulerAngle rightArmEulerAngle = new EulerAngle(rightArmRoll, rightArmYaw, rightArmPitch);
    armorStand.setRightArmPose(rightArmEulerAngle);

    // Calculate and set left arm settings
    leftArmRoll = Math.toRadians(leftArmRoll);
    leftArmYaw = Math.toRadians(leftArmYaw);
    leftArmPitch = Math.toRadians(leftArmPitch);
    EulerAngle leftArmEulerAngle = new EulerAngle(leftArmRoll, leftArmYaw, leftArmPitch);
    armorStand.setLeftArmPose(leftArmEulerAngle);

    // Calculate and set right leg settings
    rightLegRoll = Math.toRadians(rightLegRoll);
    rightLegYaw = Math.toRadians(rightLegYaw);
    rightLegPitch = Math.toRadians(rightLegPitch);
    EulerAngle rightLegEulerAngle = new EulerAngle(rightLegRoll, rightLegYaw, rightLegPitch);
    armorStand.setRightLegPose(rightLegEulerAngle);

    // Calculate and set left leg settings
    leftLegRoll = Math.toRadians(leftLegRoll);
    LeftLegYaw = Math.toRadians(LeftLegYaw);
    llp_yaw = Math.toRadians(llp_yaw);
    EulerAngle leftLegEulerAngle = new EulerAngle(leftLegRoll, LeftLegYaw, llp_yaw);
    armorStand.setLeftLegPose(leftLegEulerAngle);

    // Calculate and set body settings
    bodyRoll = Math.toRadians(bodyRoll);
    bodyYaw = Math.toRadians(bodyYaw);
    bodyPitch = Math.toRadians(bodyPitch);
    EulerAngle bodyEulerAngle = new EulerAngle(bodyRoll, bodyYaw, bodyPitch);
    armorStand.setBodyPose(bodyEulerAngle);

    // Calculate and set head settings
    headRoll = Math.toRadians(headRoll);
    headYaw = Math.toRadians(headYaw);
    headPitch = Math.toRadians(headPitch);
    EulerAngle headEulerAngle = new EulerAngle(headRoll, headYaw, headPitch);
    armorStand.setHeadPose(headEulerAngle);

  }

  public static List<ArmorStand> getNearbyArmorStands(MTPlayer MTPlayer, double radius) {
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
