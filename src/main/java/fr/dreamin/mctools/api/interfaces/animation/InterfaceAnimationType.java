package fr.dreamin.mctools.api.interfaces.animation;

import java.util.ArrayList;
import java.util.List;

public enum InterfaceAnimationType {

  CLICK,
  RAYCAST,
  IDLE;

  public static List<InterfaceAnimation> getAllAnimations() {
    List<InterfaceAnimation> list = new ArrayList<>();

    for (InterfaceAnimationType animationType : values()) {
      list.add(new InterfaceAnimation(animationType));
    }
    return list;
  }

}
