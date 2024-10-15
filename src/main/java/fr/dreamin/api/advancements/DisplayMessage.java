package fr.dreamin.api.advancements;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class DisplayMessage {

  /**
   * Display message to player
   * @param player Player to view
   */
  public abstract void showTo(Player player);

  /**
   * Show message to onlines players
   */
  public void showToAll()	{
    showTo((Player) Bukkit.getServer().getOnlinePlayers().stream().toList());
  };

  /**
   * Show message to specifics players
   * @param players Set of players
   */
  public void showTo(List<Player> players)	{
    for (Player player : players)	{
      showTo(player);
    }
  }

  /**
   * Show message to specifics players
   * @param players Players to view
   */
  public void showTo(Player... players)	{
    showTo(Arrays.asList(players));
  }
}