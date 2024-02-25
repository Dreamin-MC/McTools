package fr.dreamin.mctools.mysql.fetcher.UserFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.players.MTPlayer;
import fr.dreamin.mctools.mysql.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFetcher {

  public static void getIfInsert(MTPlayer MTPlayer) {

    try {

      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"user WHERE uuid = ?");
      prepareStatement.setString(1, MTPlayer.getPlayer().getUniqueId().toString());
      ResultSet rs = prepareStatement.executeQuery();

      while(rs.next()) {
        if (rs.getRow() == 1) {
          getUser(MTPlayer);
        }
      }

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    newUser(MTPlayer);
  }

  public static void newUser(MTPlayer MTPlayer){
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO  "+ McTools.getCodex().getDefaultPrefix() +"user (uuid,action,ambient,event) VALUES (?,?,?,?)");
      preparedStatement.setString(1, MTPlayer.getPlayer().getUniqueId().toString());
      preparedStatement.setInt(2, 80);
      preparedStatement.setInt(3, 80);
      preparedStatement.setInt(4, 80);

      MTPlayer.getVoiceManager().setVolumeAction(80);
      MTPlayer.getVoiceManager().setVolumeAmbien(80);
      MTPlayer.getVoiceManager().setVolumeEvent(80);

      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void getUser(MTPlayer MTPlayer){
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"user WHERE uuid = ?");
      preparedStatement.setString(1, MTPlayer.getPlayer().getUniqueId().toString());

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){
        MTPlayer.getVoiceManager().setVolumeAction(rs.getInt("action"));
        MTPlayer.getVoiceManager().setVolumeAmbien(rs.getInt("ambient"));
        MTPlayer.getVoiceManager().setVolumeEvent(rs.getInt("event"));
      }

      preparedStatement.close();


    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
