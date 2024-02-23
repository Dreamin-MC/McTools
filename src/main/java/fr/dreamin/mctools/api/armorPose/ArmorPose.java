package fr.dreamin.mctools.api.armorPose;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.npc.NpcBuilder;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class ArmorPose {

    private ArmorStand armorStand;
    private NPC npc;
    private ArmorStand seat;
    private Player player;
    private BukkitTask task;
    private Pose pose;

    public ArmorPose(ArmorStand armorStand, Player player, Pose pose) {
      this.player = player;
      this.armorStand = armorStand;
      this.pose = pose;

      if (pose.equals(Pose.SITTING)) {
        this.seat = armorStand.getWorld().spawn(armorStand.getLocation().clone().add(0, -1.25, 0), ArmorStand.class);
        this.seat.setInvisible(true);
        this.seat.setGravity(false);
        this.seat.setPassenger(player);
        this.player.getEyeLocation().setDirection(armorStand.getLocation().getDirection());

        this.task = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), () -> {
          if (seat.getPassenger() == null) {
            remove();
            task.cancel();
          }
        }, 0, 20);
      }
      else if (pose.equals(Pose.SLEEPING)) {

        // Obtenez la location actuelle de l'ArmorStand
        Location location = armorStand.getLocation().clone();
        // Obtenez la direction de l'ArmorStand
        Vector direction = location.getDirection().clone();
        // Multipliez la direction par -2 pour reculer de 2 blocs
        Vector recul = direction.multiply(2);
        // Ajoutez le résultat à la location actuelle pour obtenir la nouvelle location
        Location nouvelleLocation = location.add(recul);


        npc = new NpcBuilder("").setSkin(player.getName()).setGravity(false).setLocation(nouvelleLocation.add(0, 0, 0)).toNPC();

        Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {
          // Obtenez la localisation actuelle du NPC
          Location npcLocation = npc.getEntity().getLocation();
          // Obtenez le vecteur de rotation actuel du NPC
          Vector currentRotation = npc.getStoredLocation().getDirection();
          // Ajoutez une rotation de 90 degrés au vecteur de rotation actuel (sur l'axe Y)
          Vector newRotation = currentRotation.clone().rotateAroundY(Math.toRadians(-245));
          // Mettez à jour la rotation du NPC
          npc.faceLocation(npcLocation.clone().add(newRotation));
        }, 2);

        Bukkit.getScheduler().runTaskLater(McTools.getInstance(), () -> {

          npc = new NpcBuilder(npc).setPose(Pose.SLEEPING).toNPC();

        }, 5);

        this.seat = armorStand.getWorld().spawn(armorStand.getLocation().clone().add(0, -2.5, 0), ArmorStand.class);
        this.seat.setInvisible(true);
        this.seat.setGravity(false);
        this.seat.setPassenger(player);

        for (Player player1 : Bukkit.getOnlinePlayers()) {
          if (player1.equals(player)) continue;
          player1.hidePlayer(McTools.getInstance(), player);
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, false));

        this.task = Bukkit.getScheduler().runTaskTimer(McTools.getInstance(), () -> {
          if (seat.getPassenger() == null) {
            remove();
            task.cancel();
          }
        }, 0, 20);
      }

    }

    public Player getPlayer() {
      return player;
    }

    public ArmorStand getArmorStand() {
      return armorStand;
    }

    public ArmorStand getSeat() {
      return seat;
    }

    public void remove() {
      
      if (pose.equals(Pose.SLEEPING)) {
        npc.despawn();
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.teleport(player.getLocation().add(0, 3, 0));

        for (Player player1 : Bukkit.getOnlinePlayers()) {
          if (player1.equals(player)) continue;
          player1.showPlayer(McTools.getInstance(), player);
        }

      }
      else if (pose.equals(Pose.SITTING)) {
        seat.remove();
      }
      ArmorManager.removeArmor(this);
    }

  }