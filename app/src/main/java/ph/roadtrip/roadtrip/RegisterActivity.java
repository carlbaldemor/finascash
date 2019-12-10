package ph.roadtrip.roadtrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

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
    private static final String KEY_BIRTH_DATE = "dateOfBirth";
    private static final String KEY_ID_NUMBER = "identificationNumber";

    private String username, password, firstName, lastName, contactNum, address, emailAddress, dateOfBirth, confirmPassword, identificationNumber;
    private EditText etUsername, etPassword, etFirstName, etLastName, etContactNum, etAddress, etEmailAddress, etConfirmPassword;
    private Button btnRegister, btnLogin;
    private DatePicker datePicker;
    private String registerURL;
    private SessionHandler session;
    private int userID;
    blockChainWallet bc = new blockChainWallet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etContactNum = findViewById(R.id.etContactNum);
        etAddress = findViewById(R.id.etAddress);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        datePicker = findViewById(R.id.datePicker);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        UrlBean url = new UrlBean();
        registerURL = url.getRegisterURL();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                contactNum = etContactNum.getText().toString();
                address = etAddress.getText().toString();
                emailAddress = etEmailAddress.getText().toString();
                identificationNumber = "string";

                dateOfBirth = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
                try {
                    bc.blockchainVolley(RegisterActivity.this);
                    JSONObject jsonobject = new JSONObject(bc.getResponseData());
                    JSONObject data = jsonobject.getJSONObject("data");
                    String dataAddress = data.getString("address");
                    Log.d("JSON Response", dataAddress);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(validateInputs()){
                    register();
                }
            }
        });


    }

    public void loadDashboard(){
        Intent load = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(load);
        finish();
    }

    public void register(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_FIRST_NAME, firstName);
            request.put(KEY_LAST_NAME, lastName);
            request.put(KEY_EMAIL_ADDRESS, emailAddress);
            request.put(KEY_PHONE_NUMBER, contactNum);
            request.put(KEY_BIRTH_DATE, dateOfBirth);
            request.put(KEY_ADDRESS, address);
            request.put(KEY_ID_NUMBER, identificationNumber);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, registerURL, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {

                                userID = response.getInt(KEY_USER_ID);

                                session.loginUser(userID,username,
                                        firstName, lastName,
                                        emailAddress,
                                        contactNum, address, identificationNumber);

                                loadDashboard();
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }



    public boolean validateInputs(){
        if(!password.equals(confirmPassword)){
            etConfirmPassword.setError("Password does not match!");
            return false;
        }
        return true;
    }
}
