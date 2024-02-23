package fr.dreamin.dreamintools.api.npc;

import com.denizenscript.denizen.npc.traits.InvisibleTrait;
import com.denizenscript.denizen.npc.traits.SittingTrait;
import com.denizenscript.denizen.npc.traits.SleepingTrait;
import com.denizenscript.denizen.npc.traits.SneakingTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.Controllable;
import net.citizensnpcs.trait.FollowTrait;
import net.citizensnpcs.trait.Gravity;
import net.citizensnpcs.trait.SkinTrait;
import net.citizensnpcs.trait.waypoint.Waypoints;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;

public class NpcBuilder {

  private NPC npc;
  private NPCRegistry registry;

  public NpcBuilder(String name) {

    this.registry = CitizensAPI.getNPCRegistry();
    this.npc = this.registry.createNPC(EntityType.PLAYER, name);
  }

  public NpcBuilder(NPC npc) {
    this.npc = npc;
  }

  public NpcBuilder setLocation(Location location) {
    if (location != null) {
      this.npc.spawn(location);
    }
    return this;
  }

  public NpcBuilder setInvisible() {

    if (!npc.hasTrait(InvisibleTrait.class))
      npc.addTrait(InvisibleTrait.class);

    InvisibleTrait invisibleTrait = npc.getOrAddTrait(InvisibleTrait.class);
    invisibleTrait.setInvisible(true);

    return this;
  }

  public NpcBuilder setSpeed(float speed) {
    npc.getNavigator().getDefaultParameters().speedModifier(speed);
    return this;
  }

  public NpcBuilder setGravity(boolean gravity) {

    if (gravity) {
      if (!npc.hasTrait(Gravity.class)) {
        npc.addTrait(Gravity.class);

        // Get the Gravity trait and disable it
        Gravity gravityTrait = npc.getTrait(Gravity.class);
        gravityTrait.setEnabled(true);
      }
    }
    else {
      if (npc.hasTrait(Gravity.class)) {
        npc.removeTrait(Gravity.class);

        // Get the Gravity trait and disable it
        Gravity gravityTrait = npc.getTrait(Gravity.class);
        gravityTrait.setEnabled(false);
      }
    }
    return this;
  }

  public NpcBuilder setSkin(String playerName) {
    this.npc.getTrait(SkinTrait.class).setSkinName(playerName, true);

    return this;
  }

  public NpcBuilder setPose(Pose pose) {
    if (pose.equals(Pose.SLEEPING)) {

      SleepingTrait trait = npc.getOrAddTrait(SleepingTrait.class);
      trait.toSleep();
      if (!trait.isSleeping()) {
        npc.removeTrait(SleepingTrait.class);
      }

    }
    else if (pose.equals(Pose.SITTING)) {
      SittingTrait trait = npc.getOrAddTrait(SittingTrait.class);
      trait.sit();
      if (!trait.isSitting())
        npc.removeTrait(SittingTrait.class);
    }
    else if (pose.equals(Pose.SNEAKING)) {
      SneakingTrait sneakingTrait = npc.getOrAddTrait(SneakingTrait.class);
      sneakingTrait.sneak();
    }
    else if (pose.equals(Pose.STANDING)) {
      SneakingTrait sneakingTrait = npc.getOrAddTrait(SneakingTrait.class);
      sneakingTrait.stand();
      if (!sneakingTrait.isSneaking())
        npc.removeTrait(SneakingTrait.class);
    }


    return this;
  }

  public NpcBuilder setFollow(Entity entity) {
    FollowTrait trait = npc.getOrAddTrait(FollowTrait.class);
    trait.follow(entity);
    if (!trait.isActive()) {
      npc.removeTrait(FollowTrait.class);
    }

    return this;
  }

  public NpcBuilder setRideable(boolean ride) {
    Controllable trait = npc.getOrAddTrait(Controllable.class);
    trait.setEnabled(ride);
    if (!trait.isEnabled()) {
      npc.removeTrait(Controllable.class);
    }

    return this;
  }

  public NpcBuilder setWander() {
    Waypoints waypoints = npc.getOrAddTrait(Waypoints.class);
    waypoints.setWaypointProvider(waypoints.getCurrentProviderName().equals("wander") ? "linear" : "wander");
    return this;
  }

  public NpcBuilder addEquipement(int slot, ItemStack item) {
    Equipment trait = npc.getOrAddTrait(Equipment.class);
    trait.set(slot, item);
    return this;
  }

  public NpcBuilder setIA(boolean isIA) {
    this.npc.setUseMinecraftAI(isIA);
    return this;
  }

  public NpcBuilder setTarget(Entity entity, boolean b) {
    this.npc.getNavigator().setTarget(entity, b);
    return this;
  }

  public NpcBuilder setStraightLineTarget(Location location) {
    this.npc.getNavigator().setStraightLineTarget(location);
    return this;
  }

  public NpcBuilder setStraightLineTarget(Entity entity, boolean b) {
    this.npc.getNavigator().setStraightLineTarget(entity, b);
    return this;
  }

  public NpcBuilder setPassager(Entity entity) {
    this.npc.getEntity().setPassenger(entity);
    return this;
  }

  public NpcBuilder setInvulnerable(boolean invulnereable) {
    this.npc.getEntity().setInvulnerable(invulnereable);
    return this;
  }

  public NpcBuilder setCustomName(String customName) {
    this.npc.getEntity().setCustomName(customName);
    return this;
  }

  public NpcBuilder setRotation(Float v, Float v1) {
    this.npc.getEntity().setRotation(v, v1);
    return this;
  }

  public NpcBuilder setPathTo(Location location) {
    npc.getNavigator().setTarget(location);
    return  this;
  }

  public NpcBuilder setProtected(boolean isProtected) {
    this.npc.setProtected(isProtected);
    return this;
  }

  public NpcBuilder setEntity(EntityType entityType) {
    this.npc.setBukkitEntityType(entityType);
    return this;
  }

  public NpcBuilder setName(String name) {
    this.npc.setName(name);
    return this;
  }

  public NpcBuilder setFlyable(boolean flyable) {
    this.npc.setFlyable(flyable);
    return this;
  }

  public NPC toNPC() {
    return npc;
  }

}
