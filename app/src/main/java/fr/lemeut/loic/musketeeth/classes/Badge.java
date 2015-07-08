package fr.lemeut.loic.musketeeth.classes;

import android.widget.Switch;

import fr.lemeut.loic.musketeeth.R;

/**
 * Created by Loic on 07/07/2015.
 */
public class Badge {
    private String DescBadge = "";
    int imageBadge = 0;
    public Badge() {

    }

    public String getDescBadge(long idBadgeLong){
        int idBadge = (int) idBadgeLong;
        switch (idBadge) {
            case 0:
                DescBadge = "Je viens de débloquer le Badge niveau 0 !";
                break;
            case 1:
                DescBadge = "Je viens de débloquer le Badge niveau 1 !";
                break;
            case 2:
                DescBadge = "Je viens de débloquer le Badge niveau 2 !";
                break;
            case 3:
                DescBadge = "Je viens de débloquer le Badge niveau 3 !";
                break;
            case 4:
                DescBadge = "Je viens de débloquer le Badge niveau 4 !";
                break;
            case 5:
                DescBadge = "Je viens de débloquer le Badge niveau 5 !";
                break;
            case 6:
                DescBadge = "Je viens de débloquer le Badge niveau 6 !";
                break;
            case 7:
                DescBadge = "Je viens de débloquer le Badge niveau 7 !";
                break;
        }
        return DescBadge;
    }

    public int getImageBadge(long idBadgeLong){
        int idBadge = (int) idBadgeLong;
        switch (idBadge) {
            case -1:
                imageBadge =  R.mipmap.ico_princ;
                break;
            case 0:
                imageBadge =  R.mipmap.ico_badge_1;
                break;
            case 1:
                imageBadge =  R.mipmap.ico_badge_1;
                break;
            case 2:
                imageBadge =  R.mipmap.ico_badge_2;
                break;
            case 3:
                imageBadge =  R.mipmap.ico_badge_3;
                break;
            case 4:
                imageBadge =  R.mipmap.ico_badge_4;
                break;
            case 5:
                imageBadge =  R.mipmap.ico_badge_5;
                break;
            case 6:
                imageBadge =  R.mipmap.ico_badge_6;
                break;
            case 7:
                imageBadge =  R.mipmap.ico_badge_7;
                break;
        }
        return imageBadge;
    }



}
