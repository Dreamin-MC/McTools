package fr.dreamin.api.citizenNPC;

import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.*;
import net.citizensnpcs.trait.waypoint.Waypoints;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NpcBuilder {

  public enum NavigationType {DEFAULT, STRAIGHT_LINEAR}

  @Getter private final static NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

  private NPC npc;

  public NpcBuilder(@NotNull EntityType entityType, String name, @NotNull Location location) {
    this.npc = registry.createNPC(entityType, name, location);
  }

  public NpcBuilder(@NotNull EntityType entityType, String name) {
    this.npc = registry.createNPC(entityType, name);
  }

  public NpcBuilder(String name) {
    this.npc = registry.createNPC(EntityType.PLAYER, name);
  }

  public NpcBuilder(NPC npc) {
    this.npc = npc;
  }


  public NpcBuilder spawn(@NotNull Location location) {
    this.npc.spawn(location);
    return this;
  }

  public NpcBuilder teleport(Location location) {
    if (this.npc.isSpawned()) this.npc.getEntity().teleport(location);
    return this;
  }

  public NPC build() {
    return this.npc;
  }


  public NpcBuilder setName(String name) {
    this.npc.setName(name);
    return this;
  }

  public NpcBuilder setEntityType(@NotNull EntityType entityType) {
    this.npc.setBukkitEntityType(entityType);
    return this;
  }

  public NpcBuilder setNameplateVisible(boolean visible) {
    this.npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, visible);
    return this;
  }

  public NpcBuilder setFlyable(boolean flyable) {
    this.npc.setFlyable(flyable);
    return this;
  }

  public NpcBuilder setMinecraftIA(boolean isIA) {
    this.npc.setUseMinecraftAI(isIA);
    return this;
  }

  public NpcBuilder setCollidable(boolean b) {
    this.npc.data().setPersistent(NPC.Metadata.COLLIDABLE, b);
    return this;
  }

  public NpcBuilder setGravity(boolean b) {
    this.npc.getOrAddTrait(Gravity.class).setHasGravity(b);
    return this;
  }

  public NpcBuilder setInvulnerable(boolean b) {
    if (this.npc.isSpawned()) this.npc.getEntity().setInvulnerable(b);
    return this;
  }

  public NpcBuilder setProtected(boolean isProtected) {
    this.npc.setProtected(isProtected);
    return this;
  }

  public NpcBuilder setSneaking(boolean b) {
    this.npc.setSneaking(b);
    return this;
  }

  public NpcBuilder setWanderer(boolean b) {
    Waypoints waypoints = npc.getOrAddTrait(Waypoints.class);
    waypoints.setWaypointProvider(b ? "wander" : "linear");
    return this;
  }

  public NpcBuilder navigateTo(@NotNull Location location, @NotNull NavigationType navigationType) {
    if (this.npc.getNavigator().canNavigateTo(location)) {
      if (navigationType.equals(NavigationType.DEFAULT)) this.npc.getNavigator().setTarget(location);
      else this.npc.getNavigator().setStraightLineTarget(location);
    }
    return  this;
  }
  public NpcBuilder navigateTo(@NotNull Entity entity, boolean b, @NotNull NavigationType navigationType) {
    if (this.npc.getNavigator().canNavigateTo(entity.getLocation())) {
      if (navigationType.equals(NavigationType.DEFAULT)) this.npc.getNavigator().setTarget(entity, b);
      else this.npc.getNavigator().setStraightLineTarget(entity, b);
    }
    return  this;
  }
  public NpcBuilder stopNavigate() {
    if (this.npc.getNavigator().isNavigating()) this.npc.getNavigator().cancelNavigation();
    return  this;
  }

  public NpcBuilder setFollow(Entity entity) {
    FollowTrait trait = npc.getOrAddTrait(FollowTrait.class);
    trait.follow(entity);
    return this;
  }
  public NpcBuilder stopFollow() {
    if (this.npc.hasTrait(FollowTrait.class)) this.npc.removeTrait(FollowTrait.class);
    return this;
  }

  public NpcBuilder setPassager(Entity entity) {
    if (this.npc.isSpawned()) this.npc.getEntity().setPassenger(entity);
    return this;
  }
  public NpcBuilder addPassager(Entity entity) {
    if (this.npc.isSpawned()) this.npc.getEntity().addPassenger(entity);
    return this;
  }
  public NpcBuilder clearPassager() {
    if (this.npc.isSpawned()) this.npc.getEntity().getPassengers().clear();
    return this;
  }

  public NpcBuilder setRotation(Float yaw, Float pitch) {
    if (this.npc.isSpawned()) this.npc.getEntity().setRotation(yaw, pitch);
    return this;
  }

  public NpcBuilder setStuff(int slot, ItemStack item) {
    Equipment trait = npc.getOrAddTrait(Equipment.class);
    trait.set(slot, item);
    return this;
  }
  public NpcBuilder setStuff(Equipment.EquipmentSlot equipmentSlot, ItemStack item) {
    Equipment trait = npc.getOrAddTrait(Equipment.class);
    trait.set(equipmentSlot, item);
    return this;
  }

  public NpcBuilder setSkin(String playerName) {
    this.npc.getOrAddTrait(SkinTrait.class).setSkinName(playerName);
    return this;
  }
  public NpcBuilder setSkin(String value, String signature) {
    this.npc.getOrAddTrait(SkinTrait.class).setTexture(value, signature);
    return this;
  }

  public NpcBuilder setRideable(boolean ride) {
    Controllable trait = npc.getOrAddTrait(Controllable.class);
    trait.setEnabled(ride);
    return this;
  }
  public NpcBuilder setRideable(boolean ride, Controllable.BuiltInControls builtInControls) {
    Controllable trait = npc.getOrAddTrait(Controllable.class);
    trait.setEnabled(ride);
    trait.setControls(builtInControls);
    return this;
  }

  public NpcBuilder setSitting(Location location) {
    SitTrait sittingTrait = this.npc.getOrAddTrait(SitTrait.class);
    sittingTrait.setSitting(location);
    return this;
  }
  public NpcBuilder stopSitting() {
    if (this.npc.hasTrait(SitTrait.class)) this.npc.removeTrait(SitTrait.class);
    return this;
  }

  public NpcBuilder setSleeping(Location location) {
    SleepTrait sleepTrait = this.npc.getOrAddTrait(SleepTrait.class);
    sleepTrait.setSleeping(location);
    return this;
  }
  public NpcBuilder stopSleeping() {
    if (this.npc.hasTrait(SleepTrait.class)) this.npc.removeTrait(SleepTrait.class);
    return this;
  }

}
