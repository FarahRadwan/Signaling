package activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import helper.SQLiteHandler;
import helper.SessionManager;
import info.androidhive.loginandregistration.R;

public class MainActivity extends Activity {
    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btnshop;
    private Button btncart;
    private Button currentLocation;
    private SQLiteHandler db;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnshop = (Button) findViewById(R.id.btnshop);
        btncart = (Button) findViewById(R.id.btncart);
        currentLocation = (Button) findViewById(R.id.currentLocation);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        String id=user.get("id");
      //  System.out.println(id+"id");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);


        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                logoutUser();

            }
        });
        btnshop.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                shop();

            }
        });
        btncart.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                cart();

            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mapsLoc();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void mapsLoc() {


        // Launching the maps activity
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
      //  finish();
    }
    private void shop() {


        // Launching the maps activity
        Intent intent = new Intent(MainActivity.this,ProductsActivity.class);
        startActivity(intent);
        //finish();
    }
    private void cart() {


        // Launching the maps activity
        Intent intent = new Intent(MainActivity.this,CartActivity.class);
        startActivity(intent);
        //finish();
    }


}