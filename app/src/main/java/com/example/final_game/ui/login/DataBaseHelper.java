package com.example.final_game.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** DataBaseHelper class creataed and manages a SQLlite databsase*/

public class DataBaseHelper extends SQLiteOpenHelper {

  /**The next 4 static Strings are variable representing the name of the databse and the
   names of the tables.*/
  private static final String DATABASE_NAME = "GAME.db";
  private static final String TABLE1_NAME = "GAME1STATS";
  private static final String TABLE2_NAME = "GAME2STATS";
  private static final String TABLE3_NAME = "GAME3STATS";
  private static final String LOGIN_TABLE = "LOGIN";

  /** This is Card game table and its columns names.*/
  private static final String TABLE1_PLAYER_NAME = "NAME";
  private static final String TABLE1_STAT1 = "TABLE1_STAT1";
  private static final String TABLE1_STAT2 = "TABLE1_STAT2";
  private static final String TABLE1_STAT3 = "TABLE1_STAT3";

  /** This is Ant game table and its columns names.*/
  private static final String TABLE2_PLAYER_NAME = "NAME";
  private static final String TABLE2_STAT1 = "TABLE2_STAT1";
  private static final String TABLE2_STAT2 = "TABLE2_STAT2";
  private static final String TABLE2_STAT3 = "TABLE2_STAT3";

  /** This is TrueBLue game table and its columns names.*/
  private static final String TABLE3_PLAYER_NAME = "NAME";
  private static final String TABLE3_STAT1 = "TABLE3_STAT1";
  private static final String TABLE3_STAT2 = "TABLE3_STAT2";
  private static final String TABLE3_STAT3 = "TABLE3_STAT3";

  /** This is the Login Table and its columns names.*/
  private static final String LOGIN_TABLE_ID = "ID";
  private static final String USERNAME = "USERNAME";
  private static final String PASSWORD = "PASSWORD";
  private static String userName;

