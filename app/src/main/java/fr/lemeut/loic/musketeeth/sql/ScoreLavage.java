package fr.lemeut.loic.musketeeth.sql;

/**
 * Created by Loic on 03/07/2015.
 */
public class ScoreLavage {
    private long id;
    private String score;

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

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return score;
    }
}