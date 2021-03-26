package edu.cs.byu.cs240.nrsmac.familymap.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import edu.cs.byu.cs240.nrsmac.familymap.R;


public class MainActivity extends Activity  {

    private static final String TAG = "MainActivity";

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