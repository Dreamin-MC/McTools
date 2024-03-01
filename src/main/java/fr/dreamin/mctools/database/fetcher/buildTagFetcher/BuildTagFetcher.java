package fr.dreamin.mctools.database.fetcher.buildTagFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.build.Tag;
import fr.dreamin.mctools.database.DatabaseManager;
import fr.dreamin.mctools.database.DatabaseType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildTagFetcher {

  public static boolean getIfInsert(String keyName, String value) {

    try {

      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"buildtag WHERE keyName = ? AND value = ?");
      prepareStatement.setString(1, keyName);
      prepareStatement.setString(2, value);
      ResultSet rs = prepareStatement.executeQuery();

      while(rs.next()) {
        if (rs.getRow() == 1) {
          return true;
        }
      }
      return false;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void newTag(String keyName, String value, int categoryId){
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO  "+ McTools.getCodex().getDefaultPrefix() +"buildtag (keyName, value, categoryId) VALUES (?,?, ?)");
      preparedStatement.setString(1, keyName);
      preparedStatement.setString(2, value);
      preparedStatement.setInt(3, categoryId);

      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<Tag> getAllTag(){

    List<Tag> tags = new ArrayList<>();

    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"buildtag");
      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){
        tags.add(new Tag(rs.getString("keyName"), rs.getString("value"), rs.getInt("categoryId")));
      }

      preparedStatement.close();


    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tags;
  }

}
