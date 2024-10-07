package fr.dreamin.api.cmd;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandInterface {

  default String name() {
    return "";
  }

  default String[] aliases() {
    return new String[]{};
  }

  boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args);

  List<String> tabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args);
}
