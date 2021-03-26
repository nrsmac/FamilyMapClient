package edu.cs.byu.cs240.nrsmac.familymap.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.cs.byu.cs240.nrsmac.familymap.R;
import edu.cs.byu.cs240.nrsmac.familymap.model.Person;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.RegisterRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.LoginTask;
import edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.PersonTask;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.LoginRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.PersonsRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.RegisterTask;
import edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants;

import static edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants.AUTH_TOKEN_KEY;
import static edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants.FIRST_NAME_KEY;
import static edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants.LAST_NAME_KEY;
import static edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants.MESSAGE_KEY;
import static edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants.REGISTERING;
import static edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants.SUCCESS;
import static edu.cs.byu.cs240.nrsmac.familymap.net.Tasks.TaskConstants.USERNAME_KEY;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";




    private static String status;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Button loginButton;
    private Button registerButton;

    private EditText serverNameText;
    private EditText portText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleButton;
    private RadioButton femaleButton;

    private String token;
    private Person userPerson;
    private String userId;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = view.findViewById(R.id.signInButton);
        registerButton = view.findViewById(R.id.registerButton);

        serverNameText = view.findViewById(R.id.serverHost);
        portText = view.findViewById(R.id.port);
        usernameText = view.findViewById(R.id.username);
        passwordText = view.findViewById(R.id.password);
        firstNameText = view.findViewById(R.id.firstName);
        lastNameText = view.findViewById(R.id.lastName);
        emailText  = view.findViewById(R.id.email);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        maleButton = view.findViewById(R.id.maleButton);
        femaleButton = view.findViewById(R.id.femaleButton);

        loginButton.setEnabled(false);
        registerButton.setEnabled(false);


        TextWatcher loginWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (serverNameText.getText().length() > 0 &&
                        portText.getText().length() > 0 &&
                        usernameText.length()>0 &&
                        passwordText.getText().length()>0){
                    loginButton.setEnabled(true);
                }   else {
                    loginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };

        serverNameText.addTextChangedListener(loginWatcher);
        portText.addTextChangedListener(loginWatcher);
        usernameText.addTextChangedListener(loginWatcher);
        passwordText.addTextChangedListener(loginWatcher);

        TextWatcher registerWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (serverNameText.getText().length() > 0 &&
                        portText.getText().length() > 0 &&
                        usernameText.length()>0 &&
                        passwordText.getText().length()>0 &&
                        firstNameText.getText().length()>0 &&
                        lastNameText.getText().length()>0 &&
                        emailText.getText().length()>0 ){
                    registerButton.setEnabled(true);
                }   else {
                    registerButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };

        serverNameText.addTextChangedListener(registerWatcher);
        portText.addTextChangedListener(registerWatcher);
        usernameText.addTextChangedListener(registerWatcher);
        passwordText.addTextChangedListener(registerWatcher);
        firstNameText.addTextChangedListener(registerWatcher);
        lastNameText.addTextChangedListener(registerWatcher);
        emailText.addTextChangedListener(registerWatcher);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = TaskConstants.LOGGING_IN;

                String hostName = String.valueOf(serverNameText.getText());
                String port = String.valueOf(portText.getText());
                String username = String.valueOf(usernameText.getText());
                String password = String.valueOf(passwordText.getText());

                Handler syncHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message message) {
                        Bundle bundle = message.getData();
                        boolean syncSuccess = bundle.getBoolean(SUCCESS);
                        String responseMessage = bundle.getString(MESSAGE_KEY);

                        userId = bundle.getString(USERNAME_KEY);
                        String firstName = bundle.getString(FIRST_NAME_KEY);
                        String lastName = bundle.getString(LAST_NAME_KEY);

                        if(syncSuccess) {
                            Toast.makeText(getActivity(), "Retrieving data successful: " + firstName + " " + lastName, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Couldn't retrieve data :" + responseMessage, Toast.LENGTH_LONG).show();

                        }
                    }
                };

                Handler loginMessageHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message message) {
                        Bundle bundle = message.getData();
                        token = bundle.getString(AUTH_TOKEN_KEY);
                        boolean loginSuccess = bundle.getBoolean(SUCCESS);
                        String responseMessage = bundle.getString(MESSAGE_KEY);
                        
                        if(loginSuccess) {
                            PersonsRequest personsRequest = new PersonsRequest(token);
                            PersonTask personTask = new PersonTask(syncHandler,hostName,port,personsRequest);
                            ExecutorService executor = Executors.newSingleThreadExecutor();
                            executor.submit(personTask);

                            Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Login failed: " + responseMessage, Toast.LENGTH_LONG).show();

                        }
                    }
                };


                LoginRequest request = new LoginRequest(username,password);
                LoginTask task = new LoginTask(loginMessageHandler, hostName, port, request);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(task);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = REGISTERING;

                String hostName = String.valueOf(serverNameText.getText());
                String port = String.valueOf(portText.getText());
                String username = String.valueOf(usernameText.getText());
                String password = String.valueOf(passwordText.getText());
                String firstName = String.valueOf(firstNameText.getText());
                String lastName = String.valueOf(lastNameText.getText());
                String email = String.valueOf(emailText.getText());
                String gender = "";

                if(maleButton.isChecked()){
                    gender = "m";
                } else if(femaleButton.isChecked()){
                    gender = "f";
                }

                Handler registerMessageHandler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message message){
                        Bundle bundle = message.getData();
                        boolean registerSuccess = bundle.getBoolean(SUCCESS);
                        String responseMessage = bundle.getString(MESSAGE_KEY);

                        if(registerSuccess){
                            Toast.makeText(getActivity(), "Register successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                RegisterRequest request = new RegisterRequest(username,password,email,firstName,lastName,gender);
                RegisterTask task = new RegisterTask(registerMessageHandler, hostName, port, request);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(task);
            }

        });

        return view;
    }

}