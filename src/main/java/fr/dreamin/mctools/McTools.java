package fr.dreamin.mctools;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public class McTools {

    @Getter private static String VERSION = "0.1.0";

    @Getter @Setter private static JavaPlugin instance;
}
