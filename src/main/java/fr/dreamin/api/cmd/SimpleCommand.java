package fr.dreamin.api.cmd;

import fr.dreamin.mctools.McTools;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.List;
import java.util.function.BiConsumer;

public class SimpleCommand {

  /**
   * Crée une commande simple en utilisant un BiConsumer.
   * Le BiConsumer reçoit le CommandSender et les arguments de la commande (String[]).
   *
   * @param name   Le nom de la commande.
   * @param action L'action à exécuter lors de l'exécution de la commande.
   */
  public static void createCommand(String name, BiConsumer<CommandSender, String[]> action) {
    if (McTools.getInstance() == null) return;

    // Créer un exécuteur de commande basé sur l'action fournie.
    CommandExecutor executor = (sender, command, label, args) -> {
      action.accept(sender, args);  // Exécute l'action fournie pour cette commande.
      return true;  // Retourne true si la commande est traitée correctement.
    };

    registerCommand(name, executor, null);  // Enregistre la commande sans alias.
  }

  /**
   * Crée une commande avec un BiConsumer et des alias (tableau).
   *
   * @param name    Le nom de la commande.
   * @param action  L'action à exécuter lors de l'exécution de la commande.
   * @param aliases Les alias de la commande (en tant que tableau).
   */
  public static void createCommand(String name, BiConsumer<CommandSender, String[]> action, String... aliases) {
    if (McTools.getInstance() == null) return;

    CommandExecutor executor = (sender, command, label, args) -> {
      action.accept(sender, args);
      return true;
    };

    registerCommand(name, executor, List.of(aliases));  // Enregistre la commande avec les alias.
  }

  /**
   * Crée une commande avec un BiConsumer et des alias (liste).
   *
   * @param name    Le nom de la commande.
   * @param action  L'action à exécuter lors de l'exécution de la commande.
   * @param aliases Les alias de la commande (en tant que liste).
   */
  public static void createCommand(String name, BiConsumer<CommandSender, String[]> action, List<String> aliases) {
    if (McTools.getInstance() == null) return;

    CommandExecutor executor = (sender, command, label, args) -> {
      action.accept(sender, args);
      return true;
    };

    registerCommand(name, executor, aliases);  // Enregistre la commande avec les alias.
  }

  /**
   * Crée une commande en utilisant un CommandExecutor personnalisé.
   *
   * @param name     Le nom de la commande.
   * @param executor L'exécuteur de commande personnalisé.
   */
  public static void createCommand(String name, CommandExecutor executor) {
    if (McTools.getInstance() == null) return;
    registerCommand(name, executor, null);  // Enregistre la commande sans alias.
  }

  /**
   * Crée une commande avec des alias (tableau) en utilisant un CommandExecutor personnalisé.
   *
   * @param name     Le nom de la commande.
   * @param executor L'exécuteur de commande personnalisé.
   * @param aliases  Les alias de la commande (en tant que tableau).
   */
  public static void createCommand(String name, CommandExecutor executor, String... aliases) {
    if (McTools.getInstance() == null) return;
    registerCommand(name, executor, List.of(aliases));  // Enregistre la commande avec les alias.
  }

  /**
   * Crée une commande avec des alias (liste) en utilisant un CommandExecutor personnalisé.
   *
   * @param name     Le nom de la commande.
   * @param executor L'exécuteur de commande personnalisé.
   * @param aliases  Les alias de la commande (en tant que liste).
   */
  public static void createCommand(String name, CommandExecutor executor, List<String> aliases) {
    if (McTools.getInstance() == null) return;
    registerCommand(name, executor, aliases);  // Enregistre la commande avec les alias.
  }

  /**
   * Méthode privée utilitaire pour enregistrer une commande avec ou sans alias.
   *
   * @param name     Le nom de la commande.
   * @param executor L'exécuteur de commande.
   * @param aliases  Les alias de la commande (peuvent être null).
   */
  private static void registerCommand(String name, CommandExecutor executor, List<String> aliases) {
    PluginCommand cmd = McTools.getInstance().getCommand(name);
    if (cmd != null) {
      if (aliases != null && !aliases.isEmpty()) {
        cmd.setAliases(aliases);  // Ajoute les alias si fournis.
      }
      cmd.setExecutor(executor);  // Définit l'exécuteur de la commande.
    }
  }
}