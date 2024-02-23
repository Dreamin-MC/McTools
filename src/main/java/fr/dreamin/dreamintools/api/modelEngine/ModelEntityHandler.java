package fr.dreamin.dreamintools.api.modelEngine;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import lombok.Getter;
import org.bukkit.entity.Entity;

public class ModelEntityHandler {

  @Getter
  private final Entity entity;
  @Getter
  private final ModeledEntity modeledEntity;
  @Getter
  private ActiveModel activeModel;
  @Getter
  private String animationPlayingName;

  public ModelEntityHandler(Entity entity, String activeModel, boolean hasHitBox) {
    this.entity = entity;
    this.modeledEntity = ModelEngineAPI.createModeledEntity(entity);

    if (activeModel != null) {
      this.activeModel = ModelEngineAPI.createActiveModel(activeModel);
      this.modeledEntity.addModel(this.activeModel, hasHitBox);
    }
  }

  public void setHitBox(boolean hasHitBox) {
    this.modeledEntity.addModel(this.activeModel, hasHitBox);
  }

  public void playModelAnimation(String animationName) {
    if (this.modeledEntity != null && this.activeModel != null) {
      AnimationHandler animHandler = this.activeModel.getAnimationHandler();
      animHandler.playAnimation(animationName, 0, 0, 1, true);
      this.animationPlayingName = animationName;
    }
  }


  /**
   *  animation - the animation ID
   * lerpIn - time taken to interpolate into the animation (in seconds)
   * lerpOut - time taken to interpolate out of the animation (in seconds)
   * speed - raw speed multiplier (1 for default speed)
   * force - if false, the animation would only play if the model is not currently playing the animation, or the animation is in LERPOUT phase
   */
  public void playModelAnimation(String animationName, double v, double v1, double v2, boolean b) {
    if (this.modeledEntity != null && this.activeModel != null) {
      AnimationHandler animHandler = this.activeModel.getAnimationHandler();
      animHandler.playAnimation(animationName, v, v1, v2, b);
      this.animationPlayingName = animationName;
    }
  }

  public void stopModelAnimation(boolean force) {
    if (this.modeledEntity != null && this.activeModel != null) {
      AnimationHandler animHandler = this.activeModel.getAnimationHandler();
      if (animHandler.isPlayingAnimation(this.animationPlayingName)) {
        if (force)
          animHandler.forceStopAnimation(this.animationPlayingName);
        else
          animHandler.stopAnimation(this.animationPlayingName);
      }
    }
  }

  public boolean isPlaying(String animation) {
    if (this.modeledEntity != null && this.activeModel != null) {
      AnimationHandler animHandler = this.activeModel.getAnimationHandler();
      return animHandler.isPlayingAnimation(animation);
    }
    return false;
  }

  public void setModel(String activeModel) {
    this.activeModel = ModelEngineAPI.createActiveModel(activeModel);
    this.modeledEntity.addModel(this.activeModel, true);
  }

  public void removeModel(String activeModel) {
    this.modeledEntity.removeModel(activeModel);
  }

}
