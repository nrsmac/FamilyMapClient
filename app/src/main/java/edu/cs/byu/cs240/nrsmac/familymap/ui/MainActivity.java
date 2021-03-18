package edu.cs.byu.cs240.nrsmac.familymap.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.FrameLayout;

import edu.cs.byu.cs240.nrsmac.familymap.R;
import edu.cs.byu.cs240.nrsmac.familymap.net.Client;


public class MainActivity extends AppCompatActivity {

    private FrameLayout frame;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLoginFragment();
    }

    private void startLoginFragment() {
        FragmentManager fm = this.getFragmentManager();

        loginFragment = new LoginFragment();
        Bundle args = new Bundle();
        loginFragment.setArguments(args);
        fm.beginTransaction()
                .add(R.id.frameLayout, loginFragment)
                .commit();
    }
}