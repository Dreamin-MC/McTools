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

  public void registerCommand(String commandName, CommandInterface commandInterface) {
    commands.put(commandName.toLowerCase(), commandInterface);

    if (McTools.getInstance() != null) SimpleCommand.createCommand(commandName, this);
  }

  public void unregisterCommand(String commandName) {
    commands.remove(commandName.toLowerCase());
  }

  public void executeCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
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
      }

    }
    else sender.sendMessage("Commande inconnue : §7" + cmd.getName());

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
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

    executeCommand(commandSender, command, s, strings);

    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
    return List.of();
  }
}
