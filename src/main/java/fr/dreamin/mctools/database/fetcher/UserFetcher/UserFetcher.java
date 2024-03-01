package fr.dreamin.mctools.database.fetcher.UserFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.api.json.JsonManager;
import fr.dreamin.mctools.components.lang.Lang;
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

      if (mtPlayer.getVoiceManager() != null) {
        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE "+ McTools.getCodex().getDefaultPrefix() +"user SET volAction =?, volAmbien =?, volEvent =?, lang =? WHERE uuid =?");
        preparedStatement.setInt(1, mtPlayer.getVoiceManager().getVolumeAction());
        preparedStatement.setInt(2, mtPlayer.getVoiceManager().getVolumeAmbien());
        preparedStatement.setInt(3, mtPlayer.getVoiceManager().getVolumeEvent());
        preparedStatement.setString(4, mtPlayer.getLang().name());
        preparedStatement.setString(4, mtPlayer.getPlayer().getUniqueId().toString());

        preparedStatement.execute();
        preparedStatement.close();
      }
      else {

        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE "+ McTools.getCodex().getDefaultPrefix() +"user SET lang =? WHERE uuid =?");
        preparedStatement.setString(1, mtPlayer.getLang().name());
        preparedStatement.setString(2, mtPlayer.getPlayer().getUniqueId().toString());

        preparedStatement.execute();
        preparedStatement.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void newUser(MTPlayer mtPlayer){

    String languageCode = JsonManager.getLanguageFromIP(mtPlayer.getPlayer().getAddress().getAddress().getHostAddress());

    if (Lang.isValidLanguage(languageCode)) mtPlayer.setLang(Lang.valueOf(languageCode));
    else mtPlayer.setLang(McTools.getCodex().getDefaultLang());

    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO "+ McTools.getCodex().getDefaultPrefix() +"user (uuid,volAction,volAmbien,volEvent, lang) VALUES (?,?,?,?, ?)");
      preparedStatement.setString(1, mtPlayer.getPlayer().getUniqueId().toString());
      preparedStatement.setInt(2, 80);
      preparedStatement.setInt(3, 80);
      preparedStatement.setInt(4, 80);
      preparedStatement.setString(5, mtPlayer.getLang().name());

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
        mtPlayer.setLang(Lang.valueOf(rs.getString("lang")));

      }

      preparedStatement.close();


    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
