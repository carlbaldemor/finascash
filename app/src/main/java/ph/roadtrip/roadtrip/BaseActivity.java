package ph.roadtrip.roadtrip;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String KEY_EMAIL = "emailAddress";
    private static final String KEY_MOBILE_NUMBER = "mobileNumber";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_STATUS_USER ="status";
    private static final String KEY_IS_VERIFIED ="isVerified";
    private static final String KEY_STATUS ="status1";
    private static final String KEY_PROF_PIC ="profilePicture";
    private static final String KEY_MESSAGE ="message";
    private static final String KEY_USER_ID ="userID";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_MIDDLE_NAME = "middleName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_GENDER = "gender";


    private SessionHandler session;
    protected DrawerLayout drawer;
    private Intent load;
    private NavigationView navigationView;
    private TextView menu_name;
    private TextView menu_email;
    private String profilePicture;
    private String myUrl;
    private boolean role;
    private int userID;

    Fragment fragment ;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();
                userID = user.getUserID();

                TextView menu_name = findViewById(R.id.menu_name);
                TextView menu_email = findViewById(R.id.menu_email);
                ImageView menu_prof_pic = findViewById(R.id.menu_prof_pic);


                UrlBean url = new UrlBean();


                //Get and Set User details
                menu_name.setText(user.getFirstName() + " " + user.getLastName());
                menu_email.setText(user.getEmailAddress());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        navigationView = (NavigationView) drawer.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        menu_name = headerView.findViewById(R.id.menu_name);
        menu_email = headerView.findViewById(R.id.menu_email);

        session = new SessionHandler(getApplicationContext());

        User user = session.getUserDetails();
        userID = user.getUserID();

        UrlBean url = new UrlBean();


        //Get and Set User details
        menu_name.setText(user.getFirstName() + " " + user.getLastName());
        menu_email.setText(user.getEmailAddress());

        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        menu.removeItem(R.id.action_info);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        load = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(load);
                        finish();
                        break;
                    case R.id.nav_logout:
                        session.logoutUser();
                        load = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(load);
                        finish();
                        break;
                }
            }
        }, 200);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
