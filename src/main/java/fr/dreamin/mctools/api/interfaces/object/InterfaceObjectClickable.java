package fr.dreamin.mctools.api.interfaces.object;

import fr.dreamin.mctools.api.interfaces.InterfaceBuilder;
import fr.dreamin.mctools.api.interfaces.InterfaceInteractionType;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationBuilder;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimationType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterfaceObjectClickable extends InterfaceObject{

  @Getter @Setter private boolean isClickable = true;

  private Location location;


  @Getter private List<Interaction> interactions = new ArrayList<>();
  @Getter private List<InterfaceInteractionType> interfaceInteractionTypes = new ArrayList<>();

  public InterfaceObjectClickable(InterfaceBuilder interfaceBuilder, Location location, Display displayEntity, float yaw, float pitch, float height, float width, float depth, HashMap<InterfaceAnimationType, InterfaceAnimationBuilder> animations) {
    super(interfaceBuilder, location,displayEntity, yaw, pitch, animations);

    this.location = location;

    setInteraction(height, width, depth);
  }

  public boolean isInteraction(Interaction interaction) {
    return getInteractions().contains(interaction);
  }

  private void setInteraction(float height, float width, float depth) {

    Interaction interact = (Interaction) this.location.getWorld().spawnEntity(location, EntityType.INTERACTION);

    interact.setInteractionHeight(height);
    interact.setInteractionHeight(width);

    interactions.add(interact);
  }

}
