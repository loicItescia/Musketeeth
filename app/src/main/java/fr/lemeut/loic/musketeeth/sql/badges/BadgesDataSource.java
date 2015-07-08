package fr.lemeut.loic.musketeeth.sql.badges;

/**
 * Created by Loic on 03/07/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.lemeut.loic.musketeeth.sql.MySQLiteHelper;

public class BadgesDataSource {

    // Champs de la base de donnees
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_IDBADGE,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_SCOREMAX,
            MySQLiteHelper.COLUMN_HASBADGE};

    public BadgesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Badges createBadge(String COLUMN_NAME, int COLUMN_SCOREMAX, int COLUMN_HASBADGE) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, COLUMN_NAME);
        values.put(MySQLiteHelper.COLUMN_SCOREMAX, COLUMN_SCOREMAX);
        values.put(MySQLiteHelper.COLUMN_HASBADGE, COLUMN_HASBADGE);
        long insertId = database.insert(MySQLiteHelper.TABLE_BADGES, null,values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_BADGES,allColumns, MySQLiteHelper.COLUMN_IDBADGE + " = " + insertId, null,null, null, null);
        cursor.moveToFirst();
        Badges badge = cursorToBadges(cursor);
        cursor.close();

        return badge;
    }

    public boolean applyBadge(long badge_id, int hasBadge) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_HASBADGE, hasBadge);

        long updateId = database.update(MySQLiteHelper.TABLE_BADGES,values, MySQLiteHelper.COLUMN_IDBADGE + " = " + badge_id, null);
        if(updateId!=0){
            return true;
        }
        return false;
    }

    public void deleteComment(Badges badge) {
        long id = badge.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_BADGES, MySQLiteHelper.COLUMN_IDBADGE + " = " + id, null);
    }

    public List<Badges> getAllBadges() {
        List<Badges> badges = new ArrayList<Badges>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_BADGES, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Badges badge = cursorToBadges(cursor);
            badges.add(badge);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return badges;
    }

    private Badges cursorToBadges(Cursor cursor) {
        Badges badge = new Badges();
        badge.setId(cursor.getLong(0));
        badge.setBadges_BadgeName(cursor.getString(1));
        badge.setBadges_ScoreMax(cursor.getInt(2));
        badge.setBadges_HasBadge(cursor.getInt(3));

        return badge;
    }
}