package fr.lemeut.loic.musketeeth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends Activity {

    Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;


        Button buttonStartLavage = (Button) findViewById(R.id.button);
        buttonStartLavage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent goToNextActivty = new Intent(_context.getApplicationContext(), LavageStartActivity.class);
                _context.startActivity(goToNextActivty);
            }
        });


    }
}