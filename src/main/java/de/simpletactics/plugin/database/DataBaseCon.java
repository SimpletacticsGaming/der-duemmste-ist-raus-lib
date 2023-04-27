package de.simpletactics.plugin.database;

import java.sql.Connection;

public class DataBaseCon {

  private Connection con;

  public DataBaseCon(Connection con) {
    this.con = con;
    System.out.println("Connection successfull established");
  }

  public Connection getCon() {
    return this.con;
  }
}
