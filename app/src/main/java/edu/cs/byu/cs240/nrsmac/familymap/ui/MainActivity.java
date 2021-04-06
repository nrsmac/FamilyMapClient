package edu.cs.byu.cs240.nrsmac.familymap.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.cs.byu.cs240.nrsmac.familymap.R;


import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LoginFragment loginFragment;
    private MapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFragment = new LoginFragment();
        mapFragment = new MapFragment();

        Iconify.with(new FontAwesomeModule());

        if (savedInstanceState == null) {
//            if(!DataCache.instance().isSyncSuccess()){
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, loginFragment, null)
                        .commit();
//            } else {
//                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, mapFragment, null).commit();
//            }
        }
    }
}