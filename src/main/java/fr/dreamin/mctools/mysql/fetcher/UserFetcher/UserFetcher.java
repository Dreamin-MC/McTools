package fr.dreamin.mctools.mysql.fetcher.UserFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.DTPlayer;
import fr.dreamin.mctools.mysql.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFetcher {

  public static void getIfInsert(DTPlayer dtPlayer) {

    try {

      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"user WHERE uuid = ?");
      prepareStatement.setString(1, dtPlayer.getPlayer().getUniqueId().toString());
      ResultSet rs = prepareStatement.executeQuery();

      while(rs.next()) {
        if (rs.getRow() == 1) {
          getUser(dtPlayer);
        }
      }

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    newUser(dtPlayer);
  }

  public static void newUser(DTPlayer dtPlayer){
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO  "+ McTools.getCodex().getDefaultPrefix() +"user (uuid,action,ambient,event) VALUES (?,?,?,?)");
      preparedStatement.setString(1, dtPlayer.getPlayer().getUniqueId().toString());
      preparedStatement.setInt(2, 80);
      preparedStatement.setInt(3, 80);
      preparedStatement.setInt(4, 80);

      dtPlayer.getVoiceManager().setVolumeAction(80);
      dtPlayer.getVoiceManager().setVolumeAmbien(80);
      dtPlayer.getVoiceManager().setVolumeEvent(80);

      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void getUser(DTPlayer dtPlayer){
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"user WHERE uuid = ?");
      preparedStatement.setString(1, dtPlayer.getPlayer().getUniqueId().toString());

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){
        dtPlayer.getVoiceManager().setVolumeAction(rs.getInt("action"));
        dtPlayer.getVoiceManager().setVolumeAmbien(rs.getInt("ambient"));
        dtPlayer.getVoiceManager().setVolumeEvent(rs.getInt("event"));
      }

      preparedStatement.close();


    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
