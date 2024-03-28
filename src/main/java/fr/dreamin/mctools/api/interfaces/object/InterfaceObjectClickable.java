package fr.dreamin.mctools.api.interfaces.object;

import fr.dreamin.mctools.api.interfaces.InterfaceBuilder;
import fr.dreamin.mctools.api.interfaces.InterfaceInteractionType;
import fr.dreamin.mctools.api.interfaces.animation.InterfaceAnimation;
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

  public InterfaceObjectClickable(InterfaceBuilder interfaceBuilder, Location location, float yaw, float pitch, EntityType entityType, float height, float width, float depth, HashMap<InterfaceAnimation, InterfaceAnimationBuilder> animations) {
    super(interfaceBuilder, location, yaw, pitch, entityType, animations);

    this.location = location;

    setInteraction(height, width, depth);
  }

  public boolean isInteraction(Interaction interaction) {
    return getInteractions().contains(interaction);
  }

  //hauteur, largeur, profondeur
  private void setInteraction(float height, float width, float depth) {

    float w = Math.max(width, depth);
    float h = height;

    int r = 0;

    for (int i = 0; i < r; i++) {
      Interaction interact = (Interaction) this.location.getWorld().spawnEntity(location.clone().add(0, -0.5, 0), EntityType.INTERACTION);

      interact.setInteractionHeight(h);
      interact.setInteractionHeight(w);

      interactions.add(interact);
    }


  }

}
