package fr.dreamin.mctools.api.player;

import org.bukkit.entity.Player;

public enum PlayerPerm {

  PLAYER("mctools.player"),
  HOTE("mctools.hote"),
  BUILD("mctools.build"),
  DEV("mctools.dev"),
  STAFF("mctools.staff"),
  MODO("mctools.modo"),
  ADMIN("mctools.admin");

  private String perm;

  PlayerPerm(String perm) {
    this.perm = perm;
  }

  //------GETTER------//

  public String getPerm() {
    return perm;
  }

  //------METHODS------//

  public static PlayerPerm getTopPerm(Player player) {

    PlayerPerm p = null;

    for (PlayerPerm perm : PlayerPerm.values()) {
      if (player.hasPermission(perm.getPerm())) p = perm;
    }

    if (p == null) p = PlayerPerm.PLAYER;

    return p;
  }

  public static boolean hasPermMin(PlayerPerm playerPerm, PlayerPerm getMinPerm) {
    PlayerPerm minPerm = getMinPerm;

    if (playerPerm.ordinal() >= minPerm.ordinal()) return true;
    else return false;

  }

}
