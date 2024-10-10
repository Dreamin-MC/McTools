package fr.dreamin.api.cmd;

import fr.dreamin.mctools.McTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler implements CommandExecutor, TabExecutor {

  private final Map<String, CommandInterface> commands = new HashMap<>();

  public void registerCommand(CommandInterface commandInterface) {
    loadCommand(commandInterface);
  }

  public void unregisterCommand(String commandName) {
    commands.remove(commandName.toLowerCase());
  }

  private void loadCommand(CommandInterface commandInterface) {

    commands.put(commandInterface.name().toLowerCase(), commandInterface);

    SimpleCommand.createCommand(commandInterface.name(), this, commandInterface.aliases());
  }

  private void executeCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
    CommandInterface command = commands.get(cmd.getName().toLowerCase());

    if (command != null) {

      try {

        Method method = command.getClass().getMethod("execute", CommandSender.class, Command.class, String.class, String[].class);

        if (method.isAnnotationPresent(RequiredPermission.class)) {
          RequiredPermission permission = method.getAnnotation(RequiredPermission.class);
          String[] requiredPermission = permission.values();
          boolean requireAll = permission.requreAll();
          String msgNotPermission = permission.msgNotPerm();

          if (!hasPermision(sender, requiredPermission, requireAll)) {
            sender.sendMessage(msgNotPermission);
            return;
          }

        }
        method.invoke(command, sender, cmd, s, args);

      }
      catch (Exception e) {
        sender.sendMessage("§cErreur lors de l'exécution de la commande : §7" + e.getMessage());
        if (McTools.getInstance() != null) McTools.getInstance().getLogger().info(e.toString());
      }

    }
    else sender.sendMessage("Commande inconnue : §7" + cmd.getName());

  }

  private List<String> executeTabComplet(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
    CommandInterface command = commands.get(cmd.getName().toLowerCase());

    if (command != null) try {

      Method method = command.getClass().getMethod("tabComplete", CommandSender.class, Command.class, String.class, String[].class);

      if (method.isAnnotationPresent(RequiredPermission.class)) {
        RequiredPermission permission = method.getAnnotation(RequiredPermission.class);
        String[] requiredPermission = permission.values();
        boolean requireAll = permission.requreAll();

        if (!hasPermision(sender, requiredPermission, requireAll)) return List.of();
      }
      return (List<String>) method.invoke(command, sender, cmd, s, args);

    }
    catch (Exception e) {
      sender.sendMessage("§cErreur lors de l'exécution de la commande : §7" + e.getMessage());
    }
    return List.of();
  }

  private boolean hasPermision(CommandSender sender, String[] requiredPermissions, boolean requireAll) {
    boolean hasPermission = false;

    if (requireAll) {
      for (String perm : requiredPermissions) {
        if (!sender.hasPermission(perm)) {
          hasPermission = false;
          break;
        }
      }
    } else {
      for (String perm : requiredPermissions) {
        if (sender.hasPermission(perm)) {
          hasPermission = true;
          break;
        }
      }
    }
    return hasPermission;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

    executeCommand(sender, command, s, args);

    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    return executeTabComplet(sender, command, s, args);
  }
}
