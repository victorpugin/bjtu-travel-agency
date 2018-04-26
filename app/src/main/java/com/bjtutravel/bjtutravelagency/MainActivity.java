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
import com.bjtutravel.bjtutravelagency.models.InfoPlan;
import com.bjtutravel.bjtutravelagency.models.Request;
import com.bjtutravel.bjtutravelagency.plan.list.PlanFragment;
import com.bjtutravel.bjtutravelagency.request.create.CreateRequestActivity;
import com.bjtutravel.bjtutravelagency.request.detail.DetailRequestActivity;
import com.bjtutravel.bjtutravelagency.request.list.RequestFragment;
import com.bjtutravel.bjtutravelagency.utils.UtilFirebase;
import com.bjtutravel.bjtutravelagency.utils.UtilSnackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements RequestFragment.OnListFragmentInteractionListener, PlanFragment.OnListFragmentInteractionListener {
    private static final String TAG = "MainActivity";

    private boolean mUserIsAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FIREBASE AUTH
        loadCurrentUser();
        // START HERE, USER IS AUTHENTICATE
    }

    private void onCreateUser() {
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

    private void onCreateAdmin() {
        mUserIsAdmin = true;
        setTitle(getTitle() + " - Staff");

        // FLOATING BUTTON
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        // FRAGMENT MANAGER
        loadContentFragment();
    }


    // FRAGMENT MANAGER
    public void loadContentFragment() {
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.content_frame);

        Bundle bundle = new Bundle();
        bundle.putBoolean(RequestFragment.KEY_ADMIN, mUserIsAdmin);

        tabHost.addTab(tabHost.newTabSpec("Plans").setIndicator("Plans", null), PlanFragment.class, bundle);
        tabHost.addTab(tabHost.newTabSpec("Requests").setIndicator("Requests", null), RequestFragment.class, bundle);
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
            UtilFirebase.getFirebaseAuth().signOut();
            loadCurrentUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // FIREBASE AUTH
    private void loadCurrentUser() {

        final FirebaseUser firebaseUser = UtilFirebase.getFirebaseUser();

        if (firebaseUser == null){
            //Not signed in, launch the Sign In Activity
            Intent in = new Intent(this, AuthUiActivity.class);
            startActivity(in);
            finish();
            return;
        }else {
            // USER AUTHENTICATE, get user privileges
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("privileges");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Check user privilege and call onCreate corresponding
                    if (dataSnapshot.hasChild(firebaseUser.getUid())) {
                        onCreateAdmin();
                    } else {
                        onCreateUser();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "loadCurrentUser:onCancelled", databaseError.toException());
                }
            });
        }
    }


    // REQUEST LISTENER
    @Override
    public void onListFragmentInteraction(Request request) {
        Intent newIntent = new Intent(MainActivity.this, DetailRequestActivity.class);
        newIntent.putExtra("request", request);
        newIntent.putExtra(DetailRequestActivity.KEY_ADMIN, mUserIsAdmin);
        MainActivity.this.startActivity(newIntent);
    }

    // PLAN LISTENER
    @Override
    public void onListFragmentInteraction(InfoPlan infoPlan) {

    }

    private void showSnackbar(String msg) {
        UtilSnackbar.showSnakbar(findViewById(android.R.id.content), msg);
    }
}
