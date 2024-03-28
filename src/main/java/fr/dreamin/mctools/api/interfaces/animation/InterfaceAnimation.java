package fr.dreamin.mctools.api.interfaces.animation;

import lombok.Getter;

public class InterfaceAnimation {

  @Getter private Object object;

  public InterfaceAnimation(InterfaceAnimationType type) {
    this.object = type;
  }

  public InterfaceAnimation(String type) {
    this.object = type;
  }

  public boolean isString() {
    return this.object instanceof String;
  }

  public String getString() {
    return (String) this.object;
  }

  public boolean isInterfaceAnimationType() {
    return this.object instanceof InterfaceAnimationType;
  }

  public InterfaceAnimationType getInterfaceAnimationType() {
    return (InterfaceAnimationType) this.object;
  }

  public boolean isGood(InterfaceAnimationType type) {
    return this.object.equals(type);
  }

  public boolean isGood(String type) {
    return this.object.equals(type);
  }


}