  DataBaseHelper(Context context) {
    super(context, DATABASE_NAME, null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    // even spacing matters in SqlLite queries
    sqLiteDatabase.execSQL(
        "CREATE TABLE "
            + TABLE1_NAME
            + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "NAME TEXT, TABLE1_STAT1 TEXT, TABLE1_STAT2 TEXT, TABLE1_STAT3 TEXT)");
    sqLiteDatabase.execSQL(
        "CREATE TABLE "
            + TABLE2_NAME
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "NAME TEXT, TABLE2_STAT1 TEXT, TABLE2_STAT2 TEXT, TABLE2_STAT3 TEXT)");
    sqLiteDatabase.execSQL(
        "CREATE TABLE "
            + TABLE3_NAME
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "NAME TEXT, TABLE3_STAT1 TEXT, TABLE3_STAT2 TEXT, TABLE3_STAT3 TEXT)");
    sqLiteDatabase.execSQL(
        "CREATE TABLE "
            + LOGIN_TABLE
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "USERNAME TEXT, PASSWORD TEXT)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + TABLE1_NAME);
    sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + TABLE2_NAME);
    sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + TABLE3_NAME);
    sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + LOGIN_TABLE);
    onCreate(sqLiteDatabase);
  }

  /** Inserts the data into the specific table in the database depending on the parameter table.*/
  public void insertData(String table, String name, String stat1, String stat2, String stat3) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    if (table.equals(TABLE1_NAME)) {
      contentValues.put(TABLE1_PLAYER_NAME, name);
      contentValues.put(TABLE1_STAT1, stat1);
      contentValues.put(TABLE1_STAT2, stat2);
      contentValues.put(TABLE1_STAT3, stat3);
      db.insert(TABLE1_NAME, null, contentValues);
    } else if (table.equals(TABLE2_NAME)) {
      contentValues.put(TABLE2_PLAYER_NAME, name);
      contentValues.put(TABLE2_STAT1, stat1);
      contentValues.put(TABLE2_STAT2, stat2);
      contentValues.put(TABLE2_STAT3, stat3);
      db.insert(TABLE2_NAME, null, contentValues);
    } else if (table.equals(TABLE3_NAME)) {
      contentValues.put(TABLE3_PLAYER_NAME, name);
      contentValues.put(TABLE3_STAT1, stat1);
      contentValues.put(TABLE3_STAT2, stat2);
      contentValues.put(TABLE3_STAT3, stat3);
      db.insert(TABLE3_NAME, null, contentValues);
    }
  }

  /** Return all the data in the given table passed as parameter.*/
  public Cursor getAllData(String table) {
    SQLiteDatabase db = this.getWritableDatabase();
    if (table.equals(TABLE1_NAME)) {
      return db.rawQuery("select * from " + TABLE1_NAME, null);
    } else if (table.equals(TABLE2_NAME)) {
      return  db.rawQuery("select * from " + TABLE2_NAME, null);

    } else {
      return  db.rawQuery("select * from " + TABLE3_NAME, null);

    }
  }

  /** Return all the data of a specific game of the current user playing the game.*/
  public Cursor getDataByName(String table) {
    SQLiteDatabase db = this.getWritableDatabase();
    String name = getUSERNAME().substring(0, getUSERNAME().indexOf('@'));
    if (table.equals(TABLE1_NAME)) {
      return db.rawQuery(
          "select * from " + TABLE1_NAME + " WHERE " + TABLE1_PLAYER_NAME + "='" + name + "'",
          null);
    } else if (table.equals(TABLE2_NAME)) {
      return
          db.rawQuery(
              "select * from " + TABLE2_NAME + " WHERE " + TABLE2_PLAYER_NAME + "='" + name + "'",
              null);
    } else {
      return db.rawQuery(
          "select * from " + TABLE3_NAME + " WHERE " + TABLE3_PLAYER_NAME + "='" + name + "'",
          null);
    }
  }

  /** Return all the data of a specific game sorted by highest to lowest score.*/
  public Cursor getDataByStat1(String table) {
    SQLiteDatabase db = this.getWritableDatabase();
    if (table.equals(TABLE1_NAME)) {
      return db.rawQuery(
          "select * from "
              + TABLE1_NAME
              + " ORDER BY "
              + "CAST("
              + TABLE1_STAT1
              + " AS "
              + "INTEGER)"
              + " DESC",
          null);
    } else if (table.equals(TABLE2_NAME)) {
      return db.rawQuery(
          "select * from "
              + TABLE2_NAME
              + " ORDER BY "
              + "CAST("
              + TABLE2_STAT1
              + " AS "
              + "INTEGER)"
              + " DESC",
          null);
    } else {
      return db.rawQuery(
          "select * from "
              + TABLE3_NAME
              + " ORDER BY "
              + "CAST("
              + TABLE3_STAT1
              + " AS "
              + "INTEGER)"
              + " DESC",
          null);
    }
  }

  /** Return all the data of a specific game sorted by highest to lowest level.*/
  public Cursor getDataByStat3(String table) {
    SQLiteDatabase db = this.getWritableDatabase();
    if (table.equals(TABLE1_NAME)) {
      return db.rawQuery(
          "select * from " + TABLE1_NAME + " ORDER BY " + TABLE1_STAT3 + " DESC", null);
    } else if (table.equals(TABLE2_NAME)) {
      return db.rawQuery(
          "select * from " + TABLE2_NAME + " ORDER BY " + TABLE2_STAT3 + " DESC", null);
    } else {
      return db.rawQuery(
          "select * from " + TABLE3_NAME + " ORDER BY " + TABLE3_STAT3 + " DESC", null);
    }
  }

  /** Creates a new user in the database.*/
  void createUser(String username, String password) {
    SQLiteDatabase db = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    userName = username;
    contentValues.put(USERNAME, username);
    contentValues.put(PASSWORD, password);
    db.insert(LOGIN_TABLE, null, contentValues);
  }

  /** Validates the user credentials when a user logs in again using his registered credentials.*/
  boolean checkUser(String username, String password) {
    SQLiteDatabase db = getReadableDatabase();
    userName = username;
    String selection = USERNAME + "=?" + " and " + PASSWORD + "=?";
    String[] selectionArgs = {username, password};
    String[] columns = {LOGIN_TABLE_ID};
    Cursor cursor = db.query(LOGIN_TABLE, columns, selection, selectionArgs, null, null, null);
    int count = cursor.getCount();
    cursor.close();
    return count > 0;
  }

  /** Return the current username of the user.*/
  public static String getUSERNAME() {
    return userName;
  }
}
