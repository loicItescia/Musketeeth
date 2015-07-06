package fr.lemeut.loic.musketeeth.sql;

/**
 * Created by Loic on 03/07/2015.
 */
public class ScoreLavage {
    private long id;
    private String score;
    private String ts_dateScore; // Timestamp

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getscore() {
        return score;
    }

    public void setscore(String score) {
        this.score = score;
    }

    // Sera utilisee par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return score;
    }

    public String getTs_dateScore() {
        return ts_dateScore;
    }

    public void setTs_dateScore(String ts_dateScore) {
        this.ts_dateScore = ts_dateScore;
    }
}