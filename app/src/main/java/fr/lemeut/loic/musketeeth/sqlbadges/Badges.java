package fr.lemeut.loic.musketeeth.sqlbadges;

/**
 * Created by Loic on 06/07/2015.
 */
public class Badges {
    private long id;
    private String Badges_BadgeName;
    private int Badges_ScoreMax;
    private int Badges_HasBadge;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBadges_BadgeName() {
        return Badges_BadgeName;
    }

    public void setBadges_BadgeName(String badges_BadgeName) {
        Badges_BadgeName = badges_BadgeName;
    }

    public int getBadges_ScoreMax() {
        return Badges_ScoreMax;
    }

    public void setBadges_ScoreMax(int badges_ScoreMax) {
        Badges_ScoreMax = badges_ScoreMax;
    }

    public int getBadges_HasBadge() {
        return Badges_HasBadge;
    }

    public void setBadges_HasBadge(int badges_HasBadge) {
        Badges_HasBadge = badges_HasBadge;
    }

    // Sera utilisee par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return Badges_BadgeName;
    }

}
