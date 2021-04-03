package edu.cs.byu.cs240.nrsmac.familymap.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.cs.byu.cs240.nrsmac.familymap.R;


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