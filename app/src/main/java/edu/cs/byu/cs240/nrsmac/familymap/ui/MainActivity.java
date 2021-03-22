package edu.cs.byu.cs240.nrsmac.familymap.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import edu.cs.byu.cs240.nrsmac.familymap.R;


public class MainActivity extends Activity  {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFragment = new LoginFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.frameLayout, loginFragment, null)
                    .commit();
        }
    }
}