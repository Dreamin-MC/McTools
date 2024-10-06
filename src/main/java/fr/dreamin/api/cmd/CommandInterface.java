package fr.dreamin.api.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface CommandInterface {

  void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args);

}
