package edu.cs.byu.cs240.nrsmac.familymap.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import edu.cs.byu.cs240.nrsmac.familymap.R;
import edu.cs.byu.cs240.nrsmac.familymap.net.Client;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.LoginRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.LoginResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private static final String LOGGING_IN = "li";
    private static final String REGISTERING = "r";

    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String MESSAGE_KEY = "MessageKey";



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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = LOGGING_IN;

                String hostName = String.valueOf(serverNameText.getText());
                String port = String.valueOf(portText.getText());
                String username = String.valueOf(usernameText.getText());
                String password = String.valueOf(passwordText.getText());

                Handler uiThreadMessageHandler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        Bundle bundle = message.getData();

                        String token = bundle.getString(AUTH_TOKEN_KEY);
                        String responseMessage = bundle.getString(MESSAGE_KEY);

                         Log.d(TAG,responseMessage + token + "");
                    }
                };
                LoginRequest request = new LoginRequest(username,password);
                GetDataTask task = new GetDataTask(uiThreadMessageHandler, hostName, port, request);
            }
        });

        return view;
    }

    private static class GetDataTask implements Runnable {

        private final Handler messageHandler;
        private final String hostName;
        private final String port;
        private final LoginRequest request;

        private GetDataTask(Handler messageHandler, String hostName, String port, LoginRequest request) {
            this.messageHandler = messageHandler;
            this.hostName = hostName;
            this.port = port;
            this.request = request;
        }

        @Override
        public void run() {
            Client client = new Client();

            LoginResponse response = client.login(hostName,port,request);

            sendMessage(response);
        }

        private void sendMessage(LoginResponse response) {
            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            messageBundle.putString(AUTH_TOKEN_KEY, response.getAuthtoken());
            messageBundle.putString(MESSAGE_KEY, response.getMessage());
            message.setData(messageBundle);
            messageHandler.sendMessage(message);
        }
    }
}