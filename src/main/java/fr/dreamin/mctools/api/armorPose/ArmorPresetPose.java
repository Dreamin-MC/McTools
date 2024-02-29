package fr.dreamin.mctools.api.armorPose;

import fr.dreamin.mctools.api.minecraft.Minecraft;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import lombok.Getter;
import org.bukkit.entity.ArmorStand;

public class ArmorPresetPose {
  @Getter
  private final String path;
  @Getter
  private final double rightArmRoll; double rightArmYaw; double rightArmPitch; double leftArmRoll; double leftArmYaw; double leftArmPitch; double rightLegRoll; double rightLegYaw; double rightLegPitch; double leftLegRoll; double leftLegYaw; double leftLegPitch; double headRoll; double headYaw; double headPitch; double bodyRoll; double bodyYaw; double bodyPitch;

  public ArmorPresetPose(String path, double rightArmRoll, double rightArmYaw, double rightArmPitch, double leftArmRoll, double leftArmYaw, double leftArmPitch, double rightLegRoll, double rightLegYaw, double rightLegPitch, double leftLegRoll, double leftLegYaw, double leftLegPitch, double headRoll, double headYaw, double headPitch, double bodyRoll, double bodyYaw, double bodyPitch) {
    this.path = path;
    this.rightArmRoll = rightArmRoll;
    this.rightArmYaw = rightArmYaw;
    this.rightArmPitch = rightArmPitch;
    this.leftArmRoll = leftArmRoll;
    this.leftArmYaw = leftArmYaw;
    this.leftArmPitch = leftArmPitch;
    this.rightLegRoll = rightLegRoll;
    this.rightLegYaw = rightLegYaw;
    this.rightLegPitch = rightLegPitch;
    this.leftLegRoll = leftLegRoll;
    this.leftLegYaw = leftLegYaw;
    this.leftLegPitch = leftLegPitch;
    this.headRoll = headRoll;
    this.headYaw = headYaw;
    this.headPitch = headPitch;
    this.bodyRoll = bodyRoll;
    this.bodyYaw = bodyYaw;
  }

  public String getMsg(Lang lang) {
    return LangMsg.getMsg(this.path, "[color]WHITE[color]Default", lang);
  }

  public void setPose(ArmorStand armorStand) {
    Minecraft.setArmorStandPose(armorStand, rightArmRoll, rightArmYaw, rightArmPitch, leftArmRoll, leftArmYaw, leftArmPitch, rightLegRoll, rightLegYaw, rightLegPitch, leftLegRoll, leftLegYaw, leftLegPitch, headRoll, headYaw, headPitch, bodyRoll, bodyYaw, bodyPitch);
  }

}
