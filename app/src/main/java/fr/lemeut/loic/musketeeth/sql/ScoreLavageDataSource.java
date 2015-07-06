package fr.lemeut.loic.musketeeth.sql;

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

public class ScoreLavageDataSource {

    // Champs de la base de donnees
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_COMMENT, MySQLiteHelper.COLUMN_DATESCORE };

    public ScoreLavageDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ScoreLavage createComment(String comment, String ts_dateScore) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        values.put(MySQLiteHelper.COLUMN_DATESCORE, ts_dateScore);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,null, null, null);
        cursor.moveToFirst();
        ScoreLavage newComment = cursorToScoreLavage(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(ScoreLavage comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<ScoreLavage> getAllComments() {
        List<ScoreLavage> comments = new ArrayList<ScoreLavage>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScoreLavage comment = cursorToScoreLavage(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return comments;
    }

    private ScoreLavage cursorToScoreLavage(Cursor cursor) {
        ScoreLavage comment = new ScoreLavage();
        comment.setId(cursor.getLong(0));
        comment.setscore(cursor.getString(1));
        comment.setTs_dateScore(cursor.getString(2));
        return comment;
    }
}