package fr.lemeut.loic.musketeeth.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.lemeut.loic.musketeeth.classes.GestionScore;
import fr.lemeut.loic.musketeeth.R;
import fr.lemeut.loic.musketeeth.sqlbadges.Badges;
import fr.lemeut.loic.musketeeth.sqlbadges.BadgesDataSource;
import fr.lemeut.loic.musketeeth.sqlscorelavage.ScoreLavage;
import  fr.lemeut.loic.musketeeth.sqlscorelavage.ScoreLavageDataSource;

/**
 * Created by Loic on 03/07/2015.
 */
public class LavageEndStatsActivity extends ActionBarActivity {
    private TextView viewTempsLavage, viewSCORE_DEVANT_VERTICAL, viewSCORE_DESSUS_BAS_HORIZONTALE, viewSCORE_DESSOUS_HAUT_HORIZONTALE,
            viewSCORE_DERRIERE_HAUT, viewSCORE_DERRIERE_BAS, viewSCORE_NOTHING, viewScoreFinal;
    private Button buttonShare, buttonDeleteBDD, buttonDeleteBDDBadges;
    String messageTempsLavage = "";
    private float TempsLavage = 0;
    private String SCORE_DEVANT_VERTICAL;
    private String SCORE_DESSUS_BAS_HORIZONTALE;
    private String SCORE_DESSOUS_HAUT_HORIZONTALE;
    private String SCORE_DERRIERE_HAUT;
    private String SCORE_DERRIERE_BAS;
    private String SCORE_NOTHING;
    private ScoreLavageDataSource datasource;
    private ScoreLavage comment;
    private Context _context;

    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = this;
        Bundle myIntent = getIntent().getExtras(); // gets the previously created intent
        GestionScore scoreFinal = null;

        // Initialisation de l'API Facebook et du bouton présent dans le layout
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        // Initialisation du layout
        setContentView(R.layout.lavage_end_stats);

        // Gestion des TextView
        gestionTextView();
        gestionButton();

        // Recuperation des donnees de l'Intent
        if (myIntent != null) {
            scoreFinal = new GestionScore(myIntent);
            messageTempsLavage = Float.toString((float) myIntent.get("tempsLavage"));
            SCORE_DEVANT_VERTICAL = Float.toString((float) myIntent.get("SCORE_DEVANT_VERTICAL") / 1000);
            SCORE_DESSUS_BAS_HORIZONTALE = Float.toString((float) myIntent.get("SCORE_DESSUS_BAS_HORIZONTALE") / 1000);
            SCORE_DESSOUS_HAUT_HORIZONTALE = Float.toString((float) myIntent.get("SCORE_DESSOUS_HAUT_HORIZONTALE") / 1000);
            SCORE_DERRIERE_HAUT = Float.toString((float) myIntent.get("SCORE_DERRIERE_HAUT") / 1000);
            SCORE_DERRIERE_BAS = Float.toString((float) myIntent.get("SCORE_DERRIERE_BAS") / 1000);
            SCORE_NOTHING = Float.toString((float) myIntent.get("SCORE_NOTHING") / 1000);

        } else {
            messageTempsLavage = "ERR";
        }

        // Affichage des score a l'ecran
        viewTempsLavage.setText(messageTempsLavage + " s");
        viewSCORE_DEVANT_VERTICAL.setText(SCORE_DEVANT_VERTICAL + " s");
        viewSCORE_DESSUS_BAS_HORIZONTALE.setText(SCORE_DESSUS_BAS_HORIZONTALE + " s");
        viewSCORE_DESSOUS_HAUT_HORIZONTALE.setText(SCORE_DESSOUS_HAUT_HORIZONTALE + " s");
        viewSCORE_DERRIERE_HAUT.setText(SCORE_DERRIERE_HAUT + " s");
        viewSCORE_DERRIERE_BAS.setText(SCORE_DERRIERE_BAS + " s");
        viewSCORE_NOTHING.setText(SCORE_NOTHING + " s");
        viewScoreFinal.setText(Integer.toString(scoreFinal.getScoreFinal()));


        // Sauvegarde du score en BDD
        datasource = new ScoreLavageDataSource(this);
        datasource.open();
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int Month = cal.get(Calendar.MONTH) + 1;
        int Year = cal.get(Calendar.YEAR);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        String MonthStr = String.valueOf(Month);
        String YearStr = String.valueOf(Year);
        comment = datasource.createComment(Integer.toString(scoreFinal.getScoreFinal()), dayOfMonthStr + "-" + MonthStr + "-" + YearStr);

        // Gestion des badges
        gestionBadges(scoreFinal.getScoreFinal());


