package fr.dreamin.mctools.config;

import fr.dreamin.mctools.api.armorPose.ArmorPresetPose;
import fr.dreamin.mctools.api.items.ItemBuilder;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LangManager {
  private File langFile;

  @Getter
  private static List<ArmorPresetPose> armorPresetPoseLists = new ArrayList<>();

  @Getter
  private YamlConfiguration langConfig;

  public LangManager(JavaPlugin plugin) {
    // Chargement du fichier lang.yml à partir des ressources
    InputStream inputStream = plugin.getResource("lang.yml");

    // Création d'une copie temporaire du fichier lang.yml
    File langFile = new File(plugin.getDataFolder(), "lang.yml");
    try {
      if (!langFile.exists()) {
        langFile.createNewFile();
        OutputStream outputStream = new FileOutputStream(langFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
          outputStream.write(buffer, 0, length);
        }
        outputStream.close();

        plugin.getLogger().info("Created lang.yml");

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Chargement de la configuration YAML depuis le fichier
    langConfig = YamlConfiguration.loadConfiguration(langFile);

//    if (langConfig.contains("gui.armorStand.presetPoses.poses")) {
//      List<Map<?, ?>> poses = langConfig.getMapList("gui.armorStand.presetPoses.poses");
//
//      // Parcourir chaque pose
//      for (Map<?, ?> pose : poses) {
//        for (Map.Entry<?, ?> entry : pose.entrySet()) {
//          String poseId = entry.getKey().toString();
//          ConfigurationSection poseData = (ConfigurationSection) entry.getValue();
//
//          System.out.println("Pose ID: " + poseId);
//
//          // Vérifier si la pose contient la clé "value"
//          if (poseData.contains("value")) {
//            ConfigurationSection valueSection = poseData.getConfigurationSection("value");
//            String langPath = "poses." + poseId + ".lang.";
//            double rightArmRoll = 0; double rightArmYaw = 0;double rightArmPitch = 0;double leftArmRoll = 0;double leftArmYaw = 0;double leftArmPitch = 0;double rightLegRoll = 0;double rightLegYaw = 0;double rightLegPitch = 0;double leftLegRoll = 0;double LeftLegYaw = 0;double leftLegPitch = 0;double headRoll = 0;double headYaw = 0;double headPitch = 0;double bodyRoll = 0;double bodyYaw = 0;double bodyPitch = 0;
//
//            // Parcourir les données de chaque pose
//            for (String key : valueSection.getKeys(false)) {
//              ConfigurationSection subSection = valueSection.getConfigurationSection(key);
//              if (subSection != null) {
//                System.out.println("Subsection: " + key);
//                // Récupérer les valeurs pour chaque partie du corps
//                rightArmRoll = subSection.getDouble("rightArm.roll", 0);
//                rightArmYaw = subSection.getDouble("rightArm.yaw", 0);
//                rightArmPitch = subSection.getDouble("rightArm.pitch", 0);
//                leftArmRoll = subSection.getDouble("leftArm.roll", 0);
//                leftArmYaw = subSection.getDouble("leftArm.yaw", 0);
//                leftArmPitch = subSection.getDouble("leftArm.pitch", 0);
//                rightLegRoll = subSection.getDouble("rightLeg.roll", 0);
//                rightLegYaw = subSection.getDouble("rightLeg.yaw", 0);
//                rightLegPitch = subSection.getDouble("rightLeg.pitch", 0);
//                leftLegRoll = subSection.getDouble("leftLeg.roll", 0);
//                LeftLegYaw = subSection.getDouble("leftLeg.yaw", 0);
//                leftLegPitch = subSection.getDouble("leftLeg.pitch", 0);
//                headRoll = subSection.getDouble("head.roll", 0);
//                headYaw = subSection.getDouble("head.yaw", 0);
//                headPitch = subSection.getDouble("head.pitch", 0);
//                bodyRoll = subSection.getDouble("body.roll", 0);
//                bodyYaw = subSection.getDouble("body.yaw", 0);
//                bodyPitch = subSection.getDouble("body.pitch", 0);
//              }
//            }
//            armorPresetPoseLists.add(new ArmorPresetPose(langPath, rightArmRoll, rightArmYaw, rightArmPitch, leftArmRoll, leftArmYaw, leftArmPitch, rightLegRoll, rightLegYaw, rightLegPitch, leftLegRoll, LeftLegYaw, leftLegPitch, headRoll, headYaw, headPitch, bodyRoll, bodyYaw, bodyPitch));
//          }
//        }
//      }
//    }

    LangMsg.setConfig(langConfig);
  }

  public static List<ItemStack> getPoseStack(Lang lang) {
    List<ItemStack> list = new ArrayList<>();

    for (ArmorPresetPose armorPresetPose: armorPresetPoseLists) {
      list.add(new ItemBuilder(Material.ARMOR_STAND).setName(armorPresetPose.getMsg(lang)).toItemStack());
    }

    return list;
  }
}