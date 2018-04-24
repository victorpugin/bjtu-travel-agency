package com.bjtutravel.bjtutravelagency;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bjtutravel.bjtutravelagency.auth.AuthUiActivity;
import com.bjtutravel.bjtutravelagency.request.create.CreateRequestActivity;
import com.bjtutravel.bjtutravelagency.utils.UtilSnackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIREBASE AUTH
        mFirebaseAuth = FirebaseAuth.getInstance();
        loadCurrentUser();

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FLOATING BUTTON
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, CreateRequestActivity.class);
                startActivity(in);
            }
        });

        // FRAGMENT MANAGER
        loadContentFragment();
    }

    // FRAGMENT MANAGER
    public void loadContentFragment() {
        tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.content_frame);

        tabHost.addTab(tabHost.newTabSpec("Plans").setIndicator("Plans", null), MainActivityFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Requests").setIndicator("Requests", null), MainActivityFragment.class, null);

    }

    // TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_disconnect) {
            mFirebaseAuth.signOut();
            loadCurrentUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // FIREBASE AUTH
    private void loadCurrentUser() {

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null){
            //Not signed in, launch the Sign In Activity
            Intent in = new Intent(this, AuthUiActivity.class);
            startActivity(in);
            finish();
            return;
        }else {
            String mUsername = mFirebaseUser.getDisplayName();
            String mEmailAddress = mFirebaseUser.getEmail();
            showSnackbar("User: " + mUsername + ". Email: " + mEmailAddress);
        }
    }

    private void showSnackbar(String msg) {
        UtilSnackbar.showSnakbar(findViewById(android.R.id.content), msg);
    }
}
