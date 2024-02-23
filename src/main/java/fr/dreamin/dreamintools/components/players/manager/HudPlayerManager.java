package fr.dreamin.dreamintools.components.players.manager;

import fr.dreamin.dreamintools.api.hud.HudManager;
import fr.dreamin.dreamintools.components.players.DTPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HudPlayerManager {

  private HashMap<String, HudManager> hudManagers = new HashMap<>();
  private DTPlayer dtPlayer;

  public HudPlayerManager(DTPlayer dtPlayer) {
    this.dtPlayer = dtPlayer;
  }

  public DTPlayer getDtPlayer() {
    return dtPlayer;
  }

  public HashMap<String, HudManager> getHudManagers() {
    return hudManagers;
  }

  public void addHudManager(String name, HudManager hudManager) {
    this.hudManagers.put(name, hudManager);
  }

  public void removeHudManager(HudManager hudManager) {
    this.hudManagers.remove(hudManager.getHudType().name());
  }

  public void clearHudManagers() {
    this.hudManagers.clear();
  }

}
