package fr.dreamin.dreamintools.api.luckPerms;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public class LuckPerms {

  private static net.luckperms.api.LuckPerms luckPermsApi = LuckPermsProvider.get();

  public static net.luckperms.api.LuckPerms getLuckPermsApi() {
    return luckPermsApi;
  }

  public static String getPrefix(Player player, net.luckperms.api.LuckPerms luckPermsApi) {
    User user = luckPermsApi.getUserManager().getUser(player.getUniqueId());

    if (user == null) {
      return "";
    }

    return user.getCachedData().getMetaData().getPrefix().split("&")[0] + " ";
  }

  public static String getSuffix(Player player, net.luckperms.api.LuckPerms luckPermsApi) {
    User user = luckPermsApi.getUserManager().getUser(player.getUniqueId());

    if (user == null) {
      return "";
    }

    return user.getCachedData().getMetaData().getSuffix();
  }

  public static String getPrefixColor(Player player, net.luckperms.api.LuckPerms luckPermsApi) {
    User user = luckPermsApi.getUserManager().getUser(player.getUniqueId());

    if (user == null) {
      return "";
    }

    return user.getCachedData().getMetaData().getPrefix().split("&")[1];
  }

}
