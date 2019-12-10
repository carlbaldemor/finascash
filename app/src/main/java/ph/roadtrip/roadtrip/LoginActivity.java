package ph.roadtrip.roadtrip;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;



import ph.roadtrip.roadtrip.R;

public class LoginActivity extends AppCompatActivity {

    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_FIRST_NAME = "FirstName";
    private static final String KEY_LAST_NAME = "LastName";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_EMAIL_ADDRESS = "EmailAddress";
    private static final String KEY_STATUS = "status1";
    private static final String KEY_USER_ID = "userID";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ID_NUM = "identificationNumber";

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private String username, password, loginURL;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        if (session.isLoggedIn()) {
            loadDashboard();
        }
        setContentView(R.layout.activity_login);



        UrlBean url = new UrlBean();
        loginURL = url.getLoginURL();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent load = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(load);
                finish();
            }
        });



    }

    public void loadDashboard(){
        Intent load = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(load);
        finish();
    }

    public void login(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, loginURL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //Check if user got logged in successfully

                    if (response.getInt(KEY_STATUS) == 0) {
                        //Get User Type of User
                        //Login User by creating a session
                        session.loginUser(response.getInt(KEY_USER_ID),username,
                                response.getString(KEY_FIRST_NAME), response.getString(KEY_LAST_NAME),
                                response.getString(KEY_EMAIL_ADDRESS),
                                response.getString(KEY_PHONE_NUMBER), response.getString(KEY_ADDRESS), response.getString(KEY_ID_NUM));

                        //Load dashboard depending on user type
                        loadDashboard();



                    } else {
                        Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Display error message whenever an error occurs
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

}
