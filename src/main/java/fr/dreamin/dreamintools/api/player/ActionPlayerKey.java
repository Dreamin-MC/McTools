package fr.dreamin.dreamintools.api.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public  class ActionPlayerKey {
    private final UUID playerId;
    private final String action;

    // Constructeur pour créer une nouvelle clé avec l'ID du joueur et l'action
    public ActionPlayerKey(UUID playerId, String action) {
      this.playerId = playerId;
      this.action = action;
    }

    // Redéfinit equals pour comparer deux clés
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ActionPlayerKey that = (ActionPlayerKey) o;
      return playerId.equals(that.playerId) && action.equals(that.action);
    }

    // Redéfinit hashCode pour utiliser les hashCodes de l'ID du joueur et de l'action
    @Override
    public int hashCode() {
      return 31 * playerId.hashCode() + action.hashCode();
    }

    public UUID getPlayerId() {
      return playerId;
    }

    public Player getPlayer() {
      return Bukkit.getPlayer(playerId);
    }

    public String getAction() {
      return action;
    }

  }