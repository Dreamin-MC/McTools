package fr.dreamin.api.entity.interaction;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

@Getter @Setter @NotNull
public class InteractionData {

  private Location location;
  private float height, width;

  public InteractionData(@NotNull Location location, @NotNull float height, @NotNull float width) {
    this.location = location;
    this.height = height;
    this.width = width;
  }
  public InteractionData(@NotNull InteractionData interactionBuilder) {
    this(interactionBuilder.getLocation(), interactionBuilder.getHeight(), interactionBuilder.getWidth());
  }
  public InteractionData(@NotNull Interaction interaction) {
    this(interaction.getLocation(), interaction.getInteractionHeight(), interaction.getInteractionWidth());
  }

  public Interaction summon() {
    Interaction interaction = (Interaction) this.location.getWorld().spawnEntity(location, EntityType.INTERACTION);
    interaction.setInteractionHeight(this.height);
    interaction.setInteractionWidth(this.width);
    return interaction;
  }
}

