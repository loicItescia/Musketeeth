package fr.lemeut.loic.musketeeth.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Loic on 03/07/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_SCORES = "ScoreLavage";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCORE = "ScoreLavage";
    public static final String COLUMN_DATESCORE = "DateScore";

    public static final String TABLE_BADGES = "Badges";
    public static final String COLUMN_IDBADGE = "_id";
    public static final String COLUMN_NAME = "Badges_BadgeName";
    public static final String COLUMN_SCOREMAX = "Badges_ScoreMax";
    public static final String COLUMN_HASBADGE = "Badges_HasBadge";

    private static final String DATABASE_NAME = "ScoreLavageItescia5.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la creation de la base de donnees
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SCORES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SCORE +", "+ COLUMN_DATESCORE
            + ");";
    private static final String DATABASE_CREATEBADGE = "create table "
            + TABLE_BADGES + "(" + COLUMN_IDBADGE
            + " integer primary key autoincrement, " + COLUMN_NAME +", "+ COLUMN_SCOREMAX+", "+ COLUMN_HASBADGE
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATEBADGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BADGES);
        onCreate(db);
    }
}