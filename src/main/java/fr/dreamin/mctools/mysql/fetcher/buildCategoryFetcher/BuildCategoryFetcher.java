package fr.dreamin.mctools.mysql.fetcher.buildCategoryFetcher;

import fr.dreamin.mctools.McTools;
import fr.dreamin.mctools.components.build.TagCategory;
import fr.dreamin.mctools.mysql.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildCategoryFetcher {

  public static boolean getIfInsert(String value) {

    try {

      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"buildcategory WHERE value = ?");
      prepareStatement.setString(1, value);
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

  public static TagCategory getCategory(int id) {

    try {

      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"buildcategory WHERE id = ?");
      prepareStatement.setInt(1, id);
      ResultSet rs = prepareStatement.executeQuery();

      while(rs.next()) {
        if (rs.getRow() == 1) {
          return new TagCategory(rs.getInt("id"), rs.getString("value"));
        }
      }
      return null;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static TagCategory getCategory(String value) {

    try {

      PreparedStatement prepareStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"buildcategory WHERE value = ?");
      prepareStatement.setString(1, value);
      ResultSet rs = prepareStatement.executeQuery();

      while(rs.next()) {
        if (rs.getRow() == 1) {
          return new TagCategory(rs.getInt("id"), rs.getString("value"));
        }
      }
      return null;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void newTagCategory(String value){
    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO  "+ McTools.getCodex().getDefaultPrefix() +"buildcategory (value) VALUES (?)");
      preparedStatement.setString(1, value);

      preparedStatement.execute();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<TagCategory> getAllCategory(){

    List<TagCategory> tagCategories = new ArrayList<>();

    try {
      PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM "+ McTools.getCodex().getDefaultPrefix() +"buildcategory");
      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){
        tagCategories.add(new TagCategory(rs.getInt("id"), rs.getString("value")));
      }

      preparedStatement.close();


    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tagCategories;
  }

}
