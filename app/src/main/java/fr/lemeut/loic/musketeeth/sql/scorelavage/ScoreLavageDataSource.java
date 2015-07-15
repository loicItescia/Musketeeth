package fr.lemeut.loic.musketeeth.sql.scorelavage;

/**
 * Created by Loic on 03/07/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import fr.lemeut.loic.musketeeth.sql.MySQLiteHelper;

public class ScoreLavageDataSource {

    // Champs de la base de donnees
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_SCORE, MySQLiteHelper.COLUMN_DATESCORE };

    public ScoreLavageDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ScoreLavage createComment(String scoreLavage, String ts_dateScore) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SCORE, scoreLavage);
        values.put(MySQLiteHelper.COLUMN_DATESCORE, ts_dateScore);
        long insertId = database.insert(MySQLiteHelper.TABLE_SCORES, null,values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SCORES,allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,null, null, null);
        cursor.moveToFirst();
        ScoreLavage newComment = cursorToScoreLavage(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(ScoreLavage scoreLavage) {
        long id = scoreLavage.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_SCORES, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<ScoreLavage> getAllComments() {
        List<ScoreLavage> scores = new ArrayList<ScoreLavage>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SCORES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScoreLavage scoreLavage = cursorToScoreLavage(cursor);
            scores.add(scoreLavage);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return scores;
    }

    private ScoreLavage cursorToScoreLavage(Cursor cursor) {
        ScoreLavage scoreLavage = new ScoreLavage();
        scoreLavage.setId(cursor.getLong(0));
        scoreLavage.setscore(cursor.getString(1));
        scoreLavage.setTs_dateScore(cursor.getString(2));
        return scoreLavage;
    }
}