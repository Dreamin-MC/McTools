package fr.dreamin.mctools.api;

import org.bukkit.Bukkit;

public enum Version {
  V_1_20_4(17);

  public static Version VERSION;
  public final int major;

  private Version(int major) {
    this.major = major;
  }

  public static boolean isHigherOrEqual(Version compareTo) {
    return VERSION.major >= compareTo.major;
  }

  public static String getPrettyVersion() {
    String string = getInternalVersion().substring(0, 5).replace("_", ".");
    string = string.endsWith(".") ? string.substring(0, string.length() - 1) : string;
    return string;
  }

  public static Version getVersion() {
    String string = getInternalVersion().substring(0, 5).toUpperCase();
    string = string.endsWith("_") ? string.substring(0, string.length() - 1) : string;
    VERSION = valueOf(string);
    return VERSION;
  }

  public static String getInternalVersion() {
    return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
  }
}