        // Vider la BDD
        buttonDeleteBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                List<ScoreLavage> values = datasource.getAllComments();
                int size = values.size();
                for (ScoreLavage i : values) {
                    datasource.deleteComment(i);
                    Toast.makeText(getBaseContext(), "SCORE DELETE ! ["+i.toString()+"]", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Vider la BDD des Badges
        buttonDeleteBDDBadges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                BadgesDataSource datasourceBadge;
                Badges badge;

                // Initialisation du DataSource pour charger les badges
                datasourceBadge = new BadgesDataSource(_context);
                datasourceBadge.open();
                List<Badges> values = datasourceBadge.getAllBadges();
                // Boucle sur tout les badges
                for (int i = 0; i < values.size(); i++) {
                    long Badges_BadgeId = values.get(i).getId();
                    // En enfin, sauvegarde du badge pour l'utilisateur
                    if(datasourceBadge.applyBadge(Badges_BadgeId, 0)){
                        Toast.makeText(getBaseContext(), "BADGE REMOVE ! ["+Long.toString(Badges_BadgeId)+"]", Toast.LENGTH_SHORT).show();
                    }
                }
                datasourceBadge.close();
            }
        });

        // Partager sur Twitter
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Publication sur Facebook
                publishFaceBook();
            }
        });
    }

    // Gestion des Boutons
    private void gestionButton() {
        buttonShare = (Button) findViewById(R.id.button3);
        buttonDeleteBDD = (Button) findViewById(R.id.button5);
        buttonDeleteBDDBadges = (Button) findViewById(R.id.button8);
        gestionButtonFacebook();
    }

    // Gestion de tous les TextView
    private void gestionTextView() {
        viewTempsLavage = (TextView) findViewById(R.id.textView7);
        viewSCORE_DEVANT_VERTICAL = (TextView) findViewById(R.id.textView15);
        viewSCORE_DESSUS_BAS_HORIZONTALE = (TextView) findViewById(R.id.textView16);
        viewSCORE_DESSOUS_HAUT_HORIZONTALE = (TextView) findViewById(R.id.textView17);
        viewSCORE_DERRIERE_HAUT = (TextView) findViewById(R.id.textView18);
        viewSCORE_DERRIERE_BAS = (TextView) findViewById(R.id.textView19);
        viewSCORE_NOTHING = (TextView) findViewById(R.id.textView20);
        viewScoreFinal = (TextView) findViewById(R.id.textView21);
    }


    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    /*
     * Recherce si un nouveau badge peut-etre debloque
     *
     * Recherche dans tout les badges non attribues, et si le score depasse un seuil, attribuer un nouveau bade a l'utilisateur
     */
    private void gestionBadges(int scoreCourant){
        // Initialisation variables
        String Badges_BadgeName;
        int Badges_ScoreMax;
        int Badges_HasBadge;
        int scoreTotal = 0;
        long Badges_BadgeId =0;
        BadgesDataSource datasourceBadge;
        Badges badge;

        // Initialisation du DataSource pour charger les badges
        datasourceBadge = new BadgesDataSource(this);
        datasourceBadge.open();
        List<Badges> values = datasourceBadge.getAllBadges();


        // Boucle sur tout les badges
        for (int i = 0; i < values.size(); i++) {
            Badges_BadgeId = values.get(i).getId();
            Badges_BadgeName = values.get(i).getBadges_BadgeName();
            Badges_ScoreMax = values.get(i).getBadges_ScoreMax();
            Badges_HasBadge = values.get(i).getBadges_HasBadge();

            // Si l'utilisateur en dispose pas du badge
            if(Badges_HasBadge==0){
                // Charge l'objet des meilleurs scores
                GestionScore score = new GestionScore();
                scoreTotal = score.getScoreTotal(_context);

                // Si le score de l'utilisateur depasse le seuil du badge, on lui attribut le badge
                if(scoreTotal+scoreCourant > Badges_ScoreMax){
                    // Balance un toast
                    Toast.makeText(getBaseContext(), "NOUVEAU "+Badges_BadgeName+" ("+Badges_ScoreMax+") DEBLOQUE !", Toast.LENGTH_SHORT).show();
                    // Balance ne notification
                    createNotification("NOUVEAU " + Badges_BadgeName + " (" + Badges_ScoreMax + ") DEBLOQUE !");

                    // En enfin, sauvegarde du badge pour l'utilisateur
                    if(datasourceBadge.applyBadge(Badges_BadgeId, 1)){
                        Toast.makeText(getBaseContext(), "MAJ OK !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        datasourceBadge.close();
    }

    /*
     * Creation d'une notification pour indiquer un nouveau badge
     */
    private final void createNotification(String notificationDesc) {
        //Recuperation du titre et description de la notification
        final String notificationTitle = "Nouveau badge debloque !";

        //Recuperation du notification Manager
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Creation de la notification avec specification de l'icone de la notification et le texte qui apparait a la creation de la notification
        final Notification notification = new Notification(R.mipmap.ic_launcher, notificationTitle, System.currentTimeMillis());

        //Definition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        //Notification & Vibration
        notification.setLatestEventInfo(this, notificationTitle, notificationDesc, pendingIntent);
        notification.vibrate = new long[]{0, 200, 100, 200, 100, 200};
        notificationManager.notify(1, notification);
    }


    /*
     * Action au clic sur le bouton facebook pour se connecter
     */
    public void gestionButtonFacebook(){
        fbLoginButton = (LoginButton)findViewById(R.id.fb_login_button);
        fbLoginButton.setPublishPermissions(Arrays.asList("publish_actions"));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("Facebook Login Successful!");
                System.out.println("Logged in user Details : ");
                System.out.println("--------------------------");
                System.out.println("User ID  : " + loginResult.getAccessToken().getUserId());
                System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
                Toast.makeText(LavageEndStatsActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LavageEndStatsActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LavageEndStatsActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");
            }
        });
    }


    /*
     * Obligatoire pour que Facebook ai un Handle de retour
     */
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent i) {
        callbackManager.onActivityResult(reqCode, resCode, i);
    }


    /*
     * Publie une image et du texte sur Facebook
     */
    public void publishFaceBook(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);
    }

}
