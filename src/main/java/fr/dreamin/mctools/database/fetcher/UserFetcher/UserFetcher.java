package fr.dreamin.mctools.database.fetcher.UserFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.json.JsonManager;
import fr.dreamin.mctools.components.lang.Lang;
import fr.dreamin.mctools.components.lang.LangMsg;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.database.DatabaseManager;
import fr.dreamin.mctools.database.DatabaseType;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFetcher {

  public static void getIfInsert(MTPlayer mtPlayer) {

    try {
      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT lang FROM " + McTools.getCodex().getDefaultPrefix() + "user WHERE uuid =?");
      prepareStatement.setString(1, mtPlayer.getPlayer().getUniqueId().toString());
      ResultSet rs = prepareStatement.executeQuery();

      if (rs.next()) getUser(mtPlayer);
      else newUser(mtPlayer);


    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void updatePlayer(MTPlayer mtPlayer) {
    try {

      String ip = mtPlayer.getIp();
      if (!ip.contains(mtPlayer.getPlayer().getAddress().getAddress().getHostAddress())) ip += mtPlayer.getPlayer().getAddress().getAddress().getHostAddress() + ",";

      if (mtPlayer.getVoiceManager() != null) {
        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE "+ McTools.getCodex().getDefaultPrefix() +"user SET volAction =?, volAmbien =?, volEvent =?, lang =?, ip=? WHERE uuid =?");
        preparedStatement.setInt(1, mtPlayer.getVoiceManager().getVolumeAction());
        preparedStatement.setInt(2, mtPlayer.getVoiceManager().getVolumeAmbien());
        preparedStatement.setInt(3, mtPlayer.getVoiceManager().getVolumeEvent());
        preparedStatement.setString(4, mtPlayer.getLang().getNameCode());
        preparedStatement.setString(5, ip);
        preparedStatement.setString(6, mtPlayer.getPlayer().getUniqueId().toString());

        preparedStatement.execute();
        preparedStatement.close();
      }
      else {

        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE "+ McTools.getCodex().getDefaultPrefix() +"user SET lang =?, ip =? WHERE uuid =?");
        preparedStatement.setString(1, mtPlayer.getLang().getNameCode());
        preparedStatement.setString(2, ip);
        preparedStatement.setString(3, mtPlayer.getPlayer().getUniqueId().toString());

        preparedStatement.execute();
        preparedStatement.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public static boolean getIfIpExist(MTPlayer mtPlayer) {

    try {
      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM dmtools_user WHERE uuid !=? AND FIND_IN_SET(?, REPLACE(ip, ' ', '')) > 0");
      prepareStatement.setString(1, mtPlayer.getPlayer().getUniqueId().toString());
      prepareStatement.setString(2, mtPlayer.getPlayer().getAddress().getAddress().getHostAddress());
      ResultSet rs = prepareStatement.executeQuery();

      if (rs.next()) return true;
      else return false;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void newUser(MTPlayer mtPlayer){

    String languageCode = JsonManager.getLanguageFromIP(mtPlayer.getPlayer().getAddress().getAddress().getHostAddress());
    mtPlayer.setIp(mtPlayer.getPlayer().getAddress().getAddress().getHostAddress() + ",");

    if (Lang.isValidLanguage(languageCode)) mtPlayer.setLang(Lang.getLangByName(languageCode));
    else mtPlayer.setLang(McTools.getCodex().getDefaultLang());


    Bukkit.broadcastMessage("double : " + McTools.getCodex().isDoubleCount());
    Bukkit.broadcastMessage("exist : " + getIfIpExist(mtPlayer));

    if (!McTools.getCodex().isDoubleCount())
      if (getIfIpExist(mtPlayer)) {
        mtPlayer.getPlayer().kickPlayer(mtPlayer.getMsg(LangMsg.ERROR_DOUBLECOUNT, ""));
        return;
      }

    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO "+ McTools.getCodex().getDefaultPrefix() +"user (uuid,volAction,volAmbien,volEvent, lang, ip) VALUES (?,?,?,?, ?, ?)");
      preparedStatement.setString(1, mtPlayer.getPlayer().getUniqueId().toString());
      preparedStatement.setInt(2, 80);
      preparedStatement.setInt(3, 80);
      preparedStatement.setInt(4, 80);
      preparedStatement.setString(5, mtPlayer.getLang().getNameCode());
      preparedStatement.setString(6, mtPlayer.getIp());

      if (McTools.getCodex().isVoiceMode() && mtPlayer.getVoiceManager() != null) {
        mtPlayer.getVoiceManager().setVolumeAction(80);
        mtPlayer.getVoiceManager().setVolumeAmbien(80);
        mtPlayer.getVoiceManager().setVolumeEvent(80);
      }


      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void getUser(MTPlayer mtPlayer){
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"user WHERE uuid = ?");
      preparedStatement.setString(1, mtPlayer.getPlayer().getUniqueId().toString());

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {

        if (mtPlayer.getVoiceManager() != null) {
          mtPlayer.getVoiceManager().setVolumeAction(rs.getInt("volAction"));
          mtPlayer.getVoiceManager().setVolumeAmbien(rs.getInt("volAmbien"));
          mtPlayer.getVoiceManager().setVolumeEvent(rs.getInt("volEvent"));
        }
        mtPlayer.setLang(Lang.getLangByName(rs.getString("lang")));
        mtPlayer.setIp(rs.getString("ip"));

      }

      preparedStatement.close();


    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
