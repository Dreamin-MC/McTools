package fr.dreamin.mctools.component.cmd;

import fr.dreamin.mctools.McTools;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

import java.util.List;

public class SimpleCommand {

  public static void createCommand(String name, CommandExecutor executor) {
    McTools.getInstance().getCommand(name).setExecutor(executor);
  }

  public static void createCommand(String name, CommandExecutor executor, String... aliases) {
    PluginCommand cmd = McTools.getInstance().getCommand(name);
    cmd.setAliases(List.of(aliases));
    cmd.setExecutor(executor);
  }
}
