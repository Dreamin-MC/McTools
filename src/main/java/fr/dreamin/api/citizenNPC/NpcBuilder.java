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

  // Enum pour définir les types de navigation possibles pour un NPC
  public enum NavigationType {DEFAULT, STRAIGHT_LINEAR}

  @Getter
  private static final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore()); // Registre NPC

  private NPC npc;  // Le NPC en cours de création/modification par ce builder

  // Constructeurs
  public NpcBuilder(@NotNull EntityType entityType, String name, @NotNull Location location) {
    this.npc = registry.createNPC(entityType, name, location); // Création d'un NPC avec un emplacement initial
  }

  public NpcBuilder(@NotNull EntityType entityType, String name) {
    this.npc = registry.createNPC(entityType, name); // Création d'un NPC sans emplacement initial
  }

  public NpcBuilder(String name) {
    this.npc = registry.createNPC(EntityType.PLAYER, name); // Par défaut, création d'un NPC joueur
  }

  public NpcBuilder(NPC npc) {
    this.npc = npc; // Utilisation d'un NPC existant
  }

  /**
   * Spawns the NPC at the given location.
   *
   * @param location Location to spawn the NPC.
   * @return this (NpcBuilder) for chaining.
   */
  public NpcBuilder spawn(@NotNull Location location) {
    this.npc.spawn(location);
    return this;
  }

  /**
   * Teleports the NPC to the given location if it is spawned.
   *
   * @param location Location to teleport to.
   * @return this (NpcBuilder) for chaining.
   */
  public NpcBuilder teleport(@NotNull Location location) {
    if (this.npc.isSpawned()) this.npc.getEntity().teleport(location);
    return this;
  }

  /**
   * Builds and returns the NPC.
   *
   * @return The NPC instance.
   */
  public NPC build() {
    return this.npc;
  }

  // ----- Méthodes pour définir diverses propriétés du NPC -----

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

  public NpcBuilder setMinecraftIA(boolean useAI) {
    this.npc.setUseMinecraftAI(useAI);
    return this;
  }

  public NpcBuilder setCollidable(boolean collidable) {
    this.npc.data().setPersistent(NPC.Metadata.COLLIDABLE, collidable);
    return this;
  }

  public NpcBuilder setGravity(boolean hasGravity) {
    this.npc.getOrAddTrait(Gravity.class).setHasGravity(hasGravity);
    return this;
  }

  public NpcBuilder setInvulnerable(boolean invulnerable) {
    if (this.npc.isSpawned()) {
      this.npc.getEntity().setInvulnerable(invulnerable);
    }
    return this;
  }

  public NpcBuilder setProtected(boolean isProtected) {
    this.npc.setProtected(isProtected);
    return this;
  }

  public NpcBuilder setSneaking(boolean sneaking) {
    this.npc.setSneaking(sneaking);
    return this;
  }

  public NpcBuilder setWanderer(boolean wanderer) {
    Waypoints waypoints = this.npc.getOrAddTrait(Waypoints.class);
    waypoints.setWaypointProvider(wanderer ? "wander" : "linear");
    return this;
  }

  // ----- Méthodes pour la navigation et le suivi -----

  public NpcBuilder navigateTo(@NotNull Location location, @NotNull NavigationType navigationType) {
    if (this.npc.getNavigator().canNavigateTo(location)) {
      if (navigationType == NavigationType.DEFAULT) this.npc.getNavigator().setTarget(location);
      else this.npc.getNavigator().setStraightLineTarget(location);
    }
    return this;
  }

  public NpcBuilder navigateTo(@NotNull Entity entity, boolean aggressive, @NotNull NavigationType navigationType) {
    if (this.npc.getNavigator().canNavigateTo(entity.getLocation())) {
      if (navigationType == NavigationType.DEFAULT) this.npc.getNavigator().setTarget(entity, aggressive);
      else this.npc.getNavigator().setStraightLineTarget(entity, aggressive);
    }
    return this;
  }

  public NpcBuilder stopNavigation() {
    if (this.npc.getNavigator().isNavigating()) this.npc.getNavigator().cancelNavigation();
    return this;
  }

  public NpcBuilder setFollow(Entity entity) {
    FollowTrait followTrait = this.npc.getOrAddTrait(FollowTrait.class);
    followTrait.follow(entity);
    return this;
  }

  public NpcBuilder stopFollow() {
    if (this.npc.hasTrait(FollowTrait.class)) this.npc.removeTrait(FollowTrait.class);
    return this;
  }

  // ----- Méthodes pour la gestion des passagers -----

  public NpcBuilder setPassenger(Entity entity) {
    if (this.npc.isSpawned()) this.npc.getEntity().setPassenger(entity);
    return this;
  }

  public NpcBuilder addPassenger(Entity entity) {
    if (this.npc.isSpawned()) this.npc.getEntity().addPassenger(entity);
    return this;
  }

  public NpcBuilder clearPassengers() {
    if (this.npc.isSpawned()) this.npc.getEntity().getPassengers().clear();
    return this;
  }

  // ----- Méthodes pour la gestion de l'équipement du NPC -----

  public NpcBuilder setEquipment(int slot, ItemStack item) {
    Equipment equipment = this.npc.getOrAddTrait(Equipment.class);
    equipment.set(slot, item);
    return this;
  }

  public NpcBuilder setEquipment(Equipment.EquipmentSlot slot, ItemStack item) {
    Equipment equipment = this.npc.getOrAddTrait(Equipment.class);
    equipment.set(slot, item);
    return this;
  }

  // ----- Méthodes pour la gestion de la peau (skin) du NPC -----

  public NpcBuilder setSkin(String playerName) {
    this.npc.getOrAddTrait(SkinTrait.class).setSkinName(playerName);
    return this;
  }

  public NpcBuilder setSkin(String value, String signature) {
    this.npc.getOrAddTrait(SkinTrait.class).setTexture(value, signature);
    return this;
  }

  // ----- Méthodes pour les traits spéciaux (assis, couchage, montable) -----

  public NpcBuilder setRideable(boolean rideable) {
    Controllable controllable = this.npc.getOrAddTrait(Controllable.class);
    controllable.setEnabled(rideable);
    return this;
  }

  public NpcBuilder setRideable(boolean rideable, Controllable.BuiltInControls controls) {
    Controllable controllable = this.npc.getOrAddTrait(Controllable.class);
    controllable.setEnabled(rideable);
    controllable.setControls(controls);
    return this;
  }

  public NpcBuilder setSitting(@NotNull Location location) {
    SitTrait sitTrait = this.npc.getOrAddTrait(SitTrait.class);
    sitTrait.setSitting(location);
    return this;
  }

  public NpcBuilder stopSitting() {
    if (this.npc.hasTrait(SitTrait.class)) this.npc.removeTrait(SitTrait.class);
    return this;
  }

  public NpcBuilder setSleeping(@NotNull Location location) {
    SleepTrait sleepTrait = this.npc.getOrAddTrait(SleepTrait.class);
    sleepTrait.setSleeping(location);
    return this;
  }

  public NpcBuilder stopSleeping() {
    if (this.npc.hasTrait(SleepTrait.class)) this.npc.removeTrait(SleepTrait.class);
    return this;
  }

  // ----- Méthodes utilitaires -----

  /**
   * Sets the rotation of the NPC if it is spawned.
   *
   * @param yaw   Yaw value for the NPC's rotation.
   * @param pitch Pitch value for the NPC's rotation.
   * @return this (NpcBuilder) for chaining.
   */
  public NpcBuilder setRotation(float yaw, float pitch) {
    if (this.npc.isSpawned()) this.npc.getEntity().setRotation(yaw, pitch);
    return this;
  }
}