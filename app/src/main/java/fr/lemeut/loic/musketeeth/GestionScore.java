package fr.lemeut.loic.musketeeth;

import android.os.Bundle;

/**
 * Created by Loic on 03/07/2015.
 */
public class GestionScore {
    private float scoreFinal = 0;
    private int pointsmessageTempsLavage = 10;
    private int pointsDEVANT_VERTICAL = 40;
    private int pointsBAS_HORIZONTALE = 20;
    private int pointsHAUT_HORIZONTALE= 20;
    private int pointsDERRIERE_HAUT = 10;
    private int pointsDERRIERE_BAS = 10;
    private int pointsNOTHING = -10;

    public GestionScore(Bundle myIntent) {
        float messageTempsLavage = (float)myIntent.get("tempsLavage");
        float SCORE_DEVANT_VERTICAL =  (float)myIntent.get("SCORE_DEVANT_VERTICAL")/1000;
        float SCORE_DESSUS_BAS_HORIZONTALE =  (float)myIntent.get("SCORE_DESSUS_BAS_HORIZONTALE")/1000;
        float SCORE_DESSOUS_HAUT_HORIZONTALE =  (float)myIntent.get("SCORE_DESSOUS_HAUT_HORIZONTALE")/1000;
        float SCORE_DERRIERE_HAUT =  (float)myIntent.get("SCORE_DERRIERE_HAUT")/1000;
        float SCORE_DERRIERE_BAS =  (float)myIntent.get("SCORE_DERRIERE_BAS")/1000;
        float SCORE_NOTHING = (float)myIntent.get("SCORE_NOTHING")/1000;


        scoreFinal = (messageTempsLavage*pointsmessageTempsLavage) +
                    (SCORE_DEVANT_VERTICAL*pointsDEVANT_VERTICAL)+
                    (SCORE_DESSUS_BAS_HORIZONTALE*pointsBAS_HORIZONTALE)+
                    (SCORE_DESSOUS_HAUT_HORIZONTALE*pointsHAUT_HORIZONTALE)+
                    (SCORE_DERRIERE_HAUT*pointsDERRIERE_HAUT)+
                    (SCORE_DERRIERE_BAS*pointsDERRIERE_BAS)+
                    (SCORE_NOTHING*pointsNOTHING);

    }


    public int getScoreFinal() {
        return Math.round(scoreFinal);
    }

}
