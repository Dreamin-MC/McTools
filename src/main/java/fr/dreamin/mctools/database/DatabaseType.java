package fr.dreamin.mctools.database;

import lombok.Getter;

public enum DatabaseType {

  MYSQL("MYSQL"),
  SQLITE("SQLite");

  @Getter
  private final String name;

  DatabaseType(String name) {
    this.name = name;
  }

  //------FUNCTIONS------//

  public static DatabaseType getByName(String name) {
    for (DatabaseType type : values()) {
      if (type.getName().equalsIgnoreCase(name)) {
        return type;
      }
    }
    return null;
  }

}
