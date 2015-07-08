package fr.lemeut.loic.musketeeth.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;


import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import fr.lemeut.loic.musketeeth.R;
import fr.lemeut.loic.musketeeth.fragment.BrossageFragment;
import fr.lemeut.loic.musketeeth.fragment.ScoreFragment;


public class HomeActivity extends AppCompatActivity implements BrossageFragment.OnFragmentInteractionListener, ScoreFragment.OnFragmentInteractionListener {
    private  FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        BrossageFragment fragment = new BrossageFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withAnimateDrawerItems(true)
                .withDisplayBelowToolbar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.title_section1),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.title_section2),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.title_section3),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Dev")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Fragment fragment;
                        fragmentTransaction=fragmentManager.beginTransaction();
                        switch (position) {
                            case 0:
                                fragment = new BrossageFragment();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);

                                break;
                            case 2:
                                fragment = new ScoreFragment();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);

                                break;
                            case 4:
                                fragment = new BrossageFragment();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);

                                break;
                            case 6:
                                Intent intent = new Intent(getApplication().getApplicationContext(), MainActivity.class);
                                startActivity(intent);


                                break;
                            default:
                                fragment = new BrossageFragment();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);

                                break;
                        }

                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        return false;
                    }
                })
                .build();

        // La suppression de la notification se fait grace a son ID
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        notificationManager.cancel(2);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
